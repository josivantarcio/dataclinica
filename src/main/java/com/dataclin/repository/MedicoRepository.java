package com.dataclin.repository;

import com.dataclin.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByEmail(String email);
    Optional<Medico> findByCrm(String crm);
    boolean existsByEmail(String email);
    boolean existsByCrm(String crm);
    List<Medico> findByAtivoTrue();
    List<Medico> findByEspecialidadeAndAtivoTrue(String especialidade);
} 