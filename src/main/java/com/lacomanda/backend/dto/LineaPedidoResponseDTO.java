package com.lacomanda.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineaPedidoResponseDTO {

    private Long id;
    private Long productoId;
    private String productoNombreEs;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private String notas;
}