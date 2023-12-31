package com.lvsr.organizer.app.interfaces;

import com.lvsr.organizer.app.exceptions.NegocialException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IController<DTO> {

    ResponseEntity<List<DTO>> todos();
    ResponseEntity<?> recupera(@PathVariable Long id) throws NegocialException;
    ResponseEntity<?> salvar(@Validated @RequestBody DTO dto) throws NegocialException;
    ResponseEntity<?> excluir(@PathVariable Long id) throws NegocialException;

}
