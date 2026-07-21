package com.lacomanda.backend.service;

import com.lacomanda.backend.dto.ProductoRequestDTO;
import com.lacomanda.backend.dto.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    List<ProductoResponseDTO> findAll();

    List<ProductoResponseDTO> findByCategoria(Long categoriaId);

    ProductoResponseDTO findById(Long id);

    ProductoResponseDTO create(ProductoRequestDTO dto);

    ProductoResponseDTO update(Long id, ProductoRequestDTO dto);

    void delete(Long id);
}