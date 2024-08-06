package br.com.fiap.pagamento.service.core.usecases.ports.event;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;

public interface PagamentoConfirmedEventPort {
	void notify(Pagamento pagamento) throws Exception;
}
