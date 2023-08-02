package com.lvsr.organizer.app.api.controllers;

import com.lvsr.organizer.app.dtos.EfetivaLancamentoDTO;
import com.lvsr.organizer.app.dtos.LancamentoDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.services.LancamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("lancamentos")
@Tag(name = "lancamentos")
public class LancamentoRestController implements IController<LancamentoDTO> {

    private final LancamentoService service;

    public LancamentoRestController(LancamentoService service) {
        this.service = service;
    }

    @Override
    @Operation(summary = "Retorna todas os lançamentos cadastradas na base", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<LancamentoDTO>> todos() {

        return ResponseEntity.ok(service.getRepository().findAll().stream().map(lancamento -> service.getMapper().toDto(lancamento)).toList());

    }

    @Override
    @Operation(summary = "Retorna o lançamento informado", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> recupera(Long id) throws NegocialException {

        return ResponseEntity.ok(service.recuperar(id));

    }

    @Override
    @Operation(summary = "Salva o lançamento informado", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formada")
    })
    @PostMapping
    public ResponseEntity<?> salvar(LancamentoDTO dto) throws NegocialException {

        return ResponseEntity.status(Objects.nonNull(dto.getId()) ? HttpStatus.OK : HttpStatus.CREATED).body(service.salvar(dto));

    }

    @Override
    @Operation(summary = "Exclui o lançamento informado", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(Long id) throws NegocialException {

        return ResponseEntity.ok(service.excluir(id));

    }

    @Operation(summary = "Retorna os lançamentos do usuário informado", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> recuperar(@PathVariable("userId") Long userId) throws NegocialException {

        return ResponseEntity.ok(service.recuperarLancamentosUsuario(userId));

    }

    @Operation(summary = "Efetiva o lançamento informado", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formada")
    })
    @PutMapping("/efetivar")
    public ResponseEntity<?> efetivar(@Validated @RequestBody EfetivaLancamentoDTO dto) throws NegocialException {

        return ResponseEntity.ok(service.efetivar(dto));

    }

    @Operation(summary = "Retorna os lançamentos da conta informada", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada")
    })
    @GetMapping("/conta/{id}")
    public ResponseEntity<?> recuperaLancamentosConta(@PathVariable("id") Long conta) throws NegocialException {

        return ResponseEntity.ok(service.recuperarLancamentosConta(conta));

    }

}
