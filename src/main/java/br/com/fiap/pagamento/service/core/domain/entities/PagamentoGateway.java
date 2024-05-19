package br.com.fiap.pagamento.service.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagamentoGateway {
	private Long paymentId;
	private String idExterno;
	private String status;
}
