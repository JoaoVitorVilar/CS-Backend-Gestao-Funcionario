package com.senai.gestaofuncionarios.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class FuncionarioRequest {

    @NotBlank @Size(min = 3)
    private String nome;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String cargo;

    @NotNull @DecimalMin(value = "0.01")
    private BigDecimal salario;

    @NotNull @PastOrPresent
    private LocalDate dataAdmissao;

    // Getters e Setters
    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome.trim(); 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email.trim(); 
    }
    public String getCargo() { 
        return cargo; 
    }
    public void setCargo(String cargo) { 
        this.cargo = cargo.trim(); 
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
}
