package ru.corruptzero.eleftheriaback.domain.entity.withdraw;

/**
 * Enum representing the possible states of a withdraw request.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public enum EWithdrawStatus {
    /**
     * The withdraw request is still being processed.
     */
    PENDING,

    /**
     * The withdraw request has been completed.
     */
    COMPLETED,

    /**
     * The withdraw request has been cancelled.
     */
    CANCELLED
}

