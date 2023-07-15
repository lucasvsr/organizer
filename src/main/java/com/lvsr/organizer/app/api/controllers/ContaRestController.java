package com.lvsr.organizer.app.api.controllers;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.mappers.ContaMapper;
import com.lvsr.organizer.app.repositories.ContaRepository;
import com.lvsr.organizer.app.services.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("contas")
@Tag(name = "contas")
public class ContaRestController implements IController<ContaDTO> {

    private final ContaRepository repository;

    private final ContaService service;

    private final ContaMapper mapper;

    public ContaRestController(ContaRepository repository, ContaService service, ContaMapper mapper) {
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @GetMapping
    @Operation(summary = "Retorna todas as contas cadastradas na base", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso")
    })
    public ResponseEntity<List<ContaDTO>> todos() {

        return ResponseEntity.ok(repository.findAll().stream().map(mapper::toDto).toList());

    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Retorna a conta informada se existir na base", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada")
    })
    public ResponseEntity<?> recupera(Long id) throws NegocialException {

        return ResponseEntity.ok(service.recuperar(id));

    }

    @Override
    @PostMapping
    @Operation(summary = "Salva uma conta", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição mal formatada")
    })
    public ResponseEntity<?> salvar(ContaDTO dto) throws NegocialException {

        return ResponseEntity.status(Objects.nonNull(dto.getId()) ? HttpStatus.OK : HttpStatus.CREATED).body(service.salvar(dto));

    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma conta", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição mal formatada")
    })
    public ResponseEntity<?> excluir(Long id) throws NegocialException {

        return ResponseEntity.ok(service.excluir(id));

    }

}
