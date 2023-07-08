package com.lvsr.organizer.app.exceptions;

public class InstituicaoJaCadastradaException extends NegocialException {

    public InstituicaoJaCadastradaException() {
        super("Instituição já cadastrada.");
    }
}
