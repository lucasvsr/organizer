package com.lvsr.organizer.app.exceptions;

import org.springframework.http.HttpStatus;

public class UsuarioNaoEncontradoException extends NegocialException {
    public UsuarioNaoEncontradoException() {
        super("Usuario não encontrado", HttpStatus.NOT_FOUND);
    }
}
