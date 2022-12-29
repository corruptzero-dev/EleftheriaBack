package ru.corruptzero.eleftheriaback.unit.domain.entity.order;

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
import ru.corruptzero.eleftheriaback.domain.entity.order.EOrderStatus;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.corruptzero.eleftheriaback.domain.entity.order.EOrderStatus.*;

@ContextConfiguration(classes = JpaConfig.class)
@SpringBootTest
class OrderTest {
    private Order order;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @BeforeEach
    public void setUp() {
        order = Order.builder()
                .id(1L)
                .title("Order Title")
                .description("Order Description")
                .skills(new HashSet<>())
                .value(100)
                .build();
    }

    @Test
    public void testGetters() {
        // Verify that the getters return the correct set-up values
        assertEquals(1L, order.getId());
        assertEquals("Order Title", order.getTitle());
        assertEquals("Order Description", order.getDescription());
        assertEquals(new HashSet<>(), order.getSkills());
        assertEquals(100, order.getValue());

        // Verify that the getters return the correct default values
        assertNotNull(order.getCreated_at());
        assertNotNull(order.getDue_to());
        assertEquals(order.getStatus(), ACTIVE);
        assertNull(order.getUser());
        assertNull(order.getAdmin());
    }

    @Test
    public void testSetters() {
        User user = new User();
        User admin = new User();
        Set<String> someskills = new HashSet<>();
        someskills.add("Powerpoint");
        // Set all the fields to new values
        order.setId(2L);
        order.setTitle("New Order Title");
        order.setDescription("New Order Description");
        order.setSkills(someskills);
        order.setValue(200);
        order.setCreated_at(LocalDateTime.now());
        order.setDue_to(LocalDateTime.now().plusDays(1));
        order.setStatus(EOrderStatus.PENDING);
        order.setUser(user);
        order.setAdmin(admin);

        // Verify that the set values can be retrieved correctly
        assertEquals(2L, order.getId());
        assertEquals("New Order Title", order.getTitle());
        assertEquals("New Order Description", order.getDescription());
        assertTrue(order.getSkills().contains("Powerpoint"));
        assertEquals(200, order.getValue());
        assertNotNull(order.getCreated_at());
        assertNotNull(order.getDue_to());
        assertEquals(order.getStatus(), EOrderStatus.PENDING);
        assertSame(order.getUser(), user);
        assertSame(order.getAdmin(), admin);
    }

    @Test
    public void testToString() {
        // Create a new Order object
        Order order = Order.builder()
                .id(1L)
                .title("Order Title")
                .description("Order Description")
                .skills(new HashSet<>())
                .value(100)
                .created_at(LocalDateTime.now())
                .due_to(LocalDateTime.now())
                .status(ACTIVE)
                .user(new User())
                .admin(new User())
                .build();

        // Call the toString method and store the result in a String
        String orderString = order.toString();

        // Verify that the String contains the values of all relevant properties of the Order object
        assertTrue(orderString.contains("1"));
        assertTrue(orderString.contains("Order Title"));
        assertTrue(orderString.contains("Order Description"));
        assertTrue(orderString.contains("skills"));
        assertTrue(orderString.contains("100"));
        assertTrue(orderString.contains("ACTIVE"));
        assertTrue(orderString.contains("created_at"));
        assertTrue(orderString.contains("due_to"));
        assertTrue(orderString.contains("user"));
        assertTrue(orderString.contains("admin"));
    }

    @Test
    public void testHashCodeAndEquals() {
        // Create two Order objects with the same fields
        Order order1 = Order.builder()
                .id(1L)
                .title("Order Title")
                .description("Order Description")
                .skills(new HashSet<>())
                .value(100)
                .build();
        Order order2 = Order.builder()
                .id(1L)
                .title("Order Title")
                .description("Order Description")
                .skills(new HashSet<>())
                .value(100)
                .build();

        // Verify that the two Order objects have the same hash code
        assertEquals(order1.hashCode(), order2.hashCode());

        // Verify that the two Order objects are equal by using the equals method and asserting that the result is true.
        assertTrue(order1.equals(order2));
    }

