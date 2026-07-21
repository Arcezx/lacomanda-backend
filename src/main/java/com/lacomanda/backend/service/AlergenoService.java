package com.lacomanda.backend.service;

import com.lacomanda.backend.dto.AlergenoRequestDTO;
import com.lacomanda.backend.dto.AlergenoResponseDTO;

import java.util.List;

public interface AlergenoService {

    List<AlergenoResponseDTO> findAll();

    AlergenoResponseDTO findById(Long id);

    AlergenoResponseDTO create(AlergenoRequestDTO dto);

    AlergenoResponseDTO update(Long id, AlergenoRequestDTO dto);

    void delete(Long id);
}