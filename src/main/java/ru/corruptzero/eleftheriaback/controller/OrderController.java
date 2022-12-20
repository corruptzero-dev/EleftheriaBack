package ru.corruptzero.eleftheriaback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.corruptzero.eleftheriaback.domain.entity.Order;
import ru.corruptzero.eleftheriaback.service.impl.OrderServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/orders")
@Slf4j
public class OrderController {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderServiceImpl.getAllOrders();
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Optional<Order> orderData = orderServiceImpl.findById(id);
        return orderData.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam String title,
                                             @RequestParam String description,
                                             @RequestParam String skills,
                                             @RequestParam Integer value,
                                             @RequestParam Long admin_id,
                                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                     String due_to) {
        try {
            Order order = new Order();
            order.setTitle(title);
            order.setDescription(description);
            order.setSkills(skills);
            order.setValue(value);
            order.setAdmin_id(admin_id);
            order.setDue_to(LocalDateTime.parse(due_to, formatter));
            Order _order = orderServiceImpl.save(order);
            return new ResponseEntity<>(_order, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id,
                                             @RequestParam(required = false) String title,
                                             @RequestParam(required = false) String description,
                                             @RequestParam(required = false) String skills,
                                             @RequestParam(required = false) Integer value,
                                             @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String due_to) {
        Optional<Order> orderData = orderServiceImpl.findById(id);
        if (orderData.isPresent()) {
            Order _order = orderData.get();
            if (title != null) _order.setTitle(title);
            if (description != null) _order.setDescription(description);
            if (skills != null) _order.setSkills(skills);
            if (value != null) _order.setValue(value);
            if (due_to != null) _order.setDue_to(LocalDateTime.parse(due_to, formatter));
            return new ResponseEntity<>(orderServiceImpl.save(_order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") Long id) {
        try {
            orderServiceImpl.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting order: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllOrders() {
        try {
            orderServiceImpl.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting all orders: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
