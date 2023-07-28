package com.lvsr.organizer.app.api.controllers;

import com.lvsr.organizer.app.dtos.InstituicaoDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.services.InstituicaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("instituicoes")
@Tag(name = "instituicoes")
public class InstituicaoRestController implements IController<InstituicaoDTO> {

    private final InstituicaoService service;

    public InstituicaoRestController(InstituicaoService service) {
        this.service = service;
    }

    @Override
    @Operation(summary = "Retorna todas as instituições cadastradas na base", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<InstituicaoDTO>> todos() {

        return ResponseEntity.ok(service.getRepository().findAll().stream().map(instituicao -> service.getMapper().toDto(instituicao)).toList());

    }

    @Override
    @Operation(summary = "Retorna a instituição informada", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> recupera(Long id) throws NegocialException {

        return ResponseEntity.ok(service.recuperar(id));

    }

    @Override
    @Operation(summary = "Salva a instituição informada", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formada")
    })
    @PostMapping
    public ResponseEntity<?> salvar(InstituicaoDTO dto) throws NegocialException {

        return ResponseEntity.status(Objects.nonNull(dto.getId()) ? HttpStatus.OK : HttpStatus.CREATED).body(service.salvar(dto));

    }

    @Override
    @Operation(summary = "Exclui a instituição informada", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(Long id) throws NegocialException {

        return ResponseEntity.ok(service.excluir(id));

    }

}
