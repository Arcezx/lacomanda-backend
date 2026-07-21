package com.lacomanda.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredienteDTO {

    private Long id;

    @NotBlank(message = "El nombre del ingrediente es obligatorio")
    private String nombre;
}