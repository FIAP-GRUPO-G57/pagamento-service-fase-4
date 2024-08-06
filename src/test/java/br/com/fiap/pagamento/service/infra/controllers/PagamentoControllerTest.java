package br.com.fiap.pagamento.service.infra.controllers;

import br.com.fiap.pagamento.mock.PagamentoMock;
import br.com.fiap.pagamento.service.core.domain.dto.PagamentoDto;
import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.usecases.pagamento.ConfirmPagamentoUsecase;
import br.com.fiap.pagamento.service.core.usecases.pagamento.CreatePagamentoUsecase;
import br.com.fiap.pagamento.service.core.usecases.pagamento.GetPagamentoByIdUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PagamentoControllerTest {
    @Mock
    GetPagamentoByIdUsecase getPagamentoByIdUsecase;

    @Mock
    CreatePagamentoUsecase createPagamentoUsecase;

    @Mock
    ConfirmPagamentoUsecase confirmPagamentoUsecase;

    @InjectMocks
    PagamentoController pagamentoController;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPagamentoWhenGetById() {
        Pagamento pagamento = PagamentoMock.createPagamento();

        when(modelMapper.map(any(Pagamento.class), eq(PagamentoDto.class))).thenReturn(PagamentoMock.toDto(pagamento));
        when(getPagamentoByIdUsecase.get(anyLong())).thenReturn(pagamento);

        ResponseEntity<PagamentoDto> result = pagamentoController.get(1L);

        assertEquals(pagamento.getId(), result.getBody().getId());
    }

    @Test
    void shouldReturnCreatedPagamentoWhenPost() {
        PagamentoDto pagamentoDto = PagamentoMock.createPagamentoDto();

        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);

        when(createPagamentoUsecase.create(any(Pagamento.class), any(String.class))).thenReturn(pagamento);
        when(modelMapper.map(any(PagamentoDto.class), eq(Pagamento.class))).thenReturn(PagamentoMock.toPagamento(pagamentoDto));
        when(modelMapper.map(any(Pagamento.class), eq(PagamentoDto.class))).thenReturn(PagamentoMock.toDto(pagamento));

        ResponseEntity<PagamentoDto> result = pagamentoController.post(pagamentoDto, "sandbox");

        assertEquals(pagamento.getId(), result.getBody().getId());
    }

    @Test
    void shouldReturnConfirmedPagamentoWhenNotification() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);

        when(confirmPagamentoUsecase.confirm(anyLong(), anyString())).thenReturn(pagamento);

        ResponseEntity<Pagamento> result = pagamentoController.notification(1L, "payment", "other");

        assertEquals(pagamento.getId(), result.getBody().getId());
    }
}
