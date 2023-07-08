package com.lvsr.organizer.app.exceptions;

public class LancamentoNaoTipadoException extends NegocialException {

    public LancamentoNaoTipadoException() {

        super("O lançamento não possui tipo. (ENTRADA ou SAIDA)");

    }
}
