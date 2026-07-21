package com.lacomanda.backend.dto;

import com.lacomanda.backend.entity.EstadoDomicilio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomicilioResponseDTO {

    private Long id;
    private String direccion;
    private EstadoDomicilio estado;
}