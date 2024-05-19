package br.com.fiap.pagamento.service.core.usecases.ports.repositories;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;

import java.util.List;

public interface PagamentoRepositoryPort {
	Pagamento get(Long id);
	Pagamento save(Pagamento pagamento);
	Pagamento findTopByReference(Long reference);
}
