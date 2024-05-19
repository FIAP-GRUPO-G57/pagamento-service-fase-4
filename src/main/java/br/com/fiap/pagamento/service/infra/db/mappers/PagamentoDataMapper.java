package br.com.fiap.pagamento.service.infra.db.mappers;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.infra.db.entities.PagamentoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PagamentoDataMapper {

	private final ModelMapper modelMapper;

	public PagamentoDataMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Pagamento toDomain(PagamentoEntity data) {
		return modelMapper.map(data, Pagamento.class);
	}

	public PagamentoEntity toData(Pagamento pagamento) {
		return modelMapper.map(pagamento, PagamentoEntity.class);
	}
	
}
