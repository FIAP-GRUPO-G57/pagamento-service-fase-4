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

    public Pagamento confirm(Long paymentId, String type){
        if(type.equals("sandbox")){
            return confirmSandbox(paymentId);
        }
        PagamentoGateway pagamentoGateway = paymentGatewayPort.getPayment(paymentId);
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
        return null;
    }

    private Pagamento confirmSandbox(Long paymentId) {
        Pagamento pagamento = pagamentoRepositoryPort.get(paymentId);
        pagamento.setGatewayId(1111L);
        pagamento.setStatus(StatusEnum.PAGO);
        pagamentoRepositoryPort.save(pagamento);
        pagamentoConfirmedEventPort.notify(pagamento);
        return null;
    }
}
