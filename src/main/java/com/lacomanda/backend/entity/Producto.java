package com.lacomanda.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name = "nombre_es", nullable = false)
    private String nombreEs;

    @Column(name = "nombre_val", nullable = false)
    private String nombreVal;

    @Column(name = "nombre_en", nullable = false)
    private String nombreEn;

    @Column(name = "descripcion_es", columnDefinition = "TEXT")
    private String descripcionEs;

    @Column(name = "descripcion_val", columnDefinition = "TEXT")
    private String descripcionVal;

    @Column(name = "descripcion_en", columnDefinition = "TEXT")
    private String descripcionEn;

    @Column(name = "precio", nullable = false, precision = 6, scale = 2)
    private BigDecimal precio;

    @Column(name = "foto")
    private String foto;

    @Column(name = "disponible", nullable = false)
    private boolean disponible = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_alergeno",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "alergeno_id")
    )
    private Set<Alergeno> alergenos = new HashSet<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingrediente> ingredientes = new ArrayList<>();
}