package com.lacomanda.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "configuracion")
@Getter
@Setter
public class Configuracion {
    @Id
    private String clave;

    @Column(nullable = false)
    private String valor;
}