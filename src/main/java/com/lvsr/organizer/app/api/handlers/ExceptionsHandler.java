package com.lvsr.organizer.app.api.handlers;

import com.lvsr.organizer.app.exceptions.NegocialException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiErrorMessage error = new ApiErrorMessage(HttpStatus.valueOf(status.value()), ex.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());

        return new ResponseEntity<>(error, error.getStatus());

    }

    @ExceptionHandler(NegocialException.class)
    public ResponseEntity<Object> handleNegocialException(NegocialException exception, WebRequest request) {

        ApiErrorMessage error = new ApiErrorMessage(exception.getStatus(), exception.getMessage());

        return new ResponseEntity<>(error, error.getStatus());

    }

}
