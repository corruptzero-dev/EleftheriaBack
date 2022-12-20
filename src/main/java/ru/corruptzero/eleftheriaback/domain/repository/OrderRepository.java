package ru.corruptzero.eleftheriaback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.corruptzero.eleftheriaback.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