    @Test
    public void testOrderUserInteractions() {
        // Create a new order and user
        Order order = Order.builder().value(100).build();
        User user = User.builder().username("username").build();

        // Set the user on the order
        order.setUser(user);

        // Set the order on the user
        user.setOrder(order);

        // Check that the user is set on the order
        assertSame(order.getUser(), user);

        // Check that the order is set on the user
        assertSame(user.getOrder(), order);
    }

    @Test
    public void testPersistence() {
        EntityManager em = Objects.requireNonNull(entityManagerFactory.getObject()).createEntityManager();

        Order managedOrder = em.merge(order);
        managedOrder.getSkills().add("Microsoft Word");

        // Persist the Order object to the database using EntityManager
        EntityTransaction transaction1 = em.getTransaction();
        transaction1.begin();
        em.persist(managedOrder);
        transaction1.commit();

        // Retrieve the Order object from the database using EntityManager
        Order retrievedOrder = em.find(Order.class, managedOrder.getId());

        // Assert that the retrieved Order object has the same properties as the original object
        assertEquals(managedOrder.getTitle(), retrievedOrder.getTitle());
        assertEquals(managedOrder.getDescription(), retrievedOrder.getDescription());
        assertEquals(managedOrder.getSkills(), retrievedOrder.getSkills());
        assertEquals(managedOrder.getValue(), retrievedOrder.getValue());
        assertEquals(managedOrder.getStatus(), retrievedOrder.getStatus());

        // Remove the Order object from the database using EntityManager
        EntityTransaction transaction2 = em.getTransaction();
        transaction2.begin();
        em.remove(retrievedOrder);
        transaction2.commit();

        // Try to retrieve the Order object from the database again and assert that it is null
        Order deletedOrder = em.find(Order.class, order.getId());
        em.close();
        assertNull(deletedOrder);
    }

    /**
     * Provides a {@link Stream} of {@link Arguments} for the testOrderPersistenceWithInvalidData test.
     * <p>
     * Each {@code Arguments} object in the {@code Arguments} represents a set of invalid values for the title,
     * description, skills and value fields of an Order entity.
     * <p>
     * The Stream contains three Arguments objects, each with a different combination of invalid values
     * for the five fields.
     */
    private static Stream<Arguments> titleDescriptionSkillsValueProvider() {
        return Stream.of(
                Arguments.of("", "test order", Collections.singleton("skill"), 100),
                Arguments.of(" ", "test order", Collections.singleton("skill"), 100),
                Arguments.of("title", "", new HashSet<>(), -100),
                Arguments.of("title", " ", new HashSet<>(), -100,
                Arguments.of("title", "test order", Collections.singleton(""), 0),
                Arguments.of("title", "test order", Collections.singleton(" "), 0),
                Arguments.of("title", "test order", Collections.singleton("skill"), -100),
                Arguments.of("title", "test order", Collections.singleton("skill"), 100))
        );
    }

    /**
     * Test for persisting an Order entity with invalid data.
     * <p>
     * This test uses a parameterized test with a MethodSource to provide a range of invalid values
     * for the title, description, skills and value fields of the Order entity.
     * <p>
     * The test begins a transaction, creates a detached Order entity with the provided invalid data,
     * and attempts to persist the entity using the EntityManager.
     * <p>
     * The test expects a ConstraintViolationException to be thrown when the invalid entity is persisted,
     * and rolls back the transaction to undo any changes made during the test.
     */
    @ParameterizedTest
    @MethodSource("titleDescriptionSkillsValueProvider")
    public void testOrderPersistenceWithInvalidData(String title,
                                                    String description,
                                                    Set<String> skills,
                                                    Integer value) {
        // Create a detached Order object with the specified invalid data
        Order detachedOrder = new Order();
        detachedOrder.setTitle(title);
        detachedOrder.setDescription(description);
        detachedOrder.setSkills(skills);
        detachedOrder.setValue(value);;

        // Create an EntityManager
        EntityManager em = Objects.requireNonNull(entityManagerFactory.getObject()).createEntityManager();

        // Start a transaction and attempt to persist the detached Order object, so Hibernate checks for constraints
        EntityTransaction transaction1 = em.getTransaction();
        transaction1.begin();
        assertThrows(ConstraintViolationException.class, () -> em.persist(detachedOrder));

        // If an exception is thrown, rollback the transaction
        transaction1.rollback();
        em.close();
    }
}

