package com.caching.exception;

import com.caching.exception.custom.GeoCodingException;
import com.caching.exception.custom.GeoCodingResultNotFoundException;
import com.caching.exception.custom.InvalidGeoCodingRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler is responsible for handling exceptions thrown by the application.
 * It provides centralized exception handling and returns appropriate HTTP responses with error messages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InvalidGeoCodingRequestException by returning a BAD_REQUEST (400) response with the exception message.
     *
     * @param ex The InvalidGeoCodingRequestException thrown.
     * @return A ResponseEntity containing the exception message with a BAD_REQUEST status.
     */
    @ExceptionHandler(InvalidGeoCodingRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidGeoCodingRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles GeoCodingResultNotFoundException by returning a NOT_FOUND (404) response with the exception message.
     *
     * @param ex The GeoCodingResultNotFoundException thrown.
     * @return A ResponseEntity containing the exception message with a NOT_FOUND status.
     */
    @ExceptionHandler(GeoCodingResultNotFoundException.class)
    public ResponseEntity<String> handleResultNotFoundException(GeoCodingResultNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles GeoCodingException by returning an INTERNAL_SERVER_ERROR (500) response with the exception message.
     *
     * @param ex The GeoCodingException thrown.
     * @return A ResponseEntity containing the exception message with an INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(GeoCodingException.class)
    public ResponseEntity<String> handleGenericGeoCodingException(GeoCodingException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    /**
     * Handles any other generic exceptions by returning an INTERNAL_SERVER_ERROR (500) response
     * with a generic error message indicating an unexpected error occurred.
     *
     * @param ex The generic Exception thrown.
     * @return A ResponseEntity containing a generic error message with an INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}
