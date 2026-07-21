package com.lacomanda.backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    @NotBlank(message = "El nombre en español es obligatorio")
    private String nombreEs;

    @NotBlank(message = "El nombre en valenciano es obligatorio")
    private String nombreVal;

    @NotBlank(message = "El nombre en inglés es obligatorio")
    private String nombreEn;

    private String descripcionEs;
    private String descripcionVal;
    private String descripcionEn;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal precio;

    private String foto;

    private boolean disponible = true;

    private Set<Long> alergenoIds;

    private List<IngredienteDTO> ingredientes;
}