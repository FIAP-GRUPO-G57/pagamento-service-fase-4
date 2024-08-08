package br.com.fiap.pagamento.service.infra.gateways.event.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class PagamentoConfirmedListener {

	@Value("${cloud.aws.queue.name}")
	private String messageQueueTopic;
	private final AmazonSQS amazonSQSClient;

	@Scheduled(fixedDelay = 5000) // executes on every 5 second gap.
	public void receiveMessages() {
		try {
			String queueUrl = amazonSQSClient.getQueueUrl(messageQueueTopic).getQueueUrl();
			ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(queueUrl);
			if (!receiveMessageResult.getMessages().isEmpty()) {
				Message message = receiveMessageResult.getMessages().get(0);
				log.info("Message Body {}", message.getBody());
				processInvoice(message.getBody());
				amazonSQSClient.deleteMessage(queueUrl, message.getReceiptHandle());
			}
		} catch (QueueDoesNotExistException e) {
			log.error("Queue does not exist {}", e.getMessage());
		}
	}

	private void processInvoice(String body) {
		log.info("Processing invoice generation and sending invoice emails from here..");
	}
}
