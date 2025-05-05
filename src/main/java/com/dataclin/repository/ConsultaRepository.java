package com.dataclin.repository;

import com.dataclin.model.Consulta;
import com.dataclin.model.Medico;
import com.dataclin.model.Paciente;
import com.dataclin.model.Consulta.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByMedicoAndDataHoraBetween(Medico medico, LocalDateTime inicio, LocalDateTime fim);
    List<Consulta> findByPacienteAndDataHoraBetween(Paciente paciente, LocalDateTime inicio, LocalDateTime fim);
    List<Consulta> findByMedicoAndStatus(Medico medico, StatusConsulta status);
    List<Consulta> findByPacienteAndStatus(Paciente paciente, StatusConsulta status);
    boolean existsByMedicoAndDataHoraAndStatusNot(Medico medico, LocalDateTime dataHora, StatusConsulta status);
    boolean existsByPacienteAndDataHoraAndStatusNot(Paciente paciente, LocalDateTime dataHora, StatusConsulta status);
    boolean existsByMedicoAndDataHora(Medico medico, LocalDateTime dataHora);
    boolean existsByPacienteAndDataHora(Paciente paciente, LocalDateTime dataHora);
} 