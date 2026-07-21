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
public class AlergenoRequestDTO {

    @NotBlank(message = "El nombre en español es obligatorio")
    private String nombreEs;

    @NotBlank(message = "El nombre en valenciano es obligatorio")
    private String nombreVal;

    @NotBlank(message = "El nombre en inglés es obligatorio")
    private String nombreEn;

    private String icono;
}