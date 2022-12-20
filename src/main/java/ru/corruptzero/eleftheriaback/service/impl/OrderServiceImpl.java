package ru.corruptzero.eleftheriaback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.corruptzero.eleftheriaback.domain.entity.Order;
import ru.corruptzero.eleftheriaback.domain.repository.OrderRepository;
import ru.corruptzero.eleftheriaback.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return new ArrayList<>(orderRepository.findAll());
    }

    public Optional<Order> findById(Long id){
        return orderRepository.findById(id);
    }

    public Order save(Order order){
        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
