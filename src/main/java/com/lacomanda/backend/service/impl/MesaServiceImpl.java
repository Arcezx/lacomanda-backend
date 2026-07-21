package com.lacomanda.backend.service.impl;

import com.lacomanda.backend.dto.MesaRequestDTO;
import com.lacomanda.backend.dto.MesaResponseDTO;
import com.lacomanda.backend.entity.Mesa;
import com.lacomanda.backend.exception.NegocioException;
import com.lacomanda.backend.exception.ResourceNotFoundException;
import com.lacomanda.backend.repository.MesaRepository;
import com.lacomanda.backend.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MesaResponseDTO> findAll() {
        return mesaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MesaResponseDTO findById(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + id));
        return toResponseDTO(mesa);
    }

    @Override
    @Transactional
    public MesaResponseDTO create(MesaRequestDTO dto) {
        if (mesaRepository.findByNumero(dto.getNumero()).isPresent()) {
            throw new NegocioException("Ya existe una mesa con el número " + dto.getNumero());
        }

        Mesa mesa = new Mesa();
        mesa.setNumero(dto.getNumero());
        mesa.setCapacidad(dto.getCapacidad());

        Mesa guardada = mesaRepository.save(mesa);
        return toResponseDTO(guardada);
    }

    @Override
    @Transactional
    public MesaResponseDTO update(Long id, MesaRequestDTO dto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + id));

        mesaRepository.findByNumero(dto.getNumero()).ifPresent(otraMesa -> {
            if (!otraMesa.getId().equals(id)) {
                throw new NegocioException("Ya existe otra mesa con el número " + dto.getNumero());
            }
        });

        mesa.setNumero(dto.getNumero());
        mesa.setCapacidad(dto.getCapacidad());

        Mesa actualizada = mesaRepository.save(mesa);
        return toResponseDTO(actualizada);
    }
    @Override
    @Transactional
    public void delete(Long id) {
        if (!mesaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mesa no encontrada con id: " + id);
        }
        mesaRepository.deleteById(id);
    }

    private MesaResponseDTO toResponseDTO(Mesa mesa) {
        return new MesaResponseDTO(
                mesa.getId(),
                mesa.getNumero(),
                mesa.getCapacidad(),
                mesa.getQrCode()
        );
    }
}