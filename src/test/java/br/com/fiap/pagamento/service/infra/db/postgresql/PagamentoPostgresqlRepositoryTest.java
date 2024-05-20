package br.com.fiap.pagamento.service.infra.db.postgresql;

import br.com.fiap.pagamento.mock.PagamentoMock;
import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.infra.db.entities.PagamentoEntity;
import br.com.fiap.pagamento.service.infra.db.repositories.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class PagamentoPostgresqlRepositoryTest {
    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PagamentoPostgresqlRepository pagamentoPostgresqlRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPagamentoWhenGetIsCalledWithExistingId() {
        Long id = 1L;
        PagamentoEntity pagamentoEntity = createPagamentoEntity();
        Pagamento expectedPagamento = new Pagamento();
        when(pagamentoRepository.findById(id)).thenReturn(Optional.of(pagamentoEntity));
        when(modelMapper.map(pagamentoEntity, Pagamento.class)).thenReturn(expectedPagamento);

        Pagamento result = pagamentoPostgresqlRepository.get(id);

        assertEquals(expectedPagamento, result);
    }

    @Test
    void shouldReturnNullWhenGetIsCalledWithNonExistingId() {
        Long id = 1L;
        when(pagamentoRepository.findById(id)).thenReturn(Optional.empty());

        Pagamento result = pagamentoPostgresqlRepository.get(id);

        assertNull(result);
    }

    @Test
    void shouldReturnSavedPagamentoWhenSaveIsCalled() {
        Pagamento pagamento = new Pagamento();
        PagamentoEntity pagamentoEntity = createPagamentoEntity();
        when(modelMapper.map(pagamento, PagamentoEntity.class)).thenReturn(pagamentoEntity);
        when(pagamentoRepository.save(pagamentoEntity)).thenReturn(pagamentoEntity);
        when(modelMapper.map(pagamentoEntity, Pagamento.class)).thenReturn(pagamento);

        Pagamento result = pagamentoPostgresqlRepository.save(pagamento);

        assertEquals(pagamento, result);
    }

    @Test
    void shouldReturnPagamentoWhenFindTopByReferenceIsCalledWithExistingReference() {
        Long reference = 1L;
        PagamentoEntity pagamentoEntity = createPagamentoEntity();
        Pagamento expectedPagamento = new Pagamento();
        when(pagamentoRepository.findTopByReference(reference)).thenReturn(Optional.of(pagamentoEntity));
        when(modelMapper.map(pagamentoEntity, Pagamento.class)).thenReturn(expectedPagamento);

        Pagamento result = pagamentoPostgresqlRepository.findTopByReference(reference);

        assertEquals(expectedPagamento, result);
    }

    @Test
    void shouldReturnNullWhenFindTopByReferenceIsCalledWithNonExistingReference() {
        Long reference = 1L;
        when(pagamentoRepository.findTopByReference(reference)).thenReturn(Optional.empty());

        Pagamento result = pagamentoPostgresqlRepository.findTopByReference(reference);

        assertNull(result);
    }

    private PagamentoEntity createPagamentoEntity() {
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(PagamentoMock.ID);
        pagamentoEntity.setValor(PagamentoMock.VALOR);
        pagamentoEntity.setReference(PagamentoMock.REFERENCE);
        pagamentoEntity.setQrcode("qrCode");
        pagamentoEntity.setStatus(PagamentoMock.status);
        pagamentoEntity.setCriacao(LocalDateTime.now());
        pagamentoEntity.setAlteracao(LocalDateTime.now());
        return pagamentoEntity;
    }
}
