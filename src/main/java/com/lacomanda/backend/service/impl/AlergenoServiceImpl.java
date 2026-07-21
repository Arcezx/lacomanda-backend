package com.lacomanda.backend.service.impl;

import com.lacomanda.backend.dto.AlergenoRequestDTO;
import com.lacomanda.backend.dto.AlergenoResponseDTO;
import com.lacomanda.backend.entity.Alergeno;
import com.lacomanda.backend.exception.ResourceNotFoundException;
import com.lacomanda.backend.repository.AlergenoRepository;
import com.lacomanda.backend.service.AlergenoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlergenoServiceImpl implements AlergenoService {

    private final AlergenoRepository alergenoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AlergenoResponseDTO> findAll() {
        return alergenoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlergenoResponseDTO findById(Long id) {
        Alergeno alergeno = alergenoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alérgeno no encontrado con id: " + id));
        return toResponseDTO(alergeno);
    }

    @Override
    @Transactional
    public AlergenoResponseDTO create(AlergenoRequestDTO dto) {
        Alergeno alergeno = new Alergeno();
        alergeno.setNombreEs(dto.getNombreEs());
        alergeno.setNombreVal(dto.getNombreVal());
        alergeno.setNombreEn(dto.getNombreEn());
        alergeno.setIcono(dto.getIcono());

        Alergeno guardado = alergenoRepository.save(alergeno);
        return toResponseDTO(guardado);
    }

    @Override
    @Transactional
    public AlergenoResponseDTO update(Long id, AlergenoRequestDTO dto) {
        Alergeno alergeno = alergenoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alérgeno no encontrado con id: " + id));

        alergeno.setNombreEs(dto.getNombreEs());
        alergeno.setNombreVal(dto.getNombreVal());
        alergeno.setNombreEn(dto.getNombreEn());
        alergeno.setIcono(dto.getIcono());

        Alergeno actualizado = alergenoRepository.save(alergeno);
        return toResponseDTO(actualizado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!alergenoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alérgeno no encontrado con id: " + id);
        }
        alergenoRepository.deleteById(id);
    }

    private AlergenoResponseDTO toResponseDTO(Alergeno alergeno) {
        return new AlergenoResponseDTO(
                alergeno.getId(),
                alergeno.getNombreEs(),
                alergeno.getNombreVal(),
                alergeno.getNombreEn(),
                alergeno.getIcono()
        );
    }
}