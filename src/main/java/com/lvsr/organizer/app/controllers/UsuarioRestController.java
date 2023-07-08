package com.lvsr.organizer.app.controllers;

import com.lvsr.organizer.app.dtos.UsuarioDTO;
import com.lvsr.organizer.app.exceptions.NegocialException;
import com.lvsr.organizer.app.interfaces.IController;
import com.lvsr.organizer.app.mappers.UsuarioMapper;
import com.lvsr.organizer.app.repositories.UsuarioRepository;
import com.lvsr.organizer.app.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("usuarios")
public class UsuarioRestController implements IController<UsuarioDTO> {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> todos() {

        List<UsuarioDTO> lista = repository.findAll().stream().map(user -> mapper.toDto(user)).toList();

        return lista.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(lista);

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> recupera(Long id) {

        try {

            return ResponseEntity.ok(service.recuperar(id));

        } catch (NegocialException e) {

            return ResponseEntity.status(e.getStatus()).body(e.getMessage());

        } catch (Exception e) {

            e.getStackTrace();
            return ResponseEntity.internalServerError().build();

        }

    }

    @Override
    @PostMapping
    public ResponseEntity<?> salvar(UsuarioDTO dto) {

        HttpStatus status = Objects.nonNull(dto.getId()) ? HttpStatus.OK : HttpStatus.CREATED;

        try {

            return ResponseEntity.status(status).body(service.salvar(dto));

        } catch (NegocialException e) {

            return ResponseEntity.status(e.getStatus()).body(e.getMessage());

        } catch (Exception e) {

            e.getStackTrace();
            return ResponseEntity.internalServerError().build();

        }

    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(Long id) {

        try {

            return ResponseEntity.ok(service.excluir(id));

        } catch (NegocialException e) {

            return ResponseEntity.status(e.getStatus()).body(e.getMessage());

        } catch (Exception e) {

            e.getStackTrace();
            return ResponseEntity.internalServerError().build();

        }

    }

}
