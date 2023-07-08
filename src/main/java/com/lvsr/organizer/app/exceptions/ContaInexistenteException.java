package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class ContaInexistenteException extends NegocialException {

    public ContaInexistenteException() {

        super("A conta informada não existe.", HttpStatus.NOT_FOUND);

    }
}
