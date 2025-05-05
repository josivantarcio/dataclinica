package com.dataclin.repository;

import com.dataclin.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByEmail(String email);
    Optional<Paciente> findByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    List<Paciente> findByAtivoTrue();
    List<Paciente> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
} 