package com.lacomanda.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_es", nullable = false)
    private String nombreEs;

    @Column(name = "nombre_val", nullable = false)
    private String nombreVal;

    @Column(name = "nombre_en", nullable = false)
    private String nombreEn;

    @Column(name = "orden")
    private Integer orden;
}