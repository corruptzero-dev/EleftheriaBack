package ru.corruptzero.eleftheriaback.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.corruptzero.eleftheriaback.domain.repository.UserRepository;
import ru.corruptzero.eleftheriaback.service.impl.UserServiceImpl;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryMockitoTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();


    @Test
    void getAllPerson() {
        userService.getAllUsers();
        verify(repository).findAll();
    }
}
