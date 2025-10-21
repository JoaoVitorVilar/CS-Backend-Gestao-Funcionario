package com.senai.gestaofuncionarios.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "funcionarios", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false)
    private BigDecimal salario;

    @Column(nullable = false)
    private LocalDate dataAdmissao;

    @Column(nullable = false)
    private Boolean ativo = true;

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
