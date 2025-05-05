package com.dataclin.service;

import com.dataclin.dto.medico.MedicoDTO;
import com.dataclin.model.Medico;
import com.dataclin.repository.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    @Autowired
    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Transactional(readOnly = true)
    public List<MedicoDTO> findAll() {
        return medicoRepository.findByAtivoTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MedicoDTO findById(Long id) {
        return medicoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
    }

    @Transactional
    public MedicoDTO create(MedicoDTO dto) {
        if (medicoRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (medicoRepository.existsByCrm(dto.getCrm())) {
            throw new IllegalArgumentException("CRM já cadastrado");
        }

        Medico medico = toEntity(dto);
        medico.setAtivo(true);
        return toDTO(medicoRepository.save(medico));
    }

    @Transactional
    public MedicoDTO update(Long id, MedicoDTO dto) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));

        if (!medico.getEmail().equals(dto.getEmail()) && medicoRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (!medico.getCrm().equals(dto.getCrm()) && medicoRepository.existsByCrm(dto.getCrm())) {
            throw new IllegalArgumentException("CRM já cadastrado");
        }

        updateEntity(medico, dto);
        return toDTO(medicoRepository.save(medico));
    }

    @Transactional
    public void delete(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
        medico.setAtivo(false);
        medicoRepository.save(medico);
    }

    private MedicoDTO toDTO(Medico medico) {
        MedicoDTO dto = new MedicoDTO();
        dto.setId(medico.getId());
        dto.setNome(medico.getNome());
        dto.setEmail(medico.getEmail());
        dto.setCrm(medico.getCrm());
        dto.setEspecialidade(medico.getEspecialidade());
        dto.setTelefone(medico.getTelefone());
        dto.setEndereco(medico.getEndereco());
        dto.setAtivo(medico.isAtivo());
        return dto;
    }

    private Medico toEntity(MedicoDTO dto) {
        Medico medico = new Medico();
        medico.setId(dto.getId());
        medico.setNome(dto.getNome());
        medico.setEmail(dto.getEmail());
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());
        medico.setTelefone(dto.getTelefone());
        medico.setEndereco(dto.getEndereco());
        medico.setAtivo(dto.isAtivo());
        return medico;
    }

    private void updateEntity(Medico medico, MedicoDTO dto) {
        medico.setNome(dto.getNome());
        medico.setEmail(dto.getEmail());
        medico.setCrm(dto.getCrm());
        medico.setEspecialidade(dto.getEspecialidade());
        medico.setTelefone(dto.getTelefone());
        medico.setEndereco(dto.getEndereco());
    }
} 