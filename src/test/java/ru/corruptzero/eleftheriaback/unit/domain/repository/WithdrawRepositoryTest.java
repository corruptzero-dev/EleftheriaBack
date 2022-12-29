package ru.corruptzero.eleftheriaback.unit.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;
import ru.corruptzero.eleftheriaback.domain.repository.UserRepository;
import ru.corruptzero.eleftheriaback.domain.repository.WithdrawRepository;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class WithdrawRepositoryTest {

    @Autowired
    WithdrawRepository withdrawRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void testFindPendingWithdraws() {
        // Arrange
        User user = User.builder()
                .username("tester")
                .email("test@example.com")
                .password("123456")
                .build();
        userRepository.save(user);

        Withdraw withdraw = Withdraw.builder()
                .amount(50)
                .status(EWithdrawStatus.PENDING)
                .currency("USD")
                .user(user)
                .build();
        withdrawRepository.save(withdraw);

        try {
            // Act
            List<Withdraw> pendingWithdraws = withdrawRepository.findAllByStatus(EWithdrawStatus.PENDING);

            // Assert
            assertTrue(pendingWithdraws.contains(withdraw));
        } finally {
            // Clean up
            withdrawRepository.delete(withdraw);
            userRepository.delete(user);
        }
    }
}
