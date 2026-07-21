package com.lacomanda.backend.service.impl;

import com.lacomanda.backend.dto.AlergenoResponseDTO;
import com.lacomanda.backend.dto.IngredienteDTO;
import com.lacomanda.backend.dto.ProductoRequestDTO;
import com.lacomanda.backend.dto.ProductoResponseDTO;
import com.lacomanda.backend.entity.Alergeno;
import com.lacomanda.backend.entity.Categoria;
import com.lacomanda.backend.entity.Ingrediente;
import com.lacomanda.backend.entity.Producto;
import com.lacomanda.backend.exception.ResourceNotFoundException;
import com.lacomanda.backend.repository.AlergenoRepository;
import com.lacomanda.backend.repository.CategoriaRepository;
import com.lacomanda.backend.repository.ProductoRepository;
import com.lacomanda.backend.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final AlergenoRepository alergenoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> findAllPaginado(Pageable pageable) {
        return productoRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> findByCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return toResponseDTO(producto);
    }

    @Override
    @Transactional
    public ProductoResponseDTO create(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        mapRequestToEntity(dto, producto);

        Producto guardado = productoRepository.save(producto);
        return toResponseDTO(guardado);
    }

    @Override
    @Transactional
    public ProductoResponseDTO update(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        mapRequestToEntity(dto, producto);

        Producto actualizado = productoRepository.save(producto);
        return toResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }


    private void mapRequestToEntity(ProductoRequestDTO dto, Producto producto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con id: " + dto.getCategoriaId()));

        producto.setCategoria(categoria);
        producto.setNombreEs(dto.getNombreEs());
        producto.setNombreVal(dto.getNombreVal());
        producto.setNombreEn(dto.getNombreEn());
        producto.setDescripcionEs(dto.getDescripcionEs());
        producto.setDescripcionVal(dto.getDescripcionVal());
        producto.setDescripcionEn(dto.getDescripcionEn());
        producto.setPrecio(dto.getPrecio());
        producto.setFoto(dto.getFoto());
        producto.setDisponible(dto.isDisponible());

        if (dto.getAlergenoIds() != null) {
            Set<Alergeno> alergenos = new HashSet<>(alergenoRepository.findAllById(dto.getAlergenoIds()));
            producto.setAlergenos(alergenos);
        } else {
            producto.setAlergenos(new HashSet<>());
        }

        producto.getIngredientes().clear();
        if (dto.getIngredientes() != null) {
            for (IngredienteDTO ingDTO : dto.getIngredientes()) {
                Ingrediente ingrediente = new Ingrediente();
                ingrediente.setNombre(ingDTO.getNombre());
                ingrediente.setProducto(producto);
                producto.getIngredientes().add(ingrediente);
            }
        }
    }

    private ProductoResponseDTO toResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setCategoriaId(producto.getCategoria().getId());
        dto.setCategoriaNombreEs(producto.getCategoria().getNombreEs());
        dto.setNombreEs(producto.getNombreEs());
        dto.setNombreVal(producto.getNombreVal());
        dto.setNombreEn(producto.getNombreEn());
        dto.setDescripcionEs(producto.getDescripcionEs());
        dto.setDescripcionVal(producto.getDescripcionVal());
        dto.setDescripcionEn(producto.getDescripcionEn());
        dto.setPrecio(producto.getPrecio());
        dto.setFoto(producto.getFoto());
        dto.setDisponible(producto.isDisponible());

        List<AlergenoResponseDTO> alergenosDTO = producto.getAlergenos().stream()
                .map(a -> new AlergenoResponseDTO(a.getId(), a.getNombreEs(), a.getNombreVal(), a.getNombreEn(), a.getIcono()))
                .toList();
        dto.setAlergenos(alergenosDTO);

        List<IngredienteDTO> ingredientesDTO = producto.getIngredientes().stream()
                .map(i -> new IngredienteDTO(i.getId(), i.getNombre()))
                .toList();
        dto.setIngredientes(ingredientesDTO);

        return dto;
    }
}