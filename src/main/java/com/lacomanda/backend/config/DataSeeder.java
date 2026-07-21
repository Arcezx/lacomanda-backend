package com.lacomanda.backend.config;

import com.lacomanda.backend.entity.*;
import com.lacomanda.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final AlergenoRepository alergenoRepository;
    private final ProductoRepository productoRepository;
    private final MesaRepository mesaRepository;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() > 0) {
            return; // si ya tengo datos, no vuevo a insertar
        }

        // categorías
        Categoria entrantes = new Categoria();
        entrantes.setNombreEs("Entrantes");
        entrantes.setNombreVal("Entrants");
        entrantes.setNombreEn("Starters");
        entrantes.setOrden(1);
        categoriaRepository.save(entrantes);

        Categoria principales = new Categoria();
        principales.setNombreEs("Platos principales");
        principales.setNombreVal("Plats principals");
        principales.setNombreEn("Main courses");
        principales.setOrden(2);
        categoriaRepository.save(principales);

        Categoria postres = new Categoria();
        postres.setNombreEs("Postres");
        postres.setNombreVal("Postres");
        postres.setNombreEn("Desserts");
        postres.setOrden(3);
        categoriaRepository.save(postres);

        // alérgenos
        Alergeno gluten = new Alergeno();
        gluten.setNombreEs("Gluten");
        gluten.setNombreVal("Gluten");
        gluten.setNombreEn("Gluten");
        gluten.setIcono("gluten.svg");
        alergenoRepository.save(gluten);

        Alergeno lacteos = new Alergeno();
        lacteos.setNombreEs("Lácteos");
        lacteos.setNombreVal("Lactis");
        lacteos.setNombreEn("Dairy");
        lacteos.setIcono("lacteos.svg");
        alergenoRepository.save(lacteos);

        // productos
        Producto paella = new Producto();
        paella.setCategoria(principales);
        paella.setNombreEs("Paella valenciana");
        paella.setNombreVal("Paella valenciana");
        paella.setNombreEn("Valencian paella");
        paella.setDescripcionEs("Arroz con pollo, conejo y verduras");
        paella.setDescripcionVal("Arros amb pollastre, conill i verdures");
        paella.setDescripcionEn("Rice with chicken, rabbit and vegetables");
        paella.setPrecio(new BigDecimal("14.50"));
        paella.setDisponible(true);
        paella.setAlergenos(Set.of());
        productoRepository.save(paella);

        Producto ensaladilla = new Producto();
        ensaladilla.setCategoria(entrantes);
        ensaladilla.setNombreEs("Ensaladilla rusa");
        ensaladilla.setNombreVal("Ensaladilla russa");
        ensaladilla.setNombreEn("Russian salad");
        ensaladilla.setDescripcionEs("Con mahonesa casera");
        ensaladilla.setDescripcionVal("Amb maonesa casolana");
        ensaladilla.setDescripcionEn("With homemade mayonnaise");
        ensaladilla.setPrecio(new BigDecimal("6.50"));
        ensaladilla.setDisponible(true);
        ensaladilla.setAlergenos(Set.of(lacteos));
        productoRepository.save(ensaladilla);

        Producto tarta = new Producto();
        tarta.setCategoria(postres);
        tarta.setNombreEs("Tarta de queso");
        tarta.setNombreVal("Tarta de formatge");
        tarta.setNombreEn("Cheesecake");
        tarta.setDescripcionEs("Con coulis de frutos rojos");
        tarta.setDescripcionVal("Amb coulis de fruits vermells");
        tarta.setDescripcionEn("With red berry coulis");
        tarta.setPrecio(new BigDecimal("5.00"));
        tarta.setDisponible(true);
        tarta.setAlergenos(Set.of(gluten, lacteos));
        productoRepository.save(tarta);

        // mesas
        for (int i = 1; i <= 6; i++) {
            Mesa mesa = new Mesa();
            mesa.setNumero(i);
            mesa.setCapacidad(i <= 3 ? 2 : 4);
            mesaRepository.save(mesa);
        }

        System.out.println(">>> Datos de prueba insertados correctamente <<<");
    }
}