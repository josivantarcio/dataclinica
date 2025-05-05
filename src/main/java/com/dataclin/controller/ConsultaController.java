package com.dataclin.controller;

import com.dataclin.dto.consulta.ConsultaDTO;
import com.dataclin.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "APIs de gerenciamento de consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as consultas")
    public ResponseEntity<List<ConsultaDTO>> findAll() {
        return ResponseEntity.ok(consultaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma consulta pelo ID")
    public ResponseEntity<ConsultaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova consulta")
    public ResponseEntity<ConsultaDTO> create(@Valid @RequestBody ConsultaDTO consultaDTO) {
        return ResponseEntity.ok(consultaService.create(consultaDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma consulta existente")
    public ResponseEntity<ConsultaDTO> update(@PathVariable Long id, @Valid @RequestBody ConsultaDTO consultaDTO) {
        return ResponseEntity.ok(consultaService.update(id, consultaDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancela uma consulta")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consultaService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 