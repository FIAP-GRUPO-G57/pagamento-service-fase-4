package br.com.fiap.pagamento.service.core.domain.entities;

import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagamento {
    private Long reference;
    private BigDecimal valor;

    private Long id;
    private Long gatewayId;
    private String qrcode;
    private StatusEnum status;
}
