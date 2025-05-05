package com.dataclin.controller;

import com.dataclin.dto.medico.MedicoDTO;
import com.dataclin.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "APIs de gerenciamento de médicos")
public class MedicoController {

    private final MedicoService medicoService;

    @Autowired
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os médicos ativos")
    public ResponseEntity<List<MedicoDTO>> findAll() {
        return ResponseEntity.ok(medicoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um médico pelo ID")
    public ResponseEntity<MedicoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo médico")
    public ResponseEntity<MedicoDTO> create(@Valid @RequestBody MedicoDTO medicoDTO) {
        return ResponseEntity.ok(medicoService.create(medicoDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um médico existente")
    public ResponseEntity<MedicoDTO> update(@PathVariable Long id, @Valid @RequestBody MedicoDTO medicoDTO) {
        return ResponseEntity.ok(medicoService.update(id, medicoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um médico (soft delete)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 