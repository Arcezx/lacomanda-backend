package com.lacomanda.backend.controller;

import com.lacomanda.backend.dto.AlergenoRequestDTO;
import com.lacomanda.backend.dto.AlergenoResponseDTO;
import com.lacomanda.backend.service.AlergenoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alergenos")
@RequiredArgsConstructor
public class AlergenoController {

    private final AlergenoService alergenoService;

    @GetMapping
    public ResponseEntity<List<AlergenoResponseDTO>> findAll() {
        return ResponseEntity.ok(alergenoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlergenoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alergenoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AlergenoResponseDTO> create(@Valid @RequestBody AlergenoRequestDTO dto) {
        AlergenoResponseDTO creado = alergenoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlergenoResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody AlergenoRequestDTO dto) {
        return ResponseEntity.ok(alergenoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alergenoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}