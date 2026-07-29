package com.lacomanda.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "linea_pedido_extra")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LineaPedidoExtra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linea_pedido_id", nullable = false)
    private LineaPedido lineaPedido;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio_unitario", nullable = false, precision = 6, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}