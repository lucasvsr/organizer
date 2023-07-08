package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class ContaComDonoErradoException extends NegocialException {

    public ContaComDonoErradoException() {
        super("O usuário vinculado a conta nesta requisição não é o dono dela.");
    }
}
