package com.lvsr.organizer.app.exceptions;

public class LancamentoNaoDatadoException extends NegocialException {

    public LancamentoNaoDatadoException() {
        super("O lançamento precisa de uma data");
    }
}
