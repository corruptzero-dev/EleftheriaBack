package ru.corruptzero.eleftheriaback.unit.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.corruptzero.eleftheriaback.controller.UserController;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    // Mocking dependencies
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;


    // Test for the getAllUsers method
    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                User.builder()
                        .id(1L)
                        .username("user1")
                        .email("user1@test.com")
                        .password("tester1")
                        .build(),
                User.builder()
                        .id(2L)
                        .username("user2")
                        .email("user2@test.com")
                        .password("tester2")
                        .build()
        );
        // Set up mock behavior for the userService.getAllUsers method
        when(userService.findAll()).thenReturn(users);

        // Perform the GET request and verify the response status and body
        mockMvc.perform(get("/api/v2/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].email").value("user1@test.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].email").value("user2@test.com"));
    }

    // Test for the getUserById method
    @Test
    void testGetUserById() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .password("tester1")
                .build();

        // Set up mock behavior for the userService.findById method
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        // Perform the GET request and verify the response status and body
        mockMvc.perform(get("/api/v2/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.email").value("user1@test.com"));
    }
}

