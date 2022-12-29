package ru.corruptzero.eleftheriaback.exception;

/**
 * An exception that is thrown when an entity cannot be deleted.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public class EntityDeleteException extends RuntimeException {

    /**
     * Constructs a new {@link EntityDeleteException} with the given message.
     *
     * @param message the exception message
     */
    public EntityDeleteException(String message) {
        super(message);
    }
}
