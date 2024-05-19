package br.com.fiap.pagamento.service.core.usecases.pagamento;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.usecases.ports.repositories.PagamentoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetPagamentoByIdUsecase {

	private final PagamentoRepositoryPort pagamentoPort;

	public Pagamento get(Long id) {
		return pagamentoPort.get(id);
	}

}
