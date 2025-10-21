package com.senai.gestaofuncionarios.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senai.gestaofuncionarios.dto.FuncionarioRequest;
import com.senai.gestaofuncionarios.dto.FuncionarioResponse;
import com.senai.gestaofuncionarios.service.FuncionarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FuncionarioResponse>> listar(
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) Boolean ativo) {
        return ResponseEntity.ok(service.listar(cargo, ativo));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping("/criar")
    public ResponseEntity<FuncionarioResponse> criar(@Valid @RequestBody FuncionarioRequest req) {
        FuncionarioResponse criado = service.criar(req);
        // se foi reativado, criado.id já existe; porém retornamos 201 para novo cadastro
        URI location = URI.create("/api/funcionarios/" + criado.getId());
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FuncionarioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioRequest req) {
        return ResponseEntity.ok(service.atualizar(id, req));
    }

    @PatchMapping("/inativar/{id}")
    public ResponseEntity<?> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
