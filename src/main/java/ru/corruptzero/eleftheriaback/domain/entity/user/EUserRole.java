package ru.corruptzero.eleftheriaback.domain.entity.user;

/**
 * Enum representing the possible roles of a user.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public enum EUserRole {
    /**
     * A regular user with no special privileges.
     */
    USER,

    /**
     * An administrator with full privileges.
     */
    ADMIN,

    /**
     * A moderator with limited privileges.
     */
    MODERATOR,

    /**
     * A client with specific privileges.
     */
    CLIENT
}

