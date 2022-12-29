package ru.corruptzero.eleftheriaback.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.util.Set;

/**
 * A POJO to hold error information for an API response.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Getter
@Setter
public class ApiError {

    /**
     * The HTTP status code for the error.
     */
    private HttpStatus status;

    /**
     * The error message.
     */
    private String message;

    /**
     * The set of validation errors.
     */
    private Set<ObjectError> validationErrors;

    /**
     * Constructs a new {@link ApiError} with the given status and message.
     *
     * @param status  the HTTP status code for the error
     * @param message the error message
     */
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
