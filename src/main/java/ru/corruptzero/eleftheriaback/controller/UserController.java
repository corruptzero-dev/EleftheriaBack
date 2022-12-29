package ru.corruptzero.eleftheriaback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.dto.UserDTO;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;
import ru.corruptzero.eleftheriaback.mapper.UserMapper;
import ru.corruptzero.eleftheriaback.service.UserService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code UserController} class is a RESTful web service controller for managing users.
 * It maps HTTP requests to methods that perform CRUD operations on the {@link User} entity.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@RestController
@RequestMapping("/api/v2/users")
@Slf4j
public class UserController {

    private final UserMapper userMapper = UserMapper.INSTANCE;
    @Autowired
    private UserService userService;

    /**
     * Handles a GET request to retrieve all users from the database.
     *
     * @return a {@link ResponseEntity} containing a list of {@link UserDTO} objects and an HTTP status code
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOs = users.stream().map(userMapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    /**
     * Handles a GET request to retrieve a user by its id.
     *
     * @param id the id of the user to retrieve
     * @return a {@link ResponseEntity} containing an {@link UserDTO} object and an HTTP status code
     */
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        Optional<User> userData = userService.findById(id);
        return userData.map(user -> {
            UserDTO userDTO = userMapper.toDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Handles a POST request to create a new user.
     *
     * @param userDTO       the {@link UserDTO} object representing the user to create
     * @param bindingResult the result of the input validation
     * @return a {@link ResponseEntity} containing the created {@link UserDTO} object and an HTTP status code
     * @throws InvalidEntityException if the input is invalid
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidEntityException("Invalid user: " + bindingResult.getAllErrors());
        }
        User user = userMapper.toEntity(userDTO);
        user.setCreated_at(LocalDateTime.now());
        User createdUser = userService.save(user);
        UserDTO createdUserDTO = userMapper.toDTO(createdUser);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    /**
     * Handles a PATCH request to update an existing user.
     *
     * @param id      the id of the user to update
     * @param userDTO the updated user data
     * @return a {@link ResponseEntity} containing an {@link UserDTO} object and an HTTP status code
     */
    @PatchMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        Optional<User> userData = userService.findById(id);
        if (userData.isPresent()) {
            User user = userData.get();
            User updatedUser = userMapper.toEntity(userDTO);
            if (updatedUser.getUsername()!=null && !updatedUser.getUsername().isBlank()) {
                user.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getPaymentData()!=null && !updatedUser.getPaymentData().isEmpty()) {
                user.setPaymentData(updatedUser.getPaymentData());
            }
            if (updatedUser.getEmail()!=null && !updatedUser.getEmail().isBlank()) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword()!=null && !updatedUser.getPassword().isBlank()) {
                user.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getRole()!=null) {
                user.setRole(updatedUser.getRole());
            }
            if (updatedUser.getBalance()!=null && updatedUser.getBalance()>=0) {
                user.setBalance(updatedUser.getBalance());
            }
            return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles a DELETE request to delete a single user by its id.
     *
     * @param id the id of the user to delete
     * @return a {@link ResponseEntity} containing an HTTP status code
     */
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Handles a DELETE request to delete all users.
     *
     * <p><strong>Note:</strong> This method is only intended for testing purposes.</p>
     *
     * @return a {@link ResponseEntity} containing an HTTP status code
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllUsers() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}