package com.lacomanda.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;

    private Long categoriaId;
    private String categoriaNombreEs;

    private String nombreEs;
    private String nombreVal;
    private String nombreEn;

    private String descripcionEs;
    private String descripcionVal;
    private String descripcionEn;

    private BigDecimal precio;
    private String foto;
    private boolean disponible;

    private List<AlergenoResponseDTO> alergenos;
    private List<IngredienteDTO> ingredientes;
}