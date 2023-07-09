package com.lvsr.organizer.app.api.controllers;

import com.lvsr.organizer.app.dtos.LancamentoDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.mappers.LancamentoMapper;
import com.lvsr.organizer.app.repositories.LancamentoRepository;
import com.lvsr.organizer.app.services.LancamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("lancamentos")
public class LancamentoRestController implements IController<LancamentoDTO> {

    @Autowired
    LancamentoRepository repository;

    @Autowired
    LancamentoService service;

    @Autowired
    LancamentoMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<List<LancamentoDTO>> todos() {

        return ResponseEntity.ok(repository.findAll().stream().map(entidade -> mapper.toDto(entidade)).toList());

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> recupera(Long id) throws NegocialException {

        return ResponseEntity.ok(service.recuperar(id));

    }

    @Override
    @PostMapping
    public ResponseEntity<?> salvar(LancamentoDTO dto) throws NegocialException {

        return ResponseEntity.status(Objects.nonNull(dto.getId()) ? HttpStatus.OK : HttpStatus.CREATED).body(service.salvar(dto));

    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(Long id) throws NegocialException {

        return ResponseEntity.ok(service.excluir(id));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> recuperar(@PathVariable("userId") Long userId) throws NegocialException {

        return ResponseEntity.ok(service.recuperarLancamentosUsuario(userId));

    }

}
