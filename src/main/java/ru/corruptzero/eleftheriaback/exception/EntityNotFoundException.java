package ru.corruptzero.eleftheriaback.exception;

/**
 * An exception that is thrown when an entity is not found.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@link EntityNotFoundException} with the given message.
     *
     * @param message the exception message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
