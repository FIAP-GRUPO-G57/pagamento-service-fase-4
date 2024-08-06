package br.com.fiap.pagamento.service.infra.db.postgresql;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.usecases.ports.repositories.PagamentoRepositoryPort;
import br.com.fiap.pagamento.service.infra.db.entities.PagamentoEntity;
import br.com.fiap.pagamento.service.infra.db.repositories.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PagamentoPostgresqlRepository implements PagamentoRepositoryPort {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	public ModelMapper modelMapper;

	@Override
	public Pagamento get(Long id) {
		return pagamentoRepository.findById(id).map(pagamentoData -> modelMapper.map(pagamentoData, Pagamento.class)).orElse(null);
	}

	@Override
	public Pagamento save(Pagamento pagamento){
		return modelMapper.map(pagamentoRepository.save(modelMapper.map(pagamento, PagamentoEntity.class)), Pagamento.class);
	}

	@Override
	public Pagamento findTopByReference(Long reference) {
		return pagamentoRepository.findTopByReference(reference).map(pagamentoData -> modelMapper.map(pagamentoData, Pagamento.class)).orElse(null);
	}
}
