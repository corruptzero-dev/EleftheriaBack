package ru.corruptzero.eleftheriaback.service;

import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.exception.EmptyEntityListException;
import ru.corruptzero.eleftheriaback.exception.EntityDeleteException;
import ru.corruptzero.eleftheriaback.exception.EntityNotFoundException;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;

import java.util.List;
import java.util.Optional;

/**
 * Service that provides CRUD operations for {@link Order} objects.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public interface OrderService {

    /**
     * Saves a given order.
     *
     * @param order the order to save
     * @return the saved order
     * @throws InvalidEntityException if the order is invalid or has the same title as another order
     */
    Order save(Order order);

    /**
     * Returns a list of all orders.
     *
     * @return the list of all orders
     * @throws EmptyEntityListException if no orders are found
     */
    List<Order> getAllOrders();

    /**
     * Returns the order with the given ID, if it exists.
     *
     * @param id the ID of the order to retrieve
     * @return the order with the given ID, wrapped in an {@link Optional}
     * @throws EntityNotFoundException if the order is not found
     */
    Optional<Order> findById(Long id);

    /**
     * Deletes the order with the given ID.
     *
     * @param id the ID of the order to delete
     * @throws EntityDeleteException if the order cannot be deleted
     */
    void deleteById(Long id);

    /**
     * Deletes all orders.
     * <p>
     * <strong>Note:</strong> This method is only intended for testing purposes.
     *
     * @throws EntityDeleteException if the orders cannot be deleted
     */
    void deleteAll();
}

