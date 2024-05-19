package br.com.fiap.pagamento.service.infra.db.repositories;

import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.infra.db.entities.PagamentoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PagamentoRepository extends CrudRepository<PagamentoEntity, Long> {
    Optional<PagamentoEntity> findTopByReference(Long reference);
}
