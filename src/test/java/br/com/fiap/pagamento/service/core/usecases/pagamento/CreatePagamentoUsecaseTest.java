package br.com.fiap.pagamento.service.core.usecases.pagamento;

import br.com.fiap.pagamento.mock.PagamentoMock;
import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;
import br.com.fiap.pagamento.service.core.usecases.ports.gateways.PaymentGatewayPort;
import br.com.fiap.pagamento.service.core.usecases.ports.repositories.PagamentoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreatePagamentoUsecaseTest {

    @Mock
    private PaymentGatewayPort paymentGatewayPort;

    @Mock
    private PagamentoRepositoryPort pagamentoRepositoryPort;

    @InjectMocks
    private CreatePagamentoUsecase createPagamentoUsecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateNewPaymentWhenReferenceNotFound() {
        Pagamento pagamento = PagamentoMock.createPagamento();

        when(pagamentoRepositoryPort.save(any())).thenReturn(PagamentoMock.createPagamento());
        when(pagamentoRepositoryPort.findTopByReference(any())).thenReturn(null);
        when(paymentGatewayPort.create(any(), any())).thenReturn("qrcode");

        Pagamento result = createPagamentoUsecase.create(pagamento, null);

        assertEquals(StatusEnum.PENDING, result.getStatus());
        assertEquals("qrcode", result.getQrcode());
    }

    @Test
    void shouldReturnExistingPaymentWhenReferenceFound() {
        Pagamento pagamento = new Pagamento();

        when(pagamentoRepositoryPort.findTopByReference(any())).thenReturn(pagamento);

        Pagamento result = createPagamentoUsecase.create(pagamento, null);

        verify(pagamentoRepositoryPort, never()).save(any());
        assertEquals(pagamento, result);
    }
}
