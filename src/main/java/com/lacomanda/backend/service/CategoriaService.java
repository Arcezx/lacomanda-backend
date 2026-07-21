package com.lacomanda.backend.service;

import com.lacomanda.backend.dto.CategoriaRequestDTO;
import com.lacomanda.backend.dto.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDTO> findAll();

    CategoriaResponseDTO findById(Long id);

    CategoriaResponseDTO create(CategoriaRequestDTO dto);

    CategoriaResponseDTO update(Long id, CategoriaRequestDTO dto);

    void delete(Long id);
}