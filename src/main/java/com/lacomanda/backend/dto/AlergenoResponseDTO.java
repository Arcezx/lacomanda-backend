package com.lacomanda.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlergenoResponseDTO {

    private Long id;
    private String nombreEs;
    private String nombreVal;
    private String nombreEn;
    private String icono;
}