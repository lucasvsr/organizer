package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class ContaAbaixoDeZeroException extends NegocialException {

    public ContaAbaixoDeZeroException() {
        super("A conta não pode ter saldo abaixo de zero", null);
    }
}
