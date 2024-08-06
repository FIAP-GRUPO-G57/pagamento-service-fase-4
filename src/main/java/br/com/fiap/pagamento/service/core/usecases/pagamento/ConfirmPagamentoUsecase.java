package br.com.fiap.pagamento.service.core.usecases.pagamento;

import br.com.fiap.pagamento.service.core.domain.entities.PagamentoGateway;
import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;
import br.com.fiap.pagamento.service.core.usecases.ports.event.PagamentoConfirmedEventPort;
import br.com.fiap.pagamento.service.core.usecases.ports.gateways.PaymentGatewayPort;
import br.com.fiap.pagamento.service.core.usecases.ports.repositories.PagamentoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfirmPagamentoUsecase {

    private final PaymentGatewayPort paymentGatewayPort;

    private final PagamentoRepositoryPort pagamentoRepositoryPort;

    private final PagamentoConfirmedEventPort pagamentoConfirmedEventPort;

    public Pagamento confirm(Long paymentId, String type) {
        try{
            PagamentoGateway pagamentoGateway = getPaymentGateway(paymentId, type);
            if (Objects.nonNull(pagamentoGateway)) {
                Pagamento pagamento = pagamentoRepositoryPort.get(Long.valueOf(pagamentoGateway.getIdExterno()));
                pagamento.setGatewayId(pagamentoGateway.getPaymentId());
                if ("approved".equals(pagamentoGateway.getStatus())) {
                    pagamento.setStatus(StatusEnum.PAGO);
                } else {
                    pagamento.setStatus(StatusEnum.REJEITADO);
                }
                pagamentoRepositoryPort.save(pagamento);
                pagamentoConfirmedEventPort.notify(pagamento);
                return pagamento;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private PagamentoGateway getPaymentGateway(Long paymentId, String type) {
        PagamentoGateway pagamentoGateway = PagamentoGateway.builder()
                .idExterno(paymentId.toString())
                .status(paymentId % 2 == 0 ? "approved" : "rejected")
                .paymentId(Long.sum(paymentId, 1000))
                .build();
        if (type==null || !type.equals("sandbox")) {
            pagamentoGateway = paymentGatewayPort.getPayment(paymentId);
        }
        return pagamentoGateway;
    }
}
