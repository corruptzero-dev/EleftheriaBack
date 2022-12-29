package ru.corruptzero.eleftheriaback.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.corruptzero.eleftheriaback.exception.*;

import java.util.HashSet;

/**
 * A controller advice to handle exceptions thrown by controllers.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@ControllerAdvice
@Slf4j
public class EntityExceptionHandler {

    /**
     * Exception handler for {@link EntityNotFoundException}.
     *
     * @param ex the exception to be handled
     * @return a {@link ResponseEntity} with a status code of {@link HttpStatus#NOT_FOUND}
     * and an {@link ApiError} object with the exception message
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundEntity(EntityNotFoundException ex) {
        log.error("EntityNotFoundException occurred: ", ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for {@link InvalidEntityException}.
     *
     * @param ex the exception to be handled
     * @return a {@link ResponseEntity} with a status code of {@link HttpStatus#BAD_REQUEST}
     * and an {@link ApiError} object with the exception message
     */
    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ApiError> handleInvalidEntity(InvalidEntityException ex, BindingResult bindingResult) {
        log.error("InvalidEntityException occurred: ", ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        apiError.setValidationErrors(new HashSet<>(bindingResult.getAllErrors()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link EmptyEntityListException} by returning a response with an HTTP status of
     * {@link HttpStatus#NO_CONTENT}.
     *
     * @param ex the exception to be handled
     * @return a {@link ResponseEntity} with a status code of {@link HttpStatus#NO_CONTENT}
     * and an {@link ApiError} object with the exception message
     */
    @ExceptionHandler(EmptyEntityListException.class)
    public ResponseEntity<ApiError> handleEmptyEntityList(EmptyEntityListException ex) {
        log.error("EmptyEntityListException occurred: ", ex);
        ApiError apiError = new ApiError(HttpStatus.NO_CONTENT, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NO_CONTENT);
    }

    /**
     * Handles {@link EntityDeleteException} by returning a response with an HTTP status of
     * {@link HttpStatus#INTERNAL_SERVER_ERROR}.
     *
     * @return a {@link ResponseEntity} with a status code of {@link HttpStatus#INTERNAL_SERVER_ERROR}
     * and an {@link ApiError} object with the exception message
     */
    @ExceptionHandler(EntityDeleteException.class)
    public ResponseEntity<ApiError> handleEntityDelete(EntityDeleteException ex) {
        log.error("EntityDeleteException occurred: ", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler for all other exceptions.
     *
     * @param ex the exception to be handled
     * @return a {@link ResponseEntity} with a status code of {@link HttpStatus#INTERNAL_SERVER_ERROR}
     * and an {@link ApiError} object with the exception message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
        log.error("An unhandled exception occurred: ", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

