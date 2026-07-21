package com.lacomanda.backend.service.impl;

import com.lacomanda.backend.dto.CategoriaRequestDTO;
import com.lacomanda.backend.dto.CategoriaResponseDTO;
import com.lacomanda.backend.entity.Categoria;
import com.lacomanda.backend.exception.ResourceNotFoundException;
import com.lacomanda.backend.repository.CategoriaRepository;
import com.lacomanda.backend.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        return toResponseDTO(categoria);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO create(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombreEs(dto.getNombreEs());
        categoria.setNombreVal(dto.getNombreVal());
        categoria.setNombreEn(dto.getNombreEn());
        categoria.setOrden(dto.getOrden());

        Categoria guardada = categoriaRepository.save(categoria);
        return toResponseDTO(guardada);
    }

    @Override
    @Transactional
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

        categoria.setNombreEs(dto.getNombreEs());
        categoria.setNombreVal(dto.getNombreVal());
        categoria.setNombreEn(dto.getNombreEn());
        categoria.setOrden(dto.getOrden());

        Categoria actualizada = categoriaRepository.save(categoria);
        return toResponseDTO(actualizada);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombreEs(),
                categoria.getNombreVal(),
                categoria.getNombreEn(),
                categoria.getOrden()
        );
    }
}