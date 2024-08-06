package br.com.fiap.pagamento.service.core.domain.dto;

import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PagamentoDto {
	private Long reference;
	private BigDecimal valor;

	private Long id;
	private StatusEnum status;
	private String qrcode;

	private Long gatewayId;
}
