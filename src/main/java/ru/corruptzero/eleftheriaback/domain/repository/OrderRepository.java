package ru.corruptzero.eleftheriaback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;

/**
 * Repository for {@link Order} entities.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Returns whether an order with the given title exists.
     *
     * @param title the title to search for
     * @return {@code true} if an order with the given title exists, {@code false} otherwise
     */
    boolean existsByTitle(String title);
}
