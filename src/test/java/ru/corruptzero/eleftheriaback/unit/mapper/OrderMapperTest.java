package ru.corruptzero.eleftheriaback.unit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.dto.OrderDTO;
import ru.corruptzero.eleftheriaback.mapper.OrderMapper;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderMapperTest {

    private final OrderMapper orderMapper = OrderMapper.INSTANCE;
    private OrderDTO orderDTO;
    private Order order;

    //Arrange
    @BeforeEach
    public void setUp() {
        order = Order.builder()
                .title("test title")
                .description("test description")
                .value(500)
                .admin(new User())
                .skills(new HashSet<>())
                .due_to(LocalDateTime.now())
                .build();
        orderDTO = OrderDTO.builder()
                .title("test title")
                .description("test description")
                .value(500)
                .admin(new User())
                .skills(new HashSet<>())
                .due_to(LocalDateTime.now())
                .build();
    }

    @Test
    public void testToDTO() {
        // Act
        OrderDTO orderDTO = orderMapper.toDTO(order);

        // Assert
        assertEquals(order.getTitle(), orderDTO.getTitle());
        assertEquals(order.getDescription(), orderDTO.getDescription());
        assertEquals(order.getValue(), orderDTO.getValue());
        assertEquals(order.getAdmin(), orderDTO.getAdmin());
        assertEquals(order.getSkills(), orderDTO.getSkills());
        assertEquals(order.getDue_to(), orderDTO.getDue_to());
    }

    @Test
    public void testToEntity() {
        // Act
        Order order = orderMapper.toEntity(orderDTO);

        // Assert
        assertEquals(order.getTitle(), orderDTO.getTitle());
        assertEquals(order.getDescription(), orderDTO.getDescription());
        assertEquals(order.getValue(), orderDTO.getValue());
        assertEquals(order.getAdmin(), orderDTO.getAdmin());
        assertEquals(order.getSkills(), orderDTO.getSkills());
        assertEquals(order.getDue_to(), orderDTO.getDue_to());
    }
}