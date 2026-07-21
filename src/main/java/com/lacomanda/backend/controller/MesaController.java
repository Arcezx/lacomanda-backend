package com.lacomanda.backend.controller;

import com.lacomanda.backend.dto.MesaRequestDTO;
import com.lacomanda.backend.dto.MesaResponseDTO;
import com.lacomanda.backend.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public ResponseEntity<List<MesaResponseDTO>> findAll() {
        return ResponseEntity.ok(mesaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MesaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mesaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MesaResponseDTO> create(@Valid @RequestBody MesaRequestDTO dto) {
        MesaResponseDTO creada = mesaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MesaResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody MesaRequestDTO dto) {
        return ResponseEntity.ok(mesaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}