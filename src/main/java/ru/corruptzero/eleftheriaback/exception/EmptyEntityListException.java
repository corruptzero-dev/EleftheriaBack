package ru.corruptzero.eleftheriaback.exception;

/**
 * An exception that is thrown when a list of entities is empty.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public class EmptyEntityListException extends RuntimeException {

    /**
     * Constructs a new {@link EmptyEntityListException} with the given message.
     *
     * @param message the exception message
     */
    public EmptyEntityListException(String message) {
        super(message);
    }
}
