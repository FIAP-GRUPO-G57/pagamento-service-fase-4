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

	public Pagamento create(Pagamento pagamento, String type) {
		Pagamento pag = pagamentoRepositoryPort.findTopByReference(pagamento.getReference());
		if(pag!=null)
			return pag;
		pagamento.setStatus(StatusEnum.CREATED);
		Pagamento  pagamentoCreated= pagamentoRepositoryPort.save(pagamento);
		pagamentoCreated.setQrcode(getQrcode(pagamento, pagamentoCreated, type));
		pagamentoCreated.setStatus(StatusEnum.PENDING);
		Pagamento pagamentoPending = pagamentoRepositoryPort.save(pagamentoCreated);
		return pagamentoRepositoryPort.save(pagamentoPending);
	}

	private String getQrcode(Pagamento pagamento, Pagamento pagamentoCreated, String type) {
		if (type==null || !type.equals("sandbox")) {
			return paymentGatewayPort.create(pagamentoCreated.getId().toString(), pagamento.getValor());
		}
		return "00020101021226940014BR.GOV.BCB.PIX2572pix-qr.mercadopago.com/instore/o/v2/c6482d96-e063-4f9d-b66e-9331347a468b5204000053039865802BR5915linkpagoproduct6009SAO PAULO62070503***630445F5";
	}

}
