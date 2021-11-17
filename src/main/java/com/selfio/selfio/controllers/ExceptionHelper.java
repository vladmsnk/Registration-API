package com.selfio.selfio.controllers;

import com.selfio.selfio.exceptions.AlreadyExistsException;
import com.selfio.selfio.exceptions.EmailSendingException;
import com.selfio.selfio.exceptions.ExpiredTokenException;
import com.selfio.selfio.exceptions.UserNotFoundException;
import com.selfio.selfio.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    @ExceptionHandler(value = {AlreadyExistsException.class})
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException exception) {
        logger.error("Already Exists Exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        logger.error("User not found Exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ExpiredTokenException.class})
    public ResponseEntity<Object> handleExpiredTokenException(ExpiredTokenException exception) {
        logger.error("Expired Token Exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = {EmailSendingException.class})
    public  ResponseEntity<Object> handleEmailSendingException(EmailSendingException exception) {
        logger.error("Email Sending Exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}