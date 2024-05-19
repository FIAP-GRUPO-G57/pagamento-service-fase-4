package br.com.fiap.pagamento.service.infra.controllers;

import br.com.fiap.pagamento.service.infra.controllers.dto.ExceptionDto;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionDto> BadRequestException(EntityExistsException e){
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> generalException(Exception e){
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR, "Dirija-se ao caixa mais próximo para efetuar o pagamento.")
        );
    }
}
