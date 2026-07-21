package com.lacomanda.backend.dto;

import com.lacomanda.backend.entity.EstadoPedido;
import com.lacomanda.backend.entity.FormaPago;
import com.lacomanda.backend.entity.TipoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    private Long id;
    private TipoPedido tipo;
    private EstadoPedido estado;
    private LocalDateTime fecha;
    private FormaPago formaPago;

    private Integer mesaNumero; // null si es domicilio

    private DomicilioResponseDTO domicilio; // null si es local

    private List<LineaPedidoResponseDTO> lineas;

    private BigDecimal total;
}