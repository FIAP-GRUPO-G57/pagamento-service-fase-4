package br.com.fiap.pagamento.service.infra.controllers;

import br.com.fiap.pagamento.service.core.domain.dto.PagamentoDto;
import br.com.fiap.pagamento.service.core.domain.entities.Pagamento;
import br.com.fiap.pagamento.service.core.usecases.pagamento.ConfirmPagamentoUsecase;
import br.com.fiap.pagamento.service.core.usecases.pagamento.CreatePagamentoUsecase;
import br.com.fiap.pagamento.service.core.usecases.pagamento.GetPagamentoByIdUsecase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final GetPagamentoByIdUsecase getPagamentoByIdUsecase;

    private final CreatePagamentoUsecase createPagamentoUsecase;

    private final ConfirmPagamentoUsecase confirmPagamentoUsecase;

    @Autowired
    private final ModelMapper modelMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PagamentoDto> get(@PathVariable(value = "id") Long id) {
        Pagamento pagamento = Optional.ofNullable(getPagamentoByIdUsecase.get(id))
                .orElseThrow(() -> new EntityNotFoundException("Pagamento nao encontrado para o id :: " + id));
        return ResponseEntity.ok().body(modelMapper.map(pagamento, PagamentoDto.class));
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> post(@Validated @RequestBody PagamentoDto PagamentoDto, @RequestParam(name = "type", required = false) String type) {
        Pagamento pagamento = createPagamentoUsecase.create(modelMapper.map(PagamentoDto, Pagamento.class), type);
        return new ResponseEntity<PagamentoDto>(modelMapper.map(pagamento, PagamentoDto.class), HttpStatus.CREATED);
    }

    @PostMapping(value = "/notifications")
    public ResponseEntity<Pagamento> notification(@RequestParam(name = "id") Long id, @RequestParam(name = "topic") String topic, @RequestParam(name = "type", required = false) String type) {
        Pagamento pagamento = null;
        if (("payment").equals(topic) && Objects.nonNull(id)) {
            pagamento = confirmPagamentoUsecase.confirm(id, type);
        }
        return ResponseEntity.ok(pagamento);
    }
}
