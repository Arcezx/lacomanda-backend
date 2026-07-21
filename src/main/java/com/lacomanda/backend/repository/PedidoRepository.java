package com.lacomanda.backend.repository;

import com.lacomanda.backend.entity.EstadoPedido;
import com.lacomanda.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}