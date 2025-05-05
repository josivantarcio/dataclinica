package com.dataclin.service;

import com.dataclin.dto.consulta.ConsultaDTO;
import com.dataclin.model.Consulta;
import com.dataclin.model.Medico;
import com.dataclin.model.Paciente;
import com.dataclin.repository.ConsultaRepository;
import com.dataclin.repository.MedicoRepository;
import com.dataclin.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository,
                          MedicoRepository medicoRepository,
                          PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional(readOnly = true)
    public List<ConsultaDTO> findAll() {
        return consultaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConsultaDTO findById(Long id) {
        return consultaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));
    }

    @Transactional
    public ConsultaDTO create(ConsultaDTO dto) {
        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
        
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // Verificar disponibilidade do médico
        if (consultaRepository.existsByMedicoAndDataHora(medico, dto.getDataHora())) {
            throw new IllegalArgumentException("Médico já possui consulta agendada neste horário");
        }

        // Verificar se o paciente já tem consulta no mesmo horário
        if (consultaRepository.existsByPacienteAndDataHora(paciente, dto.getDataHora())) {
            throw new IllegalArgumentException("Paciente já possui consulta agendada neste horário");
        }

        Consulta consulta = toEntity(dto);
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setStatus(Consulta.StatusConsulta.AGENDADA);
        
        return toDTO(consultaRepository.save(consulta));
    }

    @Transactional
    public ConsultaDTO update(Long id, ConsultaDTO dto) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));

        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
        
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // Verificar disponibilidade do médico (exceto para a própria consulta)
        if (!consulta.getMedico().equals(medico) && 
            consultaRepository.existsByMedicoAndDataHora(medico, dto.getDataHora())) {
            throw new IllegalArgumentException("Médico já possui consulta agendada neste horário");
        }

        // Verificar se o paciente já tem consulta no mesmo horário (exceto para a própria consulta)
        if (!consulta.getPaciente().equals(paciente) && 
            consultaRepository.existsByPacienteAndDataHora(paciente, dto.getDataHora())) {
            throw new IllegalArgumentException("Paciente já possui consulta agendada neste horário");
        }

        updateEntity(consulta, dto);
        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        
        return toDTO(consultaRepository.save(consulta));
    }

    @Transactional
    public void delete(Long id) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));
        
        if (consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível cancelar uma consulta passada");
        }
        
        consulta.setStatus(Consulta.StatusConsulta.CANCELADA);
        consultaRepository.save(consulta);
    }

    private ConsultaDTO toDTO(Consulta consulta) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setId(consulta.getId());
        dto.setMedicoId(consulta.getMedico().getId());
        dto.setPacienteId(consulta.getPaciente().getId());
        dto.setDataHora(consulta.getDataHora());
        dto.setObservacoes(consulta.getObservacoes());
        dto.setStatus(consulta.getStatus().name());
        return dto;
    }

    private Consulta toEntity(ConsultaDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setId(dto.getId());
        consulta.setDataHora(dto.getDataHora());
        consulta.setObservacoes(dto.getObservacoes());
        if (dto.getStatus() != null) {
            consulta.setStatus(Consulta.StatusConsulta.valueOf(dto.getStatus()));
        }
        return consulta;
    }

    private void updateEntity(Consulta consulta, ConsultaDTO dto) {
        consulta.setDataHora(dto.getDataHora());
        consulta.setObservacoes(dto.getObservacoes());
        if (dto.getStatus() != null) {
            consulta.setStatus(Consulta.StatusConsulta.valueOf(dto.getStatus()));
        }
    }
} 