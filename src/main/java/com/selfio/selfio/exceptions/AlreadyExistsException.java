package com.selfio.selfio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class of custom exception that thrown when user tries to register existing account.
 * @see RuntimeException
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
