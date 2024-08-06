package br.com.fiap.pagamento.service.infra.controllers;

import br.com.fiap.pagamento.service.infra.controllers.GlobalExceptionHandler;
import br.com.fiap.pagamento.service.infra.controllers.dto.ErrorDetails;
import br.com.fiap.pagamento.service.infra.controllers.dto.ExceptionDto;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldHandleEntityExistsException() {
        EntityExistsException exception = new EntityExistsException("Entity exists");
        ResponseEntity<ExceptionDto> response = globalExceptionHandler.BadRequestException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Entity exists", response.getBody().getMessage());
    }

    @Test
    void shouldHandleGeneralException() {
        Exception exception = new Exception("General exception");
        ResponseEntity<ErrorDetails> response = (ResponseEntity<ErrorDetails>) globalExceptionHandler.handleGlobalException(exception, Mockito.mock(WebRequest.class));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Dirija-se ao caixa mais próximo para efetuar o pagamento.", response.getBody().getMessage());
    }
}
