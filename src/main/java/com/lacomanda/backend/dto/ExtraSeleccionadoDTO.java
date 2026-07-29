package com.lacomanda.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExtraSeleccionadoDTO {
    @NotNull(message = "El extra es obligatorio")
    private Long extraId;

    @NotNull(message = "La cantidad del extra es obligatoria")
    @Min(value = 1, message = "La cantidad mínima del extra es 1")
    private Integer cantidad;
}