package br.com.fiap.pagamento.service.core.usecases.pagamento;

import br.com.fiap.pagamento.mock.PagamentoMock;
import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.domain.entities.PagamentoGateway;
import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;
import br.com.fiap.pagamento.service.core.usecases.ports.event.PagamentoConfirmedEventPort;
import br.com.fiap.pagamento.service.core.usecases.ports.gateways.PaymentGatewayPort;
import br.com.fiap.pagamento.service.core.usecases.ports.repositories.PagamentoRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConfirmPagamentoUsecaseTest {

    @Mock
    PaymentGatewayPort paymentGatewayPort;

    @Mock
    PagamentoRepositoryPort pagamentoRepositoryPort;

    @Mock
    PagamentoConfirmedEventPort pagamentoConfirmedEventPort;

    @InjectMocks
    ConfirmPagamentoUsecase confirmPagamentoUsecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldConfirmPaymentWhenTypeIsNotSandbox() throws Exception {
        PagamentoGateway pagamentoGateway = new PagamentoGateway();
        pagamentoGateway.setStatus("approved");
        pagamentoGateway.setIdExterno("1");

        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);

        when(paymentGatewayPort.getPayment(anyLong())).thenReturn(pagamentoGateway);
        when(pagamentoRepositoryPort.get(anyLong())).thenReturn(pagamento);

        Pagamento result = confirmPagamentoUsecase.confirm(1L, "");

        verify(pagamentoRepositoryPort).save(pagamento);
        verify(pagamentoConfirmedEventPort).notify(pagamento);
        assertEquals(StatusEnum.PAGO, result.getStatus());
    }

    @Test
    void shouldConfirmSandboxPaymentRejeitado() throws Exception {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);

        when(pagamentoRepositoryPort.get(anyLong())).thenReturn(pagamento);

        Pagamento result = confirmPagamentoUsecase.confirm(1L, "sandbox");

        verify(pagamentoRepositoryPort).save(pagamento);
        verify(pagamentoConfirmedEventPort).notify(pagamento);
        assertEquals(StatusEnum.REJEITADO, result.getStatus());
    }

    @Test
    void shouldConfirmSandboxPaymentPago() throws Exception {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(2L);

        when(pagamentoRepositoryPort.get(anyLong())).thenReturn(pagamento);

        Pagamento result = confirmPagamentoUsecase.confirm(2L, "sandbox");

        verify(pagamentoRepositoryPort).save(pagamento);
        verify(pagamentoConfirmedEventPort).notify(pagamento);
        assertEquals(StatusEnum.PAGO, result.getStatus());
    }

    @Test
    void shouldNotConfirmPaymentWhenGatewayDoesNotReturnPayment() throws Exception {
        when(paymentGatewayPort.getPayment(anyLong())).thenReturn(null);

        Pagamento result = confirmPagamentoUsecase.confirm(1L, "");

        verify(pagamentoRepositoryPort, never()).save(any());
        verify(pagamentoConfirmedEventPort, never()).notify(any());
        assertEquals(null, result);
    }
}
