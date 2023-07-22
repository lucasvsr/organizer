package com.lvsr.organizer.app.api.controllers;

import com.lvsr.organizer.app.dtos.UsuarioDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.mappers.UsuarioMapper;
import com.lvsr.organizer.app.repositories.UsuarioRepository;
import com.lvsr.organizer.app.services.UsuarioService;
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
@RequestMapping("usuarios")
@Tag(name = "usuarios")
public class UsuarioRestController implements IController<UsuarioDTO> {

    private final UsuarioRepository repository;

    private final UsuarioService service;

    private final UsuarioMapper mapper;

    public UsuarioRestController(UsuarioRepository repository, UsuarioService service, UsuarioMapper mapper) {
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @Operation(summary = "Retorna todos os usuários cadastrados na base", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> todos() {

        return ResponseEntity.ok(repository.findAll().stream().map(mapper::toDto).toList());

    }

    @Override
    @Operation(summary = "Retorna o usuário informado", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> recupera(Long id) throws NegocialException {

        return ResponseEntity.ok(service.recuperar(id));

    }

    @Override
    @Operation(summary = "Salva o usuário informado", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição mal formada")
    })
    @PostMapping
    public ResponseEntity<?> salvar(UsuarioDTO dto) throws NegocialException {

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

}
