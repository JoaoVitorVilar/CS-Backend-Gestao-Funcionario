package com.senai.gestaofuncionarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.senai.gestaofuncionarios.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByEmail(String email);
    List<Funcionario> findByCargoIgnoreCase(String cargo, Sort sort);
    List<Funcionario> findByAtivo(Boolean ativo, Sort sort);
    List<Funcionario> findByCargoIgnoreCaseAndAtivo(String cargo, Boolean ativo, Sort sort);
}
