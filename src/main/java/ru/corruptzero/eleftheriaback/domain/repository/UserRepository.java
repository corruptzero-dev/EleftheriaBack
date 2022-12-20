package ru.corruptzero.eleftheriaback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.corruptzero.eleftheriaback.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
}
