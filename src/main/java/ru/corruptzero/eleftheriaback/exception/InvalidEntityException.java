package ru.corruptzero.eleftheriaback.exception;

/**
 * An exception that is thrown when an entity is invalid.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 * 
 * @author corruptzero
 */
public class InvalidEntityException extends RuntimeException {
    /**
     * Constructs a new {@link InvalidEntityException} with the given message.
     *
     * @param message the exception message
     */
    public InvalidEntityException(String message) {
        super(message);
    }
}
