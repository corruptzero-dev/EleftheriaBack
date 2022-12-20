package ru.corruptzero.eleftheriaback.service;

import ru.corruptzero.eleftheriaback.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order save(Order order);
    List<Order> getAllOrders();
    Optional<Order> findById(Long id);
    void deleteById(Long id);
    void deleteAll();
}
