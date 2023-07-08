package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class InstituicaoNaoEncontradaException extends NegocialException {

    public InstituicaoNaoEncontradaException() {
        super("A instituição usada na requisição não existe", HttpStatus.NOT_FOUND);
    }
}
