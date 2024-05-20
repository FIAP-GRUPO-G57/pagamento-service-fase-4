package br.com.fiap.pagamento.service.infra.db.entities;

import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PagamentoEntityTest {
    private PagamentoEntity pagamentoEntity;

    @BeforeEach
    void setUp() {
        pagamentoEntity = new PagamentoEntity();
    }

    @Test
    void shouldSetAndGetId() {
        Long id = 1L;
        pagamentoEntity.setId(id);

        assertEquals(id, pagamentoEntity.getId());
    }

    @Test
    void shouldSetAndGetValor() {
        BigDecimal valor = BigDecimal.ONE;
        pagamentoEntity.setValor(valor);

        assertEquals(valor, pagamentoEntity.getValor());
    }

    @Test
    void shouldSetAndGetReference() {
        Long reference = 1L;
        pagamentoEntity.setReference(reference);

        assertEquals(reference, pagamentoEntity.getReference());
    }

    @Test
    void shouldSetAndGetQrcode() {
        String qrcode = "qrcode";
        pagamentoEntity.setQrcode(qrcode);

        assertEquals(qrcode, pagamentoEntity.getQrcode());
    }

    @Test
    void shouldSetAndGetStatus() {
        StatusEnum status = StatusEnum.PAGO;
        pagamentoEntity.setStatus(status);

        assertEquals(status, pagamentoEntity.getStatus());
    }

    @Test
    void shouldSetAndGetCriacao() {
        pagamentoEntity.setCriacao(LocalDateTime.now());

        assertNotNull(pagamentoEntity.getCriacao());
    }

    @Test
    void shouldSetAndGetAlteracao() {
        pagamentoEntity.setAlteracao(LocalDateTime.now());

        assertNotNull(pagamentoEntity.getAlteracao());
    }
}
