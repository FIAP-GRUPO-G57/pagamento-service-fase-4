package br.com.fiap.pagamento.service.core.usecases.pagamento;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.usecases.ports.repositories.PagamentoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetPagamentoByIdUsecaseTest {

    @Mock
    private PagamentoRepositoryPort pagamentoRepositoryPort;

    @InjectMocks
    private GetPagamentoByIdUsecase getPagamentoByIdUsecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCorrectPagamentoWhenIdExists() {
        Long id = 1L;
        Pagamento expectedPagamento = new Pagamento();
        when(pagamentoRepositoryPort.get(id)).thenReturn(expectedPagamento);

        Pagamento result = getPagamentoByIdUsecase.get(id);

        assertEquals(expectedPagamento, result);
    }

    @Test
    void shouldReturnNullWhenIdDoesNotExist() {
        Long id = 1L;
        when(pagamentoRepositoryPort.get(id)).thenReturn(null);

        Pagamento result = getPagamentoByIdUsecase.get(id);

        assertNull(result);
    }
}
