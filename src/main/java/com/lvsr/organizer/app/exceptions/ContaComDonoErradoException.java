package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class ContaComDonoErradoException extends NegocialException {

    public ContaComDonoErradoException() {
        super("O usuário informado não é o dono da conta.");
    }
}
