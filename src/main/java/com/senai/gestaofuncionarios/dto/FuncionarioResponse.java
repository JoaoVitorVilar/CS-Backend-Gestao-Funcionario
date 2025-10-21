package com.senai.gestaofuncionarios.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FuncionarioResponse {
    private Long id;
    private String nome;
    private String email;
    private String cargo;
    private BigDecimal salario;
    private LocalDate dataAdmissao;
    private Boolean ativo;

    public FuncionarioResponse() {}
    public FuncionarioResponse(Long id, String nome, String email, String cargo,
                               BigDecimal salario, LocalDate dataAdmissao, Boolean ativo) {
        this.id = id; this.nome = nome; this.email = email; this.cargo = cargo;
        this.salario = salario; this.dataAdmissao = dataAdmissao; this.ativo = ativo;
    }

    // Getters e Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public String getCargo() { 
        return cargo; 
    }
    public void setCargo(String cargo) { 
        this.cargo = cargo; 
    }
    public BigDecimal getSalario() { 
        return salario; 
    }
    public void setSalario(BigDecimal salario) { 
        this.salario = salario; 
    }
    public LocalDate getDataAdmissao() { 
        return dataAdmissao; 
    }
    public void setDataAdmissao(LocalDate dataAdmissao) { 
        this.dataAdmissao = dataAdmissao; 
    }
    public Boolean getAtivo() { 
        return ativo; 
    }
    public void setAtivo(Boolean ativo) { 
        this.ativo = ativo; 
    }
}
