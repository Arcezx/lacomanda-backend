package com.lacomanda.backend.service.impl;
import com.lacomanda.backend.dto.MesaRequestDTO;
import com.lacomanda.backend.dto.MesaResponseDTO;
import com.lacomanda.backend.entity.EstadoPedido;
import com.lacomanda.backend.entity.Mesa;
import com.lacomanda.backend.entity.Pedido;
import com.lacomanda.backend.exception.NegocioException;
import com.lacomanda.backend.exception.ResourceNotFoundException;
import com.lacomanda.backend.repository.MesaRepository;
import com.lacomanda.backend.repository.PedidoRepository;
import com.lacomanda.backend.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {
    private final MesaRepository mesaRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final PedidoRepository pedidoRepository;
    @Override
    @Transactional(readOnly = true)
    public List<MesaResponseDTO> findAll() {
        return mesaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public MesaResponseDTO findById(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + id));
        return toResponseDTO(mesa);
    }
    @Override
    @Transactional(readOnly = true)
    public MesaResponseDTO findByQrCode(String qrCode) {
        Mesa mesa = mesaRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con código: " + qrCode));
        return toResponseDTO(mesa);
    }
    @Override
    @Transactional
    public MesaResponseDTO create(MesaRequestDTO dto) {
        if (mesaRepository.findByNumero(dto.getNumero()).isPresent()) {
            throw new NegocioException("Ya existe una mesa con el número " + dto.getNumero());
        }
        Mesa mesa = new Mesa();
        mesa.setNumero(dto.getNumero());
        mesa.setCapacidad(dto.getCapacidad());
        mesa.setQrCode(java.util.UUID.randomUUID().toString());
        Mesa guardada = mesaRepository.save(mesa);
        return toResponseDTO(guardada);
    }
    @Override
    @Transactional
    public MesaResponseDTO update(Long id, MesaRequestDTO dto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + id));
        mesaRepository.findByNumero(dto.getNumero()).ifPresent(otraMesa -> {
            if (!otraMesa.getId().equals(id)) {
                throw new NegocioException("Ya existe otra mesa con el número " + dto.getNumero());
            }
        });
        mesa.setNumero(dto.getNumero());
        mesa.setCapacidad(dto.getCapacidad());
        Mesa actualizada = mesaRepository.save(mesa);
        return toResponseDTO(actualizada);
    }
    @Override
    @Transactional
    public MesaResponseDTO cambiarOcupacion(Long id, boolean ocupada) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + id));

        boolean seLibero = mesa.isOcupada() && !ocupada;

        if (ocupada && !mesa.isOcupada()) {
            mesa.setSesionActual(java.util.UUID.randomUUID().toString());
        } else if (!ocupada) {
            mesa.setSesionActual(null);
        }

        mesa.setOcupada(ocupada);
        Mesa actualizada = mesaRepository.save(mesa);
        MesaResponseDTO respuestaDTO = toResponseDTO(actualizada);

        if (seLibero) {
            messagingTemplate.convertAndSend("/topic/mesas-actualizadas", respuestaDTO);
        }

        return respuestaDTO;
    }
    @Override
    @Transactional
    public void delete(Long id) {
        if (!mesaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mesa no encontrada con id: " + id);
        }
        mesaRepository.deleteById(id);
    }
    private MesaResponseDTO toResponseDTO(Mesa mesa) {
        return new MesaResponseDTO(
                mesa.getId(),
                mesa.getNumero(),
                mesa.getCapacidad(),
                mesa.getQrCode(),
                mesa.isOcupada(),
                mesa.getSesionActual()
        );
    }

    @Override
    @Transactional
    public MesaResponseDTO pagarYLiberar(String qrCode) {
        Mesa mesa = mesaRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con código: " + qrCode));

        if (!mesa.isOcupada()) {
            throw new NegocioException("Esta mesa ya está libre");
        }

        List<Pedido> pedidosDeSesion = pedidoRepository.findBySesionMesaId(mesa.getSesionActual());
        boolean todosEnviados = !pedidosDeSesion.isEmpty()
                && pedidosDeSesion.stream().allMatch(p -> p.getEstado() == EstadoPedido.ENVIADO);

        if (!todosEnviados) {
            throw new NegocioException("Todavía hay pedidos sin servir, no se puede pagar");
        }

        mesa.setOcupada(false);
        mesa.setSesionActual(null);
        Mesa actualizada = mesaRepository.save(mesa);
        MesaResponseDTO respuestaDTO = toResponseDTO(actualizada);

        messagingTemplate.convertAndSend("/topic/mesas-actualizadas", respuestaDTO);

        return respuestaDTO;
    }
}