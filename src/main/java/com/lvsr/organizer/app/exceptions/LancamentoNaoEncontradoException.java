package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class LancamentoNaoEncontradoException extends NegocialException {

    public LancamentoNaoEncontradoException() {
        super("Lançamento não encontrado.", HttpStatus.NOT_FOUND);
    }
}
