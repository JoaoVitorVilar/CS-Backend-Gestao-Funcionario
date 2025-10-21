package com.senai.gestaofuncionarios.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.gestaofuncionarios.dto.FuncionarioRequest;
import com.senai.gestaofuncionarios.dto.FuncionarioResponse;
import com.senai.gestaofuncionarios.exception.BusinessRuleException;
import com.senai.gestaofuncionarios.exception.EmailConflictException;
import com.senai.gestaofuncionarios.exception.ResourceNotFoundException;
import com.senai.gestaofuncionarios.model.Funcionario;
import com.senai.gestaofuncionarios.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repo;

    public FuncionarioService(FuncionarioRepository repo) { this.repo = repo; }

    private FuncionarioResponse toResponse(Funcionario f) {
        return new FuncionarioResponse(
                f.getId(), f.getNome(), f.getEmail(), f.getCargo(),
                f.getSalario(), f.getDataAdmissao(), f.getAtivo()
        );
    }

    // Listagem com filtros opcionais, ordenado por nome
    public List<FuncionarioResponse> listar(String cargo, Boolean ativo) {
        Sort sortNome = Sort.by("nome").ascending();
        List<Funcionario> lista;

        if (cargo != null && ativo != null) {
            lista = repo.findByCargoIgnoreCaseAndAtivo(cargo, ativo, sortNome);
        } else if (cargo != null) {
            lista = repo.findByCargoIgnoreCase(cargo, sortNome);
        } else if (ativo != null) {
            lista = repo.findByAtivo(ativo, sortNome);
        } else {
            lista = repo.findAll(sortNome);
        }

        return lista.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public FuncionarioResponse buscarPorId(Long id) {
        Funcionario f = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com id " + id));
        return toResponse(f);
    }

    // Criação com lógica de email único e reativação
    @Transactional
    public FuncionarioResponse criar(FuncionarioRequest req) {
        // validações adicionais (espaços em branco já tratados no DTO setters)
        validarCamposBasicos(req);

        Optional<Funcionario> existente = repo.findByEmail(req.getEmail());
        if (existente.isPresent()) {
            Funcionario f = existente.get();
            if (Boolean.FALSE.equals(f.getAtivo())) {
                // Reativa e atualiza
                f.setNome(req.getNome());
                f.setCargo(req.getCargo());
                f.setSalario(req.getSalario());
                f.setDataAdmissao(req.getDataAdmissao());
                f.setAtivo(true);
                Funcionario salvo = repo.save(f);
                return toResponse(salvo);
            } else {
                throw new EmailConflictException("Email já cadastrado: " + req.getEmail());
            }
        }

        // novo cadastro
        Funcionario novo = new Funcionario();
        novo.setNome(req.getNome());
        novo.setEmail(req.getEmail());
        novo.setCargo(req.getCargo());
        novo.setSalario(req.getSalario());
        novo.setDataAdmissao(req.getDataAdmissao());
        novo.setAtivo(true);

        Funcionario salvo = repo.save(novo);
        return toResponse(salvo);
    }

    @Transactional
    public FuncionarioResponse atualizar(Long id, FuncionarioRequest req) {
        validarCamposBasicos(req);

        Funcionario existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com id " + id));

        if (!Boolean.TRUE.equals(existente.getAtivo())) {
            throw new BusinessRuleException("Somente funcionários ativos podem ser editados.");
        }

        // se mudou o email, verificar duplicidade
        if (!existente.getEmail().equalsIgnoreCase(req.getEmail())) {
            repo.findByEmail(req.getEmail()).ifPresent(f -> {
                throw new EmailConflictException("Email já cadastrado: " + req.getEmail());
            });
            existente.setEmail(req.getEmail());
        }

        // salário não pode diminuir
        BigDecimal novoSalario = req.getSalario();
        if (novoSalario.compareTo(existente.getSalario()) < 0) {
            throw new BusinessRuleException("Salário não pode ser reduzido.");
        }

        existente.setNome(req.getNome());
        existente.setCargo(req.getCargo());
        existente.setSalario(novoSalario);
        existente.setDataAdmissao(req.getDataAdmissao());

        Funcionario atualizado = repo.save(existente);
        return toResponse(atualizado);
    }

    @Transactional
    public void inativar(Long id) {
        Funcionario existente = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com id " + id));
        existente.setAtivo(false);
        repo.save(existente);
    }

    private void validarCamposBasicos(FuncionarioRequest req) {
        if (req.getNome() == null || req.getNome().trim().isEmpty())
            throw new BusinessRuleException("Nome não pode ser vazio ou apenas espaços.");
        if (req.getNome().trim().length() < 3)
            throw new BusinessRuleException("Nome deve conter pelo menos 3 caracteres.");
        if (req.getEmail() == null || req.getEmail().trim().isEmpty())
            throw new BusinessRuleException("Email não pode ser vazio.");
        if (req.getCargo() == null || req.getCargo().trim().isEmpty())
            throw new BusinessRuleException("Cargo não pode ser vazio.");
        if (req.getSalario() == null || req.getSalario().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessRuleException("Salário deve ser maior que zero.");
        if (req.getDataAdmissao() == null)
            throw new BusinessRuleException("Data de admissão é obrigatória.");
        if (req.getDataAdmissao().isAfter(java.time.LocalDate.now()))
            throw new BusinessRuleException("Data de admissão não pode ser futura.");
    }
}
