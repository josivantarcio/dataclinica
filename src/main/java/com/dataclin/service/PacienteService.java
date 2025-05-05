package com.dataclin.service;

import com.dataclin.dto.paciente.PacienteDTO;
import com.dataclin.model.Paciente;
import com.dataclin.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional(readOnly = true)
    public List<PacienteDTO> findAll() {
        return pacienteRepository.findByAtivoTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PacienteDTO findById(Long id) {
        return pacienteRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }

    @Transactional
    public PacienteDTO create(PacienteDTO dto) {
        if (pacienteRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (pacienteRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        Paciente paciente = toEntity(dto);
        paciente.setAtivo(true);
        return toDTO(pacienteRepository.save(paciente));
    }

    @Transactional
    public PacienteDTO update(Long id, PacienteDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        if (!paciente.getEmail().equals(dto.getEmail()) && pacienteRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (!paciente.getCpf().equals(dto.getCpf()) && pacienteRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        updateEntity(paciente, dto);
        return toDTO(pacienteRepository.save(paciente));
    }

    @Transactional
    public void delete(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
        paciente.setAtivo(false);
        pacienteRepository.save(paciente);
    }

    private PacienteDTO toDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNome(paciente.getNome());
        dto.setEmail(paciente.getEmail());
        dto.setCpf(paciente.getCpf());
        dto.setTelefone(paciente.getTelefone());
        dto.setEndereco(paciente.getEndereco());
        dto.setAtivo(paciente.isAtivo());
        return dto;
    }

    private Paciente toEntity(PacienteDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setId(dto.getId());
        paciente.setNome(dto.getNome());
        paciente.setEmail(dto.getEmail());
        paciente.setCpf(dto.getCpf());
        paciente.setTelefone(dto.getTelefone());
        paciente.setEndereco(dto.getEndereco());
        paciente.setAtivo(dto.isAtivo());
        return paciente;
    }

    private void updateEntity(Paciente paciente, PacienteDTO dto) {
        paciente.setNome(dto.getNome());
        paciente.setEmail(dto.getEmail());
        paciente.setCpf(dto.getCpf());
        paciente.setTelefone(dto.getTelefone());
        paciente.setEndereco(dto.getEndereco());
    }
} 