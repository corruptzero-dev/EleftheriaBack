package ru.corruptzero.eleftheriaback.service;

import ru.corruptzero.eleftheriaback.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    List<User> getAllUsers();
    Optional<User> findById(Long id);
    void deleteById(Long id);
    void deleteAll();

}
