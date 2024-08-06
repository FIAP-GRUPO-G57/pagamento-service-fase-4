package br.com.fiap.pagamento.service.infra.gateway.event.sqs;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.infra.gateways.event.sqs.PagamentoConfirmedPublisher;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PagamentoConfirmedPublisherTest {

    @Mock
    private AmazonSQS amazonSQS;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PagamentoConfirmedPublisher pagamentoConfirmedPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldPublishEventWhenNotifyIsCalled() throws Exception {
        Pagamento pagamento = new Pagamento();
        String paymentJson = "{}";
        when(objectMapper.writeValueAsString(pagamento)).thenReturn(paymentJson);

        pagamentoConfirmedPublisher.notify(pagamento);

        verify(amazonSQS).sendMessage(any(SendMessageRequest.class));
    }

    @Test
    void shouldNotPublishEventWhenJsonProcessingExceptionOccurs()  throws Exception{
        Pagamento pagamento = new Pagamento();
        when(objectMapper.writeValueAsString(pagamento)).thenThrow(new RuntimeException());
        Exception exception = assertThrows(Exception.class,
                ()->{pagamentoConfirmedPublisher.notify(pagamento);} );

        verify(amazonSQS, never()).sendMessage(any(SendMessageRequest.class));
    }
}
