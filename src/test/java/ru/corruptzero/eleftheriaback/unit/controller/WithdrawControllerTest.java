package ru.corruptzero.eleftheriaback.unit.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.corruptzero.eleftheriaback.controller.WithdrawController;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;
import ru.corruptzero.eleftheriaback.service.WithdrawService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus.COMPLETED;
import static ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus.PENDING;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WithdrawController.class)
class WithdrawControllerTest {

    // Mocking dependencies
    @MockBean
    private WithdrawService withdrawService;
    @Autowired
    private MockMvc mockMvc;

    // Test for the getAllWithdraws method
    @Test
    void testGetAllWithdraws() throws Exception {
        List<Withdraw> withdraws = Arrays.asList(
                Withdraw.builder()
                        .id(1L)
                        .status(PENDING)
                        .amount(100)
                        .currency("USD")
                        .user(User.builder().username("user1").email("user1@test.com").password("tester123").build())
                        .build(),
                Withdraw.builder()
                        .id(2L)
                        .status(COMPLETED)
                        .amount(200)
                        .currency("EUR")
                        .user(User.builder().username("user2").email("user2@test.com").password("tester345").build())
                        .build()
        );
        // Set up mock behavior for the withdrawService.getAllWithdraws method
        when(withdrawService.getAllWithdraws()).thenReturn(withdraws);

        // Perform the GET request and verify the response status and body
        mockMvc.perform(get("/api/v2/withdraws")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("PENDING"))
                .andExpect(jsonPath("$[0].amount").value(100))
                .andExpect(jsonPath("$[0].currency").value("USD"))
                .andExpect(jsonPath("$[0].user.username").value("user1"))
                .andExpect(jsonPath("$[0].user.email").value("user1@test.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].status").value("COMPLETED"))
                .andExpect(jsonPath("$[1].amount").value(200))
                .andExpect(jsonPath("$[1].currency").value("EUR"))
                .andExpect(jsonPath("$[1].user.username").value("user2"))
                .andExpect(jsonPath("$[1].user.email").value("user2@test.com"));
    }
    // Test for the getWithdrawById method
    @Test
    void testGetWithdrawById() throws Exception {
        Withdraw withdraw = Withdraw.builder()
                .id(1L)
                .status(PENDING)
                .amount(100)
                .currency("USD")
                .user(User.builder().username("user1").email("user1@test.com").password("tester123").build())
                .build();

        // Set up mock behavior for the withdrawService.findById method
        when(withdrawService.findById(1L)).thenReturn(Optional.of(withdraw));

        // Perform the GET request and verify the response status and body
        mockMvc.perform(get("/api/v2/withdraws/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.user.username").value("user1"))
                .andExpect(jsonPath("$.user.email").value("user1@test.com"));
    }
}
