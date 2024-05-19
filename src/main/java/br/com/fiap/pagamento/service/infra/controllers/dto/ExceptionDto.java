package br.com.fiap.pagamento.service.infra.controllers.dto;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ExceptionDto{
    private HttpStatus status;
    private String message;

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return status;
    }
}
