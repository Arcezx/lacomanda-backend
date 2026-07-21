package com.lacomanda.backend.controller;

import com.lacomanda.backend.dto.PedidoRequestDTO;
import com.lacomanda.backend.dto.PedidoResponseDTO;
import com.lacomanda.backend.entity.EstadoPedido;
import com.lacomanda.backend.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> findAll(
            @RequestParam(required = false) EstadoPedido estado) {

        if (estado != null) {
            return ResponseEntity.ok(pedidoService.findByEstado(estado));
        }
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO creado = pedidoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponseDTO> updateEstado(@PathVariable Long id,
                                                          @RequestParam EstadoPedido nuevoEstado) {
        return ResponseEntity.ok(pedidoService.updateEstado(id, nuevoEstado));
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<PedidoResponseDTO>> findAllPaginado(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {

        Pageable pageable = PageRequest.of(pagina, tamanio, Sort.by(Sort.Direction.DESC, "fecha"));
        return ResponseEntity.ok(pedidoService.findAllPaginado(pageable));
    }
}