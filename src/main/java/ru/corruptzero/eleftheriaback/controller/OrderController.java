package ru.corruptzero.eleftheriaback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.dto.OrderDTO;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;
import ru.corruptzero.eleftheriaback.mapper.OrderMapper;
import ru.corruptzero.eleftheriaback.service.OrderService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code OrderController} class is a RESTful web service controller for managing orders.
 * It maps HTTP requests to methods that perform CRUD operations on the {@link Order} entity.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@RestController
@RequestMapping("/api/v2/orders")
@Slf4j
public class OrderController {

    /**
     * Mapper object for mapping between Withdraw and WithdrawDTO objects.
     */
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;

    /**
     * Service object for handling withdraw-related logic.
     */
    @Autowired
    private OrderService orderService;


    /**
     * Handles a GET request to retrieve all orders from the database.
     *
     * @return a {@link ResponseEntity} containing a list of {@link OrderDTO} objects and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDTO> orderDTOs = orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    /**
     * Handles a GET request to retrieve an order by its id.
     *
     * @param id the id of the order to retrieve
     * @return a {@link ResponseEntity} containing an {@link OrderDTO} object and an HTTP status code
     */
    @GetMapping("{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") Long id) {
        Optional<Order> orderData = orderService.findById(id);
        return orderData.map(order -> {
            OrderDTO orderDTO = orderMapper.toDTO(order);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Handles a POST request to create a new order.
     *
     * @param orderDTO      the {@link OrderDTO} object representing the order to create
     * @param bindingResult the result of the input validation
     * @return a {@link ResponseEntity} containing the created {@link OrderDTO} object and an HTTP status code
     * @throws InvalidEntityException if the input is invalid
     */
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidEntityException("Invalid order: " + bindingResult.getAllErrors());
        }
        Order order = orderMapper.toEntity(orderDTO);
        order.setCreated_at(LocalDateTime.now());
        Order createdOrder = orderService.save(order);
        OrderDTO createdOrderDTO = orderMapper.toDTO(createdOrder);
        return new ResponseEntity<>(createdOrderDTO, HttpStatus.CREATED);
    }

    /**
     * Handles a PATCH request to update an existing order.
     *
     * @param id       the id of the order to update
     * @param orderDTO the updated order data
     * @return a {@link ResponseEntity} containing an {@link OrderDTO} object and an HTTP status code
     */
    @PatchMapping("{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
        Optional<Order> orderData = orderService.findById(id);
        if (orderData.isPresent()) {
            Order order = orderData.get();
            Order updatedOrder = orderMapper.toEntity(orderDTO);
            if (updatedOrder.getTitle()!=null && !updatedOrder.getTitle().isBlank()){
                order.setTitle(updatedOrder.getTitle());
            }
            if (updatedOrder.getDescription()!=null && !updatedOrder.getDescription().isBlank()){
                order.setDescription(updatedOrder.getDescription());
            }
            if (updatedOrder.getSkills()!=null && !updatedOrder.getSkills().isEmpty()){
                order.setSkills(updatedOrder.getSkills());
            }
            if (updatedOrder.getValue()!=null && updatedOrder.getValue()>0){
                order.setValue(updatedOrder.getValue());
            }
            if (updatedOrder.getDue_to()!=null){
                order.setDue_to(updatedOrder.getDue_to());
            }
            if (updatedOrder.getStatus()!=null){
                order.setStatus(updatedOrder.getStatus());
            }
            Order savedOrder = orderService.save(order);
            OrderDTO savedOrderDTO = orderMapper.toDTO(savedOrder);
            return new ResponseEntity<>(savedOrderDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles a DELETE request to delete a single order by its id.
     *
     * @param id the id of the order to delete
     * @return a {@link ResponseEntity} containing an HTTP status code
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles a DELETE request to delete all orders.
     *
     * <p><strong>Note:</strong> This method is only intended for testing purposes.</p>
     *
     * @return a {@link ResponseEntity} containing an HTTP status code
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllOrders() {
        orderService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}