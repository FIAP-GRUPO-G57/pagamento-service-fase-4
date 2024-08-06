package br.com.fiap.pagamento.service.infra.controllers;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
