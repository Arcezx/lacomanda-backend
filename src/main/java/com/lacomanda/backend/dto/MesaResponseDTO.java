package com.lacomanda.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MesaResponseDTO {

    private Long id;
    private Integer numero;
    private Integer capacidad;
    private String qrCode;
}