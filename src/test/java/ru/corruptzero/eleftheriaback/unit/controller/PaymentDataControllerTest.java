package ru.corruptzero.eleftheriaback.unit.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.corruptzero.eleftheriaback.controller.PaymentDataController;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.service.PaymentDataService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PaymentDataController.class)
class PaymentDataControllerTest {

    // Mocking dependencies
    @MockBean
    private PaymentDataService paymentDataService;
    @Autowired
    private MockMvc mockMvc;

    // Test for the getAllPaymentData method
    @Test
    void testGetAllPaymentData() throws Exception {
        List<PaymentData> paymentDataList = Arrays.asList(
                PaymentData.builder()
                        .id(1L)
                        .paymentMethod("PayPal")
                        .accountNumber("user1@test.com")
                        .user(User.builder()
                                .username("user1")
                                .email("user1@test.com")
                                .password("tester123")
                                .build())
                        .build(),
                PaymentData.builder()
                        .id(2L)
                        .paymentMethod("Bank Transfer")
                        .accountNumber("1234567890")
                        .bankName("Test Bank")
                        .user(User.builder()
                                .username("user2")
                                .email("user2@test.com")
                                .password("tester345")
                                .build())
                        .build()
        );
        // Set up mock behavior for the paymentDataService.getAllPaymentData method
        when(paymentDataService.getAllPaymentData()).thenReturn(paymentDataList);

        // Perform the GET request and verify the response status and body
        mockMvc.perform(get("/api/v2/payment_data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].paymentMethod").value("PayPal"))
                .andExpect(jsonPath("$[0].accountNumber").value("user1@test.com"))
                .andExpect(jsonPath("$[0].user.username").value("user1"))
                .andExpect(jsonPath("$[0].user.email").value("user1@test.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].paymentMethod").value("Bank Transfer"))
                .andExpect(jsonPath("$[1].accountNumber").value("1234567890"))
                .andExpect(jsonPath("$[1].bankName").value("Test Bank"))
                .andExpect(jsonPath("$[1].user.username").value("user2"))
                .andExpect(jsonPath("$[1].user.email").value("user2@test.com"));
    }

    // Test for the getPaymentDataById method
    @Test
    void testGetPaymentDataById() throws Exception {
        PaymentData paymentData = PaymentData.builder()
                .id(1L)
                .paymentMethod("PayPal")
                .accountNumber("user1@test.com")
                .user(User.builder()
                        .username("user1")
                        .email("user1@test.com")
                        .password("tester123")
                        .build())
                .build();

        // Set up mock behavior for the paymentDataService.findById method
        when(paymentDataService.findById(1L)).thenReturn(Optional.of(paymentData));

        // Perform the GET request and verify the response status and body
        mockMvc.perform(get("/api/v2/payment_data/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.paymentMethod").value("PayPal"))
                .andExpect(jsonPath("$.accountNumber").value("user1@test.com"))
                .andExpect(jsonPath("$.user.username").value("user1"))
                .andExpect(jsonPath("$.user.email").value("user1@test.com"));
    }
}