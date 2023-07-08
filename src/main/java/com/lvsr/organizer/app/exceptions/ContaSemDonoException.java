package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class ContaSemDonoException extends NegocialException {

    public ContaSemDonoException() {
        super("A conta informada não tem dono");
    }

}
