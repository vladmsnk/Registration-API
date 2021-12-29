package com.selfio.selfio.controllers;

import com.selfio.selfio.exceptions.AlreadyExistsException;
import com.selfio.selfio.exceptions.EmailSendingException;
import com.selfio.selfio.exceptions.ExpiredTokenException;
import com.selfio.selfio.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The class provides catching exceptions from controllers and sending response statuses.
 */
@ControllerAdvice
public class ExceptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    /**
     * Method catches {@link AlreadyExistsException} and send http status 409 with message.
     * @param exception is the caught exception.
     * @return response with status code and message.
     */
    @ExceptionHandler(value = {AlreadyExistsException.class})
    public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException exception) {
        logger.error("Already Exists Exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Method catches {@link UserNotFoundException} and send http status 404 with message.
     * @param exception is the caught exception.
     * @return response with status code and message.
     */
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        logger.error("User not found Exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Method catches {@link ExpiredTokenException} and send http status 403 with message.
     * @param exception is the caught exception.
     * @return response with status code and message.
     */
    @ExceptionHandler(value = {ExpiredTokenException.class})
    public ResponseEntity<String> handleExpiredTokenException(ExpiredTokenException exception) {
        logger.error("Expired Token Exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Method catches {@link EmailSendingException} and send http status 500 with message.
     * @param exception is the caught exception.
     * @return response with status code and message.
     */
    @ExceptionHandler(value = {EmailSendingException.class})
    public  ResponseEntity<String> handleEmailSendingException(EmailSendingException exception) {
        logger.error("Email Sending Exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method catches {@link BadCredentialsException} and send http status 401 with message.
     * @param exception is the caught exception.
     * @return response with status code and message.
     */
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
