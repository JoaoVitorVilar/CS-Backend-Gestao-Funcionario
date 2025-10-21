package com.senai.gestaofuncionarios.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensagem) { super(mensagem); }
}
