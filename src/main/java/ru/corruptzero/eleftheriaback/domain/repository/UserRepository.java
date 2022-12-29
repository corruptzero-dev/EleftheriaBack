package ru.corruptzero.eleftheriaback.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;

/**
 * Repository interface for {@link User} entities.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Returns whether a user with the given email already exists.
     *
     * @param email the email to check
     * @return {@code true} if a user with the given email exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Returns whether a user with the given email and password exists.
     *
     * @param email    the email to check
     * @param password the password to check
     * @return {@code true} if a user with the given email and password exists, {@code false} otherwise
     */
    boolean existsByEmailAndPassword(String email, String password);
}
