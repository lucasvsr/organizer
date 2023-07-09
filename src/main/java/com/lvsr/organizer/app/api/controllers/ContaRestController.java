package com.lvsr.organizer.app.api.controllers;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.mappers.ContaMapper;
import com.lvsr.organizer.app.repositories.ContaRepository;
import com.lvsr.organizer.app.services.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("contas")
public class ContaRestController implements IController<ContaDTO> {

    @Autowired
    ContaRepository repository;

    @Autowired
    ContaService service;

    @Autowired
    ContaMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<List<ContaDTO>> todos() {

        return ResponseEntity.ok(repository.findAll().stream().map(entidade -> mapper.toDto(entidade)).toList());

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> recupera(Long id) throws NegocialException {

        return ResponseEntity.ok(service.recuperar(id));

    }

    @Override
    @PostMapping
    public ResponseEntity<?> salvar(ContaDTO dto) throws NegocialException {

        return ResponseEntity.status(Objects.nonNull(dto.getId()) ? HttpStatus.OK : HttpStatus.CREATED).body(service.salvar(dto));

    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(Long id) throws NegocialException {

        return ResponseEntity.ok(service.excluir(id));

    }

}
