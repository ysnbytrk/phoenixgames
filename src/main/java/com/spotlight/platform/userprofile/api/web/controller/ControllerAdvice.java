package com.spotlight.platform.userprofile.api.web.controller;

import com.spotlight.platform.userprofile.api.core.exceptions.EntityNotFoundException;
import com.spotlight.platform.userprofile.api.core.exceptions.InvalidCommandException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global controller advice class for handling exceptions thrown by controllers.
 */
@RestControllerAdvice
@Api(tags = "Exception Handling")
public class ControllerAdvice {

    /**
     * Handles the EntityNotFoundException by returning a ResponseEntity with a bad request status and the exception message.
     *
     * @param ex The EntityNotFoundException to handle.
     * @return A ResponseEntity with a bad request status and the exception message.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ApiOperation(value = "Handle Entity Not Found Exception", notes = "Handles the EntityNotFoundException and returns a bad request response.")
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles the InvalidCommandException by returning a ResponseEntity with a bad request status and the exception message.
     *
     * @param ex The InvalidCommandException to handle.
     * @return A ResponseEntity with a bad request status and the exception message.
     */
    @ExceptionHandler(InvalidCommandException.class)
    @ApiOperation(value = "Handle Invalid Command Exception", notes = "Handles the InvalidCommandException and returns a bad request response.")
    public ResponseEntity<String> handleInvalidCommandException(InvalidCommandException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles all other exceptions by returning a ResponseEntity with a bad request status and a generic error message.
     *
     * @param ex The Exception to handle.
     * @return A ResponseEntity with a bad request status and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    @ApiOperation(value = "Handle Exception", notes = "Handles general exceptions and returns a bad request response.")
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.badRequest().body("Invalid request");
    }

}
