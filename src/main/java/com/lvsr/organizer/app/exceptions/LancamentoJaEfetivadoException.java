package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class LancamentoJaEfetivadoException extends NegocialException {

    public LancamentoJaEfetivadoException() {
        super("Lançamento já efetivado.");
    }
}
