package com.lvsr.organizer.app.api.controllers;

import com.lvsr.organizer.app.dtos.InstituicaoDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.mappers.InstituicaoMapper;
import com.lvsr.organizer.app.repositories.InstituicaoRepository;
import com.lvsr.organizer.app.services.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("instituicoes")
public class InstituicaoRestController implements IController<InstituicaoDTO> {

    @Autowired
    InstituicaoRepository repository;

    @Autowired
    InstituicaoService service;

    @Autowired
    InstituicaoMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<List<InstituicaoDTO>> todos() {

        List<InstituicaoDTO> lista = repository.findAll().stream().map(user -> mapper.toDto(user)).toList();

        return lista.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(lista);

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> recupera(Long id) throws NegocialException {

        return ResponseEntity.ok(service.recuperar(id));

    }

    @Override
    @PostMapping
    public ResponseEntity<?> salvar(@Valid InstituicaoDTO dto) throws NegocialException {

        HttpStatus status = Objects.nonNull(dto.getId()) ? HttpStatus.OK : HttpStatus.CREATED;

        return ResponseEntity.status(status).body(service.salvar(dto));

    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(Long id) throws NegocialException {

        return ResponseEntity.ok(service.excluir(id));

    }

}