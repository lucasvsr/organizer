package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class EmailJaExisteException extends NegocialException {

    public EmailJaExisteException() {

        super("Já existe um usuário com o e-mail informado.", HttpStatus.BAD_REQUEST);

    }
}
