package com.selfio.selfio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The class of custom exception that thrown when email confirmation cannot be sent.
 * @see RuntimeException
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSendingException extends RuntimeException{
    public EmailSendingException(String message) {
        super(message);
    }
}
