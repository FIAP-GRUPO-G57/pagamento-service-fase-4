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
import static org.junit.jupiter.api.Assertions.assertNull;
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
    void shouldSavePaymentWithStatusPaidWhenGatewayStatusIsApproved() {
        PagamentoGateway pagamentoGateway = new PagamentoGateway();
        pagamentoGateway.setStatus("approved");
        pagamentoGateway.setIdExterno("1");

        Pagamento pagamento = PagamentoMock.createPagamento();

        when(paymentGatewayPort.getPayment(anyLong())).thenReturn(pagamentoGateway);
        when(pagamentoRepositoryPort.get(anyLong())).thenReturn(pagamento);

        confirmPagamentoUsecase.confirm(1L, "");

        verify(pagamentoRepositoryPort).save(pagamento);
        assertEquals(StatusEnum.PAGO, pagamento.getStatus());
    }

    @Test
    void shouldSavePaymentWithStatusRejectedWhenGatewayStatusIsNotApproved() {
        PagamentoGateway pagamentoGateway = new PagamentoGateway();
        pagamentoGateway.setStatus("not approved");
        pagamentoGateway.setIdExterno("1");

        Pagamento pagamento = PagamentoMock.createPagamento();

        when(paymentGatewayPort.getPayment(anyLong())).thenReturn(pagamentoGateway);
        when(pagamentoRepositoryPort.get(anyLong())).thenReturn(pagamento);

        confirmPagamentoUsecase.confirm(1L, "");

        verify(pagamentoRepositoryPort).save(pagamento);
        assertEquals(StatusEnum.REJEITADO, pagamento.getStatus());
    }

    @Test
    void shouldNotSavePaymentWhenGatewayDoesNotReturnPayment() {
        when(paymentGatewayPort.getPayment(anyLong())).thenReturn(null);

        confirmPagamentoUsecase.confirm(1L, "");

        verify(pagamentoRepositoryPort, never()).save(any());
    }
}
