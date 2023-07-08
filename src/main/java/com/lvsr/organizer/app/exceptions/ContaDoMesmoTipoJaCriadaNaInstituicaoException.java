package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class ContaDoMesmoTipoJaCriadaNaInstituicaoException extends NegocialException{

    public ContaDoMesmoTipoJaCriadaNaInstituicaoException() {
        super("O usuário já possui uma conta deste tipo nesta instituição.");
    }
}
