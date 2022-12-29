package ru.corruptzero.eleftheriaback.domain.entity.order;

/**
 * Enum representing the possible states of an order.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public enum EOrderStatus {
    /**
     * The order is active.
     */
    ACTIVE,

    /**
     * The order is under completion.
     */
    PENDING,

    /**
     * The order is inactive.
     */
    INACTIVE
}