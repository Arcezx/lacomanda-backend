package com.lacomanda.backend.controller;

import com.lacomanda.backend.dto.ProductoRequestDTO;
import com.lacomanda.backend.dto.ProductoResponseDTO;
import com.lacomanda.backend.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAll(
            @RequestParam(required = false) Long categoriaId) {

        if (categoriaId != null) {
            return ResponseEntity.ok(productoService.findByCategoria(categoriaId));
        }
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<ProductoResponseDTO>> findAllPaginado(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanio) {

        Pageable pageable = PageRequest.of(pagina, tamanio);
        return ResponseEntity.ok(productoService.findAllPaginado(pageable));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO creado = productoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}