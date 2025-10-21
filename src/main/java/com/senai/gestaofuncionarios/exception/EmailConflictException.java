package com.senai.gestaofuncionarios.exception;
public class EmailConflictException extends RuntimeException {
    public EmailConflictException(String mensagem) { super(mensagem); }
}
