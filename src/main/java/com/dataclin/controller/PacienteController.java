package com.dataclin.controller;

import com.dataclin.dto.paciente.PacienteDTO;
import com.dataclin.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "APIs de gerenciamento de pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os pacientes ativos")
    public ResponseEntity<List<PacienteDTO>> findAll() {
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um paciente pelo ID")
    public ResponseEntity<PacienteDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo paciente")
    public ResponseEntity<PacienteDTO> create(@Valid @RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(pacienteService.create(pacienteDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um paciente existente")
    public ResponseEntity<PacienteDTO> update(@PathVariable Long id, @Valid @RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(pacienteService.update(id, pacienteDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um paciente (soft delete)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 