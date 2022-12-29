package ru.corruptzero.eleftheriaback.unit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;
import ru.corruptzero.eleftheriaback.dto.WithdrawDTO;
import ru.corruptzero.eleftheriaback.mapper.WithdrawMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WithdrawMapperTest {

    private final WithdrawMapper withdrawMapper = WithdrawMapper.INSTANCE;
    private Withdraw withdraw;
    private WithdrawDTO withdrawDTO;

    //Arrange
    @BeforeEach
    public void setUp() {
        withdraw = Withdraw.builder()
                .amount(500)
                .currency("USD")
                .user(User.builder().username("tester").build())
                .build();
        withdrawDTO = WithdrawDTO.builder()
                .amount(500)
                .currency("USD")
                .user(User.builder().username("tester").build())
                .build();
    }

    @Test
    public void testToDTO() {
        // Act
        WithdrawDTO withdrawDTO = withdrawMapper.toDTO(withdraw);

        // Assert
        assertEquals(withdraw.getId(), withdrawDTO.getId());
        assertEquals(withdraw.getAmount(), withdrawDTO.getAmount());
        assertEquals(withdraw.getCurrency(), withdrawDTO.getCurrency());
        assertEquals(withdraw.getUser(), withdrawDTO.getUser());
    }

    @Test
    public void testToEntity() {
        // Act
        Withdraw withdraw = WithdrawMapper.INSTANCE.toEntity(withdrawDTO);

        // Assert
        assertEquals(withdraw.getId(), withdrawDTO.getId());
        assertEquals(withdraw.getAmount(), withdrawDTO.getAmount());
        assertEquals(withdraw.getCurrency(), withdrawDTO.getCurrency());
        assertEquals(withdraw.getUser(), withdrawDTO.getUser());
    }
}
