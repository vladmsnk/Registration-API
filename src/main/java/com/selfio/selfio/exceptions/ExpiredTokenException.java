package com.selfio.selfio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class of custom exception that thrown when token is expired.
 * @see RuntimeException
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(String message) {
        super(message);
    }
}
