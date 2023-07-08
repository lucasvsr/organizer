package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class InstituicaoInexistenteException extends NegocialException {

    public InstituicaoInexistenteException() {
        super("A instituição informada não existe");
    }
}
