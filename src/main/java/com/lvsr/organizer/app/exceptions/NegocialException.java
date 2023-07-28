package com.lvsr.organizer.app.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class NegocialException extends Exception {

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public NegocialException(String message, HttpStatus status) {

        super(message);

        if (status != null) {

            this.status = status;

        }

    }

    public NegocialException(String message) {

        this(message, null);

    }

}
