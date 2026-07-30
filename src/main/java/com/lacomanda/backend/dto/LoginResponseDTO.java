package com.lacomanda.backend.dto;

import com.lacomanda.backend.entity.Rol;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String username;
    private String nombre;
    private Rol rol;
}