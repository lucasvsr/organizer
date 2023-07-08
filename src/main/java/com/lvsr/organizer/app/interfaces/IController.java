package com.lvsr.organizer.app.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IController<DTO> {

    ResponseEntity<List<DTO>> todos();

    ResponseEntity<?> recupera(@PathVariable Long id);

    ResponseEntity<?> salvar(@RequestBody DTO dto);

    ResponseEntity<?> excluir(@PathVariable Long id);

}
