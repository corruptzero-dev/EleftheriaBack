package ru.corruptzero.eleftheriaback.unit.domain.entity.withdraw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import ru.corruptzero.eleftheriaback.configuration.JpaConfig;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus.COMPLETED;
import static ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus.PENDING;

@ContextConfiguration(classes = JpaConfig.class)
@SpringBootTest
class WithdrawTest {
    private Withdraw withdraw;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @BeforeEach
    public void setUp() {
        withdraw = Withdraw.builder()
                .id(1L)
                .status(PENDING)
                .amount(100)
                .currency("USD")
                .build();
    }

    @Test
    public void testGetters() {
        // Verify that the getters return the correct set-up values
        assertEquals(1L, withdraw.getId());
        assertEquals(PENDING, withdraw.getStatus());
        assertEquals(100, withdraw.getAmount());
        assertEquals("USD", withdraw.getCurrency());

        // Verify that the getters return the correct default values
        assertNotNull(withdraw.getCreated_at());
        assertNull(withdraw.getUser());
    }

    @Test
    public void testSetters() {
        User user = new User();
        // Set all the fields to new values
        withdraw.setId(2L);
        withdraw.setStatus(PENDING);
        withdraw.setAmount(200);
        withdraw.setCurrency("EUR");
        withdraw.setCreated_at(LocalDateTime.now());
        withdraw.setUser(user);

        // Verify that the set values can be retrieved correctly
        assertEquals(2L, withdraw.getId());
        assertEquals(PENDING, withdraw.getStatus());
        assertEquals(200, withdraw.getAmount());
        assertEquals("EUR", withdraw.getCurrency());
        assertNotNull(withdraw.getCreated_at());
        assertSame(withdraw.getUser(), user);
    }

    @Test
    public void testToString() {
        // Create a new Withdraw object
        Withdraw withdraw = Withdraw.builder()
                .id(1L)
                .status(PENDING)
                .created_at(LocalDateTime.now())
                .amount(100)
                .currency("USD")
                .user(new User())
                .build();

        // Call the toString method and store the result in a String
        String withdrawString = withdraw.toString();

        // Verify that the String contains the values of all relevant properties of the Withdraw object
        assertTrue(withdrawString.contains("1"));
        assertTrue(withdrawString.contains("PENDING"));
        assertTrue(withdrawString.contains("created_at"));
        assertTrue(withdrawString.contains("100"));
        assertTrue(withdrawString.contains("USD"));
        assertTrue(withdrawString.contains("user"));
    }

    @Test
    public void testHashCodeAndEquals() {
        // Create two Withdraw objects with the same fields
        Withdraw withdraw1 = Withdraw.builder()
                .id(1L)
                .status(PENDING)
                .amount(100)
                .currency("USD")
                .build();
        Withdraw withdraw2 = Withdraw.builder()
                .id(1L)
                .status(PENDING)
                .amount(100)
                .currency("USD")
                .build();

        // Verify that the two Withdraw objects have the same hash code
        assertEquals(withdraw1.hashCode(), withdraw2.hashCode());

        // Verify that the two Withdraw objects are equal by using the equals method and asserting that the result is true.
        assertTrue(withdraw1.equals(withdraw2));
    }

    @Test
    public void testPersistence() {
        EntityManager em = Objects.requireNonNull(entityManagerFactory.getObject()).createEntityManager();
        Withdraw managedWithdraw = em.merge(withdraw);

        // Persist the Withdraw object to the database using EntityManager
        em.getTransaction().begin();
        em.persist(managedWithdraw);
        em.getTransaction().commit();

        // Retrieve the Withdraw object from the database using EntityManager
        Withdraw retrievedWithdraw = em.find(Withdraw.class, managedWithdraw.getId());

        // Assert that the retrieved Withdraw object has the same properties as the original object
        assertEquals(managedWithdraw.getStatus(), retrievedWithdraw.getStatus());
        assertEquals(managedWithdraw.getAmount(), retrievedWithdraw.getAmount());
        assertEquals(managedWithdraw.getCurrency(), retrievedWithdraw.getCurrency());
        assertEquals(managedWithdraw.getUser(), retrievedWithdraw.getUser());

        // Remove the Withdraw object from the database using EntityManager
        em.getTransaction().begin();
        em.remove(retrievedWithdraw);
        em.getTransaction().commit();

        // Try to retrieve the Withdraw object from the database again and assert that it is null
        Withdraw deletedWithdraw = em.find(Withdraw.class, withdraw.getId());
        em.close();
        assertNull(deletedWithdraw);
    }
}

