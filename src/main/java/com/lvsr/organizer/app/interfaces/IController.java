package com.lvsr.organizer.app.interfaces;

import com.lvsr.organizer.app.exceptions.NegocialException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IController<DTO> {

    ResponseEntity<List<DTO>> todos();

    ResponseEntity<?> recupera(@PathVariable Long id) throws NegocialException;

    ResponseEntity<?> salvar(@Valid @RequestBody DTO dto) throws NegocialException;

    ResponseEntity<?> excluir(@PathVariable Long id) throws NegocialException;

}
