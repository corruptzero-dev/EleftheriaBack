package ru.corruptzero.eleftheriaback.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.repository.UserRepository;
import ru.corruptzero.eleftheriaback.exception.EmptyEntityListException;
import ru.corruptzero.eleftheriaback.exception.EntityDeleteException;
import ru.corruptzero.eleftheriaback.exception.EntityNotFoundException;
import ru.corruptzero.eleftheriaback.exception.InvalidEntityException;
import ru.corruptzero.eleftheriaback.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        List<User> users = new ArrayList<>(userRepository.findAll());
        if (users.isEmpty()) {
            throw new EmptyEntityListException("No users found.");
        }
        return users;
    }

    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        return user;
    }

    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new InvalidEntityException("A user with the same email already exists.");
        }
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityDeleteException("Failed to delete user with ID: " + id + ". Entity not found.");
        } catch (DataAccessException e) {
            throw new EntityDeleteException("Failed to delete user with ID: " + id + ". Data access error occurred.");
        }
    }

    public void deleteAll() {
        try {
            userRepository.deleteAll();
        } catch (DataAccessException e) {
            throw new EntityDeleteException("Failed to delete all users. Data access error occurred.");
        }
    }
}
