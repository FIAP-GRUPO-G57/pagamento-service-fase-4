package br.com.fiap.pagamento.service.infra.gateways.event.sqs;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.usecases.ports.event.PagamentoConfirmedEventPort;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class PagamentoConfirmedPublisher implements PagamentoConfirmedEventPort {

	@Autowired
	private AmazonSQS amazonSQS;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void notify(Pagamento pagamento) throws Exception {
		log.info("Generating event : {}", pagamento);
		SendMessageRequest sendMessageRequest = null;
		try {
			sendMessageRequest = new SendMessageRequest().withQueueUrl("http://localstack:4566/000000000000/payment-queue.fifo")
					.withMessageBody(objectMapper.writeValueAsString(pagamento))
					.withMessageGroupId("SampleMessage")
					.withMessageDeduplicationId(UUID.randomUUID().toString().replace("-", ""));
			amazonSQS.sendMessage(sendMessageRequest);
			log.info("Event has been published in SQS.");
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
		} catch (Exception e) {
			log.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
			throw new Exception("Exception notify", e);
		}
	}
}
