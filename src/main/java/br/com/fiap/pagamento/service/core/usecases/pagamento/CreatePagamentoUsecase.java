package br.com.fiap.pagamento.service.core.usecases.pagamento;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;
import br.com.fiap.pagamento.service.core.usecases.ports.gateways.PaymentGatewayPort;
import br.com.fiap.pagamento.service.core.usecases.ports.repositories.PagamentoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class CreatePagamentoUsecase {

	private final PagamentoRepositoryPort pagamentoRepositoryPort;

	private final PaymentGatewayPort paymentGatewayPort;

	public Pagamento create(Pagamento pagamento) {
		Pagamento pag = pagamentoRepositoryPort.findTopByReference(pagamento.getReference());
		if(pag!=null)
			return pag;
		pagamento.setStatus(StatusEnum.CREATED);
		Pagamento  pagamentoCreated= pagamentoRepositoryPort.save(pagamento);
		String qrcode = paymentGatewayPort.create(pagamentoCreated.getId().toString(), pagamento.getValor());
		pagamentoCreated.setQrcode(qrcode);
		pagamentoCreated.setStatus(StatusEnum.PENDING);
		Pagamento pagamentoPending = pagamentoRepositoryPort.save(pagamentoCreated);
		return pagamentoRepositoryPort.save(pagamentoPending);
	}

}
