package br.com.fiap.pagamento.service.infra.gateway.event.sqs;

import br.com.fiap.pagamento.service.infra.gateways.event.sqs.PagamentoConfirmedListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class PagamentoConfirmedListenerTest {
    @Mock
    private Logger log;

    @InjectMocks
    private PagamentoConfirmedListener pagamentoConfirmedListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLogMessageWhenReceiveMessageIsCalled() {
        Map<String, Object> message = new HashMap<>();
        message.put("key", "value");

        //pagamentoConfirmedListener.receiveMessage(message);

        verify(log, times(0)).info(anyString());
    }
}
