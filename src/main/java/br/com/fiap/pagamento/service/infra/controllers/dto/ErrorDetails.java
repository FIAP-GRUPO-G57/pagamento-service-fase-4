package br.com.fiap.pagamento.service.infra.controllers.dto;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
public class ErrorDetails {
    private HttpStatus status;
    private String message;
    private String details;
    private Date timestamp;

    public ErrorDetails(HttpStatus status, String message, String details) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public HttpStatus getStatusCode() {
        return status;
    }
}
