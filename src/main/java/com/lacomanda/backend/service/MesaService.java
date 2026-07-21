package com.lacomanda.backend.service;

import com.lacomanda.backend.dto.MesaRequestDTO;
import com.lacomanda.backend.dto.MesaResponseDTO;

import java.util.List;

public interface MesaService {

    List<MesaResponseDTO> findAll();

    MesaResponseDTO findById(Long id);

    MesaResponseDTO create(MesaRequestDTO dto);

    MesaResponseDTO update(Long id, MesaRequestDTO dto);

    void delete(Long id);
}