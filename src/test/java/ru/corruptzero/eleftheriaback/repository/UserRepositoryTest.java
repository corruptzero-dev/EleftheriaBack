package ru.corruptzero.eleftheriaback.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.corruptzero.eleftheriaback.domain.entity.User;
import ru.corruptzero.eleftheriaback.domain.repository.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void isUserExistById(){
        User user = User.builder().username("test").email("tester@test.ru").password("234234").build();
        if (!repository.existsByEmail("tester@test.ru")){
            repository.save(user);
        }
        assertThat(repository.existsByEmail("tester@test.ru")).isTrue();
    }
}
