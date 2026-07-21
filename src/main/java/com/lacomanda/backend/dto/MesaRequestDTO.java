package com.lacomanda.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MesaRequestDTO {

    @NotNull(message = "El número de mesa es obligatorio")
    private Integer numero;

    @NotNull(message = "La capacidad es obligatoria")
    private Integer capacidad;
}