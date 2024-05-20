package br.com.fiap.pagamento.service.infra.gateway.rest.mp;

import br.com.fiap.pagamento.mock.PagamentoMock;
import br.com.fiap.pagamento.service.core.domain.entities.PagamentoGateway;
import br.com.fiap.pagamento.service.infra.gateways.rest.mp.MercadoPagoRestClient;
import br.com.fiap.pagamento.service.infra.gateways.rest.mp.dto.order.QuickResponse;
import br.com.fiap.pagamento.service.infra.gateways.rest.mp.dto.payment.PaymentsResponse;
import br.com.fiap.pagamento.service.main.configuration.GatewayPaymentConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MercadoPagoRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GatewayPaymentConfiguration gatewayPaymentConfiguration;

    @InjectMocks
    private MercadoPagoRestClient mercadoPagoRestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnQrDataWhenCreateIsCalled() {
        String expectedQrData = "qrData";
        String inStoreOrderId = "inStoreOrderId";
        when(restTemplate.postForEntity(any(), any(), any()))
                .thenReturn(ResponseEntity.ok(new QuickResponse(expectedQrData,inStoreOrderId)));

        String qrData = mercadoPagoRestClient.create("reference", BigDecimal.ONE);

        assertEquals(expectedQrData, qrData);
    }

    @Test
    void shouldReturnPagamentoGatewayWhenGetPaymentIsCalled() {

        PaymentsResponse paymentsResponse = PaymentsResponse.builder().id(PagamentoMock.GATEWAY_ID).externalReference(PagamentoMock.ID.toString()).status(PagamentoMock.statusPago.name()).build();

        PagamentoGateway expectedPagamentoGateway = new PagamentoGateway();
        expectedPagamentoGateway.setIdExterno(PagamentoMock.ID.toString());

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(HttpEntity.class),
                ArgumentMatchers.<Class<PaymentsResponse>>any()))
                .thenReturn(ResponseEntity.ok(paymentsResponse));

        PagamentoGateway pagamentoGateway = mercadoPagoRestClient.getPayment(PagamentoMock.GATEWAY_ID);

        assertEquals(expectedPagamentoGateway.getIdExterno(), pagamentoGateway.getIdExterno());
    }

    @Test
    void shouldReturnNullWhenGetPaymentIsCalledAndExceptionOccurs() {
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(HttpEntity.class),
                ArgumentMatchers.<Class<PaymentsResponse>>any()))
                .thenThrow(RuntimeException.class);

        PagamentoGateway pagamentoGateway = mercadoPagoRestClient.getPayment(1L);

        assertNull(pagamentoGateway);
    }

}
