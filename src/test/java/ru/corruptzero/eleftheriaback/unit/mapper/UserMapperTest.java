package ru.corruptzero.eleftheriaback.unit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.dto.UserDTO;
import ru.corruptzero.eleftheriaback.mapper.UserMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private User user;
    private UserDTO userDTO;

    // Arrange
    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("tester")
                .email("tester@test.com")
                .password("123456")
                .build();

        userDTO = UserDTO.builder()
                .username("tester")
                .email("tester@test.com")
                .password("123456")
                .build();
    }

    @Test
    public void testToDTO() {
        // Act
        UserDTO userDTO = userMapper.toDTO(user);

        // Assert
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }

    @Test
    public void testToEntity() {
        // Act
        User user = userMapper.toEntity(userDTO);

        // Assert
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
    }
}
