package br.com.fiap.pagamento.service.core.usecases.ports.gateways;

import br.com.fiap.pagamento.service.core.domain.entities.PagamentoGateway;

import java.math.BigDecimal;

public interface PaymentGatewayPort {
	String create(String reference, BigDecimal totalAmount) ;
	PagamentoGateway getPayment(Long id);
}
