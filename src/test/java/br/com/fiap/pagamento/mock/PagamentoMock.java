package br.com.fiap.pagamento.mock;

import br.com.fiap.pagamento.service.core.domain.dto.PagamentoDto;
import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;

import java.math.BigDecimal;

public class PagamentoMock {

    public static final Long ID = 1L;
    public static final Long REFERENCE = 11L;
    public static final BigDecimal VALOR = new BigDecimal(10);
    public static final Long GATEWAY_ID = 100L;
    public static final String QRCODE = "qrcode";
    public static final StatusEnum status = StatusEnum.PENDING;
    public static final StatusEnum statusPago = StatusEnum.PAGO;

    public static Pagamento createPagamento() {
        return Pagamento.builder()
                .id(ID)
                .reference(REFERENCE)
                .valor(VALOR)
                .gatewayId(GATEWAY_ID)
                .qrcode(QRCODE)
                .status(status)
                .build();
    }

    public static PagamentoDto createPagamentoDto() {
        return PagamentoDto.builder()
                .id(ID)
                .reference(REFERENCE)
                .valor(VALOR)
                .gatewayId(GATEWAY_ID)
                .qrcode(QRCODE)
                .status(status)
                .build();
    }

    public static Pagamento toPagamento(PagamentoDto pagamentoDto) {
        return Pagamento.builder()
                .id(pagamentoDto.getId())
                .reference(pagamentoDto.getReference())
                .valor(pagamentoDto.getValor())
                .gatewayId(pagamentoDto.getGatewayId())
                .qrcode(pagamentoDto.getQrcode())
                .status(pagamentoDto.getStatus())
                .build();
    }

    public static PagamentoDto toDto(Pagamento pagamento) {
        return PagamentoDto.builder()
                .id(pagamento.getId())
                .reference(pagamento.getReference())
                .valor(pagamento.getValor())
                .gatewayId(pagamento.getGatewayId())
                .qrcode(pagamento.getQrcode())
                .status(pagamento.getStatus())
                .build();
    }

}
