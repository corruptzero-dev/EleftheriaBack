package ru.corruptzero.eleftheriaback.unit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.dto.PaymentDataDTO;
import ru.corruptzero.eleftheriaback.mapper.PaymentDataMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentDataMapperTest {

    private final PaymentDataMapper paymentDataMapper = PaymentDataMapper.INSTANCE;
    private PaymentData paymentData;
    private PaymentDataDTO paymentDataDTO;

    // Arrange
    @BeforeEach
    public void setUp() {
        paymentData = PaymentData.builder()
                .paymentMethod("PayPal")
                .accountNumber("test@example.com")
                .bankName("Test Bank")
                .user(User.builder().username("tester").build())
                .build();

        paymentDataDTO = PaymentDataDTO.builder()
                .paymentMethod("PayPal")
                .accountNumber("test@example.com")
                .bankName("Test Bank")
                .user(User.builder().username("tester").build())
                .build();
    }

    @Test
    public void testToDTO() {
        // Act
        PaymentDataDTO paymentDataDTO = paymentDataMapper.toDTO(paymentData);

        // Assert
        assertEquals(paymentData.getId(), paymentDataDTO.getId());
        assertEquals(paymentData.getPaymentMethod(), paymentDataDTO.getPaymentMethod());
        assertEquals(paymentData.getAccountNumber(), paymentDataDTO.getAccountNumber());
        assertEquals(paymentData.getBankName(), paymentDataDTO.getBankName());
        assertEquals(paymentData.getUser(), paymentDataDTO.getUser());
    }

    @Test
    public void testToEntity() {
        // Act
        PaymentData paymentData = paymentDataMapper.toEntity(paymentDataDTO);

        // Assert
        assertEquals(paymentData.getId(), paymentDataDTO.getId());
        assertEquals(paymentData.getPaymentMethod(), paymentDataDTO.getPaymentMethod());
        assertEquals(paymentData.getAccountNumber(), paymentDataDTO.getAccountNumber());
        assertEquals(paymentData.getBankName(), paymentDataDTO.getBankName());
        assertEquals(paymentData.getUser(), paymentDataDTO.getUser());
    }
}
