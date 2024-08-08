package br.com.fiap.pagamento.service.infra.gateways.event.sqs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class PagamentoConfirmedListener  {
	@SqsListener("${cloud.aws.queue.name}")
	public void receiveMessage(Map<String, Object> message) {
		log.info("SQS Message Received : {}", message);
	}
}
