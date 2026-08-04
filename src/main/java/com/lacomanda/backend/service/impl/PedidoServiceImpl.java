package com.lacomanda.backend.service.impl;

import com.lacomanda.backend.dto.*;
import com.lacomanda.backend.entity.*;
import com.lacomanda.backend.exception.NegocioException;
import com.lacomanda.backend.exception.ResourceNotFoundException;
import com.lacomanda.backend.repository.MesaRepository;
import com.lacomanda.backend.repository.PedidoRepository;
import com.lacomanda.backend.repository.ProductoRepository;
import com.lacomanda.backend.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProductoRepository productoRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> findByEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado).stream().map(this::toResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        return toResponseDTO(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoRequestDTO dto) {
        validarTipoPedido(dto);

        Pedido pedido = new Pedido();
        pedido.setTipo(dto.getTipo());
        pedido.setFormaPago(dto.getFormaPago());

        if (dto.getTipo() == TipoPedido.LOCAL) {
            Mesa mesa = mesaRepository.findById(dto.getMesaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + dto.getMesaId()));
            mesa.setOcupada(true);
            mesaRepository.save(mesa);
            pedido.setMesa(mesa);
        }

        for (LineaPedidoRequestDTO lineaDTO : dto.getLineas()) {
            Producto producto = productoRepository.findById(lineaDTO.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Producto no encontrado con id: " + lineaDTO.getProductoId()));

            LineaPedido linea = new LineaPedido();
            linea.setPedido(pedido);
            linea.setProducto(producto);
            linea.setPrecioUnitario(producto.getPrecio());
            linea.setCantidad(lineaDTO.getCantidad());
            linea.setNotas(lineaDTO.getNotas());

            if (lineaDTO.getExtras() != null) {
                for (ExtraSeleccionadoDTO extraSel : lineaDTO.getExtras()) {
                    Extra extra = producto.getExtras().stream()
                            .filter(e -> e.getId().equals(extraSel.getExtraId()))
                            .findFirst()
                            .orElseThrow(() -> new NegocioException(
                                    "El extra seleccionado no pertenece a este producto"));

                    LineaPedidoExtra lpe = new LineaPedidoExtra();
                    lpe.setLineaPedido(linea);
                    lpe.setNombre(extra.getNombre());
                    lpe.setPrecioUnitario(extra.getPrecio());
                    lpe.setCantidad(extraSel.getCantidad());

                    linea.getExtras().add(lpe);
                }
            }

            pedido.getLineas().add(linea);
        }

        if (dto.getTipo() == TipoPedido.DOMICILIO) {
            Domicilio domicilio = new Domicilio();
            domicilio.setPedido(pedido);
            domicilio.setDireccion(dto.getDomicilio().getDireccion());
            pedido.setDomicilio(domicilio);
        }

        Pedido guardado = pedidoRepository.save(pedido);
        PedidoResponseDTO respuestaDTO = toResponseDTO(guardado);

        messagingTemplate.convertAndSend("/topic/pedidos", respuestaDTO);

        return respuestaDTO;
    }

    @Override
    @Transactional
    public PedidoResponseDTO updateEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        pedido.setEstado(nuevoEstado);
        Pedido actualizado = pedidoRepository.save(pedido);

        PedidoResponseDTO respuestaDTO = toResponseDTO(actualizado);
        messagingTemplate.convertAndSend("/topic/pedidos-actualizados", respuestaDTO);

        return respuestaDTO;
    }

    private void validarTipoPedido(PedidoRequestDTO dto) {
        if (dto.getTipo() == TipoPedido.LOCAL && dto.getMesaId() == null) {
            throw new NegocioException("Un pedido local requiere indicar la mesa");
        }
        if (dto.getTipo() == TipoPedido.DOMICILIO && dto.getDomicilio() == null) {
            throw new NegocioException("Un pedido a domicilio requiere indicar la dirección");
        }
    }

    private PedidoResponseDTO toResponseDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setTipo(pedido.getTipo());
        dto.setEstado(pedido.getEstado());
        dto.setFecha(pedido.getFecha());
        dto.setFormaPago(pedido.getFormaPago());

        if (pedido.getMesa() != null) {
            dto.setMesaNumero(pedido.getMesa().getNumero());
        }

        if (pedido.getDomicilio() != null) {
            DomicilioResponseDTO domicilioDTO = new DomicilioResponseDTO(
                    pedido.getDomicilio().getId(),
                    pedido.getDomicilio().getDireccion(),
                    pedido.getDomicilio().getEstado()
            );
            dto.setDomicilio(domicilioDTO);
        }

        List<LineaPedidoResponseDTO> lineasDTO = pedido.getLineas().stream()
                .map(this::toLineaResponseDTO)
                .toList();
        dto.setLineas(lineasDTO);

        BigDecimal total = lineasDTO.stream()
                .map(LineaPedidoResponseDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotal(total);

        return dto;
    }

    private LineaPedidoResponseDTO toLineaResponseDTO(LineaPedido linea) {
        List<ExtraDTO> extrasDTO = linea.getExtras().stream()
                .map(e -> new ExtraDTO(e.getId(), e.getNombre(), e.getPrecioUnitario()))
                .toList();

        BigDecimal totalExtrasPorUnidad = linea.getExtras().stream()
                .map(e -> e.getPrecioUnitario().multiply(BigDecimal.valueOf(e.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal subtotal = linea.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(linea.getCantidad()))
                .add(totalExtrasPorUnidad);

        return new LineaPedidoResponseDTO(
                linea.getId(),
                linea.getProducto().getId(),
                linea.getProducto().getNombreEs(),
                linea.getPrecioUnitario(),
                linea.getCantidad(),
                linea.getNotas(),
                extrasDTO,
                subtotal
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PedidoResponseDTO> findAllPaginado(Pageable pageable) {
        return pedidoRepository.findAll(pageable).map(this::toResponseDTO);
    }
}