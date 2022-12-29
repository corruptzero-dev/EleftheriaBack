package ru.corruptzero.eleftheriaback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.domain.repository.OrderRepository;
import ru.corruptzero.eleftheriaback.exception.EmptyEntityListException;
import ru.corruptzero.eleftheriaback.exception.EntityDeleteException;
import ru.corruptzero.eleftheriaback.exception.EntityNotFoundException;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;
import ru.corruptzero.eleftheriaback.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>(orderRepository.findAll());
        if (orders.isEmpty()) {
            throw new EmptyEntityListException("No orders found.");
        }
        return orders;
    }

    public Optional<Order> findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new EntityNotFoundException("Order not found with ID: " + id);
        }
        return order;
    }

    public Order save(Order order) {
        if (orderRepository.existsByTitle(order.getTitle())) {
            throw new InvalidEntityException("An order with the same title already exists.");
        }
        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityDeleteException("Failed to delete order with ID: " + id + ". Entity not found.");
        } catch (DataAccessException e) {
            throw new EntityDeleteException("Failed to delete order with ID: " + id + ". Data access error occurred.");
        }
    }

    public void deleteAll() {
        try {
            orderRepository.deleteAll();
        } catch (DataAccessException e) {
            throw new EntityDeleteException("Failed to delete all orders. Data access error occurred.");
        }
    }
}



