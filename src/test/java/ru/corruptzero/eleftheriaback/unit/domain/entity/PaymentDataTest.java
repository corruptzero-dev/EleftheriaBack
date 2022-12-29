package ru.corruptzero.eleftheriaback.unit.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import ru.corruptzero.eleftheriaback.configuration.JpaConfig;
import ru.corruptzero.eleftheriaback.domain.entity.PaymentData;

import javax.persistence.EntityManager;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = JpaConfig.class)
@SpringBootTest
class PaymentDataTest {
    private PaymentData paymentData;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @BeforeEach
    public void setUp() {
        paymentData = PaymentData.builder()
                .id(1L)
                .paymentMethod("PayPal")
                .accountNumber("user@example.com")
                .bankName("Bank of Test")
                .build();
    }

    @Test
    public void testPaymentDataGetters() {
        // Verify that the getters return the correct set values
        assertEquals(1L, paymentData.getId());
        assertEquals("PayPal", paymentData.getPaymentMethod());
        assertEquals("user@example.com", paymentData.getAccountNumber());
        assertEquals("Bank of Test", paymentData.getBankName());
    }

    @Test
    public void testPaymentDataSetters() {
        // Set new values using the setters
        paymentData.setId(2L);
        paymentData.setPaymentMethod("Bank Transfer");
        paymentData.setAccountNumber("123456");
        paymentData.setBankName("Test Bank");

        // Verify that the setters correctly set the values
        assertEquals(2L, paymentData.getId());
        assertEquals("Bank Transfer", paymentData.getPaymentMethod());
        assertEquals("123456", paymentData.getAccountNumber());
        assertEquals("Test Bank", paymentData.getBankName());
    }

    @Test
    public void testToString() {
        // Create a PaymentData object with test data
        PaymentData paymentData = PaymentData.builder()
                .id(1L)
                .paymentMethod("PayPal")
                .accountNumber("test@example.com")
                .bankName("Bank of Test")
                .build();

        // Get the string representation of the PaymentData object
        String paymentDataString = paymentData.toString();

        // Verify that the string contains the values of the relevant properties
        assertTrue(paymentDataString.contains("1"));
        assertTrue(paymentDataString.contains("PayPal"));
        assertTrue(paymentDataString.contains("test@example.com"));
        assertTrue(paymentDataString.contains("Bank of Test"));
    }

    @Test
    public void testHashCodeAndEquals() {
        // Create two PaymentData objects with the same fields
        PaymentData paymentData1 = PaymentData.builder()
                .id(1L)
                .paymentMethod("PayPal")
                .accountNumber("user@example.com")
                .build();
        PaymentData paymentData2 = PaymentData.builder()
                .id(1L)
                .paymentMethod("PayPal")
                .accountNumber("user@example.com")
                .build();
        // Verify that the two PaymentData objects have the same hash code
        assertEquals(paymentData1.hashCode(), paymentData2.hashCode());

        // Verify that the two PaymentData objects are equal by using the equals method and asserting that the result is true.
        assertTrue(paymentData1.equals(paymentData2));
    }

    @Test
    public void testPersistence() {
        EntityManager em = Objects.requireNonNull(entityManagerFactory.getObject()).createEntityManager();
        PaymentData managedPaymentData = em.merge(paymentData);
        // Persist the PaymentData object to the database using EntityManager
        em.getTransaction().begin();
        em.persist(managedPaymentData);
        em.getTransaction().commit();

        // Retrieve the PaymentData object from the database using EntityManager
        PaymentData retrievedPaymentData = em.find(PaymentData.class, managedPaymentData.getId());

        // Assert that the retrieved PaymentData object has the same properties as the original object
        assertEquals(managedPaymentData.getPaymentMethod(), retrievedPaymentData.getPaymentMethod());
        assertEquals(managedPaymentData.getAccountNumber(), retrievedPaymentData.getAccountNumber());
        assertEquals(managedPaymentData.getBankName(), retrievedPaymentData.getBankName());
        assertEquals(managedPaymentData.getUser(), retrievedPaymentData.getUser());

        // Remove the PaymentData object from the database using EntityManager
        em.getTransaction().begin();
        em.remove(retrievedPaymentData);
        em.getTransaction().commit();

        // Try to retrieve the PaymentData object from the database again and assert that it is null
        PaymentData deletedPaymentData = em.find(PaymentData.class, paymentData.getId());
        em.close();
        assertNull(deletedPaymentData);
    }
}
