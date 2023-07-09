package com.lvsr.organizer.app.api.controllers;

import com.lvsr.organizer.app.dtos.ContaDTO;
import com.lvsr.organizer.app.dtos.LancamentoDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.mappers.ContaMapper;
import com.lvsr.organizer.app.mappers.LancamentoMapper;
import com.lvsr.organizer.app.repositories.ContaRepository;
import com.lvsr.organizer.app.repositories.LancamentoRepository;
import com.lvsr.organizer.app.services.ContaService;
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

        List<LancamentoDTO> lista = repository.findAll().stream().map(user -> mapper.toDto(user)).toList();

        return lista.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(lista);

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> recupera(Long id) throws NegocialException {

        return ResponseEntity.ok(service.recuperar(id));

    }

    @Override
    @PostMapping
    public ResponseEntity<?> salvar(@Valid LancamentoDTO dto) throws NegocialException {

        HttpStatus status = Objects.nonNull(dto.getId()) ? HttpStatus.OK : HttpStatus.CREATED;

        return ResponseEntity.status(status).body(service.salvar(dto));

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
