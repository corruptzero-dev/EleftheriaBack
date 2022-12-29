package ru.corruptzero.eleftheriaback.unit.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Test
    public void testExistsByEmail() {
        // Arrange
        User user = User.builder()
                .username("tester")
                .email("test@example.com")
                .password("123456")
                .build();
        repository.save(user);

        try {
            // Act
            boolean exists = repository.existsByEmail("test@example.com");

            // Assert
            assertTrue(exists);
        } finally {
            // Clean up
            repository.delete(user);
        }
    }

    @Test
    public void testExistsByEmailAndPassword() {
        // Arrange
        User user = User.builder()
                .username("tester")
                .email("test@example.com")
                .password("123456")
                .build();
        repository.save(user);

        try {
            // Act
            boolean exists = repository.existsByEmailAndPassword("test@example.com", "123456");

            // Assert
            assertTrue(exists);
        } finally {
            // Clean up
            repository.delete(user);
        }
    }
}
