package com.lacomanda.backend.service;

import com.lacomanda.backend.dto.PedidoRequestDTO;
import com.lacomanda.backend.dto.PedidoResponseDTO;
import com.lacomanda.backend.entity.EstadoPedido;

import java.util.List;

public interface PedidoService {

    List<PedidoResponseDTO> findAll();

    List<PedidoResponseDTO> findByEstado(EstadoPedido estado);

    PedidoResponseDTO findById(Long id);

    PedidoResponseDTO create(PedidoRequestDTO dto);

    PedidoResponseDTO updateEstado(Long id, EstadoPedido nuevoEstado);
}