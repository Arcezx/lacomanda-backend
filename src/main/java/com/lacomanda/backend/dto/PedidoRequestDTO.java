package com.lacomanda.backend.dto;

import com.lacomanda.backend.entity.FormaPago;
import com.lacomanda.backend.entity.TipoPedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotNull(message = "El tipo de pedido es obligatorio")
    private TipoPedido tipo;

    private Long mesaId; // aqui seria obligatorio solo si tipo = LOCAL

    private DomicilioRequestDTO domicilio; // aqui solo si tipo = DOMICILIO

    @NotNull(message = "La forma de pago es obligatoria")
    private FormaPago formaPago;

    @NotEmpty(message = "El pedido debe tener al menos una línea")
    @Valid
    private List<LineaPedidoRequestDTO> lineas;
}