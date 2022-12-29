package ru.corruptzero.eleftheriaback.unit.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.corruptzero.eleftheriaback.controller.OrderController;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    // Mocking dependencies
    @MockBean
    private OrderService orderService;
    @Autowired
    private MockMvc mockMvc;

    // Test for the getAllOrders method
    @Test
    void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(
                Order.builder()
                        .id(1L)
                        .title("Order 1")
                        .description("Description for order 1")
                        .value(100)
                        .admin(User.builder()
                                .username("admin1")
                                .email("admin1@test.com")
                                .password("admin123")
                                .build())
                        .build(),
                Order.builder()
                        .id(2L)
                        .title("Order 2")
                        .description("Description for order 2")
                        .value(200)
                        .admin(User.builder()
                                .username("admin2")
                                .email("admin2@test.com")
                                .password("admin345")
                                .build())
                        .build()
        );
        // Set up mock behavior for the orderService.getAllOrders method
        when(orderService.getAllOrders()).thenReturn(orders);

        // Perform the GET request and verify the response status and body
        mockMvc.perform(get("/api/v2/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Order 1"))
                .andExpect(jsonPath("$[0].description").value("Description for order 1"))
                .andExpect(jsonPath("$[0].value").value(100))
                .andExpect(jsonPath("$[0].admin.username").value("admin1"))
                .andExpect(jsonPath("$[0].admin.email").value("admin1@test.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Order 2"))
                .andExpect(jsonPath("$[1].description").value("Description for order 2"))
                .andExpect(jsonPath("$[1].value").value(200))
                .andExpect(jsonPath("$[1].admin.username").value("admin2"))
                .andExpect(jsonPath("$[1].admin.email").value("admin2@test.com"));
    }

    // Test for the getOrderById method
    @Test
    void testGetOrderById() throws Exception {
        Order order = Order.builder()
                .id(1L)
                .title("Order 1")
                .description("Description for order 1")
                .value(100)
                .admin(User.builder()
                        .username("admin1")
                        .email("admin1@test.com")
                        .password("admin123")
                        .build())
                .build();

        // Set up mock behavior for the orderService.findById method
        when(orderService.findById(1L)).thenReturn(Optional.of(order));

        // Perform the GET request and verify the response status and body
        mockMvc.perform(get("/api/v2/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Order 1"))
                .andExpect(jsonPath("$.value").value(100))
                .andExpect(jsonPath("$.admin.username").value("admin1"))
                .andExpect(jsonPath("$.admin.email").value("admin1@test.com"));
    }
}