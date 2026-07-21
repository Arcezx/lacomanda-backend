package com.lacomanda.backend.service;

import com.lacomanda.backend.dto.UsuarioRequestDTO;
import com.lacomanda.backend.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponseDTO> findAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO create(UsuarioRequestDTO dto);

    UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto);

    void delete(Long id);
}