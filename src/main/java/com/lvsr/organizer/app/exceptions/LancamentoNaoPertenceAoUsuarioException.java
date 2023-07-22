package com.lvsr.organizer.app.exceptions;

public class LancamentoNaoPertenceAoUsuarioException extends NegocialException {

    public LancamentoNaoPertenceAoUsuarioException() {
        super("O lançamento informado não pertence ao usuário da requisição");
    }
}
