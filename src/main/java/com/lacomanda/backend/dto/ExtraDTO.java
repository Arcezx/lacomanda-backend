package com.lacomanda.backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExtraDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
}