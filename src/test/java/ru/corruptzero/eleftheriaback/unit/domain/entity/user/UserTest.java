package ru.corruptzero.eleftheriaback.unit.domain.entity.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import ru.corruptzero.eleftheriaback.configuration.JpaConfig;
import ru.corruptzero.eleftheriaback.domain.entity.order.Order;
import ru.corruptzero.eleftheriaback.domain.entity.user.EUserRole;
import ru.corruptzero.eleftheriaback.domain.entity.user.User;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.Withdraw;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = JpaConfig.class)
@SpringBootTest
class UserTest {
    private User user;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;



    /**
     * Set up method that is run before each test.
     * <p>
     * This method creates a new User object with predefined values for its fields.
     * It is called before each test method to ensure that each test is run with a "fresh" User object.
     */
    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .username("user")
                .email("user@example.com")
                .password("password")
                .balance(100)
                .build();
    }

    /**
     * Test for the getter methods of the User class.
     * <p>
     * This test uses assertions to verify that the getter methods return the correct set-up values and default values
     * for a User object.
     */
    @Test
    public void testGetters() {
        // Verify that the getters return the correct set-up values
        assertEquals(1L, user.getId());
        assertEquals("user", user.getUsername());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("password", user.getPassword());

        // Verify that the getters return the correct default values
        assertEquals(user.getBalance(), 100);
        assertEquals(new HashSet<Withdraw>(), user.getWithdraws());
        assertEquals(user.getRole(), EUserRole.USER);
        assertNull(user.getOrder());
        assertEquals(user.getPaymentData(), new HashSet<>());
        assertNotNull(user.getCreated_at());
    }

    /**
     * Test for the setter methods of the User class.
     * <p>
     * This test calls the setter methods on a User object to set all of its fields to new values.
     * It then uses assertions to verify that the set values can be retrieved correctly using the
     * corresponding getter methods.
     */
    @Test
    public void testSetters() {
        Order order = new Order();
        // Set all the fields to new values
        user.setId(2L);
        user.setUsername("newUsername");
        user.setEmail("newEmail@example.com");
        user.setPassword("newPassword");
        user.setBalance(10);
        user.setWithdraws(new HashSet<>());
        user.setRole(EUserRole.ADMIN);
        user.setOrder(order);
        user.setPaymentData(new HashSet<>());
        user.setCreated_at(LocalDateTime.now());

        // Verify that the set values can be retrieved correctly
        assertEquals(2L, user.getId());
        assertEquals("newUsername", user.getUsername());
        assertEquals("newEmail@example.com", user.getEmail());
        assertEquals("newPassword", user.getPassword());
        assertEquals(10, user.getBalance());
        assertEquals(new HashSet<>(), user.getWithdraws());
        assertEquals(EUserRole.ADMIN, user.getRole());
        assertSame(user.getOrder(), order);
        assertEquals(user.getPaymentData(), new HashSet<>());
        assertNotNull(user.getCreated_at());
    }

    /**
     * Test for the hashCode and equals methods of the User class.
     * <p>
     * This test creates two User objects with the same fields and calls the hashCode method on both objects.
     * It then asserts that the two User objects have the same hash code.
     * <p>
     * The test also calls the equals method on the two User objects and asserts that they are equal.
     */
    @Test
    public void testHashCodeAndEquals() {
        // Create two User objects with the same fields
        User user1 = User.builder()
                .id(1L)
                .username("user")
                .email("user@example.com")
                .password("password")
                .build();
        User user2 = User.builder()
                .id(1L)
                .username("user")
                .email("user@example.com")
                .password("password")
                .build();

        // Verify that the two User objects have the same hash code
        assertEquals(user1.hashCode(), user2.hashCode());

        // Verify that the two User objects are equal by using the equals method and asserting that the result is true.
        assertTrue(user1.equals(user2));
    }

    /**
     * Test for the toString method of the User class.
     * <p>
     * This test creates a new User object and calls the toString method on it.
     * It then stores the result in a String and uses assertions to verify that the String contains
     * the values of all relevant properties of the User object.
     */
    @Test
    public void testToString() {
        // Create a new User object
        User user = User.builder()
                .id(1L)
                .username("user")
                .email("user@example.com")
                .password("password")
                .balance(10)
                .withdraws(new HashSet<>())
                .role(EUserRole.ADMIN)
                .order(new Order())
                .paymentData(new HashSet<>())
                .created_at(LocalDateTime.now())
                .build();

        // Call the toString method and store the result in a String
        String userString = user.toString();

        // Verify that the String contains the values of all relevant properties of the User object
        assertTrue(userString.contains("1"));
        assertTrue(userString.contains("user"));
        assertTrue(userString.contains("user@example.com"));
        assertTrue(userString.contains("password"));
        assertTrue(userString.contains("10"));
        assertTrue(userString.contains("ADMIN"));
        assertTrue(userString.contains("created_at"));
    }

    /**
     * Test for setting and getting a User and Order entity using their respective setter and getter methods.
     * <p>
     * This test creates a new User and Order entity and sets the Order on the User and the User on the Order
     * using their respective setter methods.
     * <p>
     * It then uses the getter methods to retrieve the Order from the User and the User from the Order and
     * asserts that they are the same objects as the original entities.
     */
    @Test
    public void testUserOrderInteractions() {
        // Create a new user and order
        User user = User.builder().username("test_user").build();
        Order order = Order.builder().value(100).build();

        // Set the order on the user
        user.setOrder(order);

        // Set the user on the order
        order.setUser(user);

        // Check that the order is set on the user
        assertSame(user.getOrder(), order);

        // Check that the user is set on the order
        assertSame(order.getUser(), user);
    }

    /**
     * Test for persisting, retrieving, and deleting a User entity using EntityManager.
     * <p>
     * This test creates a User entity and persists it to the database using EntityManager.
     * It then retrieves the entity from the database and asserts that it has the same properties
     * as the original object.
     * <p>
     * The test then removes the entity from the database using EntityManager and attempts to
     * retrieve it again, asserting that it is no longer present in the database.
     */
    @Test
    public void testPersistence() {

        EntityManager em = Objects.requireNonNull(entityManagerFactory.getObject()).createEntityManager();
        User managedUser = em.merge(user);

        // Persist the User object to the database using EntityManager
        EntityTransaction transaction1 = em.getTransaction();
        em.getTransaction().begin();
        em.persist(managedUser);
        transaction1.commit();

        // Retrieve the User object from the database using EntityManager
        User retrievedUser = em.find(User.class, managedUser.getId());

        // Assert that the retrieved User object has the same properties as the original object
        assertEquals(managedUser.getUsername(), retrievedUser.getUsername());
        assertEquals(managedUser.getEmail(), retrievedUser.getEmail());
        assertEquals(managedUser.getPassword(), retrievedUser.getPassword());
        assertEquals(managedUser.getBalance(), retrievedUser.getBalance());
        assertEquals(managedUser.getRole(), retrievedUser.getRole());


        // Remove the User object from the database using EntityManager
        EntityTransaction transaction2 = em.getTransaction();
        transaction2.begin();
        em.remove(retrievedUser);
        transaction2.commit();

        // Try to retrieve the User object from the database again and assert that it is null
        User deletedUser = em.find(User.class, user.getId());
        em.close();
        assertNull(deletedUser);
    }


    /**
     * Test the CRUD operations of the {@link User} entity using an {@link EntityManager}.
     * <p>
     * This test method takes a username, email, password, and balance as input parameters.
     * It uses these parameters to create a new {@link User} object and persist it to the database using an {@link EntityManager}.
     * Then it retrieves the object from the database and asserts that its values are equal to the original object's values.
     * <p>
     * It then performs a simple query to retrieve a list of all users from the database, and asserts that the list is not empty.
     * <p>
     * It then retrieves a user from the database and updates one of its fields.
     * It persists the changes to the database using the {@link EntityManager} and then retrieves the user from the database again
     * to assert that the field was updated.
     * <p>
     * Finally, it retrieves a user from the database and removes it using the {@link EntityManager}.
     * It then tries to retrieve the user from the database again and asserts that it is null.
     * @param username the username of the user to create
     * @param email the email of the user to create
     * @param password the password of the user to create
     * @param balance the balance of the user to create
     */
    @ParameterizedTest
    @CsvSource({
            "testuser1, testuser1@example.com, password1, 100",
            "testuser2, testuser2@example.com, password2, 200",
            "testuser3, testuser3@example.com, password3, 300"
    })
    public void testUserCRUD(String username, String email, String password, Integer balance) {
        EntityManager em = Objects.requireNonNull(entityManagerFactory.getObject()).createEntityManager();
        EntityTransaction transaction1 = em.getTransaction();
        // Create a new User object and persist it to the database using EntityManager
        User detachedUser = new User();
        detachedUser.setId(0L);
        detachedUser.setUsername(username);
        detachedUser.setEmail(email);
        detachedUser.setPassword(password);
        detachedUser.setBalance(balance);
        User managedUser = em.merge(detachedUser);
        transaction1.begin();
        em.persist(managedUser);
        transaction1.commit();

        // Retrieve the User object from the database and assert that it has the same values as the original object
        User retrievedUser = em.find(User.class, managedUser.getId());
        assertEquals(managedUser.getUsername(), retrievedUser.getUsername());
        assertEquals(managedUser.getEmail(), retrievedUser.getEmail());
        assertEquals(managedUser.getPassword(), retrievedUser.getPassword());
        assertEquals(managedUser.getBalance(), retrievedUser.getBalance());

        // Perform a simple query to retrieve a list of users from the database
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        // Assert that the list of users is not empty
        assertFalse(users.isEmpty());

        // Retrieve a user from the database and update one of its fields
        User userToFind = em.find(User.class, managedUser.getId());
        userToFind.setUsername("new username");

        // Persist the changes to the database using EntityManager
        EntityTransaction transaction2 = em.getTransaction();
        transaction2.begin();
        em.merge(userToFind);
        transaction2.commit();

        // Retrieve the user from the database again and assert that the field was updated
        User updatedUser = em.find(User.class, managedUser.getId());
        assertEquals(userToFind.getUsername(), updatedUser.getUsername());

        // Retrieve a user from the database and remove it using EntityManager
        EntityTransaction transaction3 = em.getTransaction();
        User user = em.find(User.class, managedUser.getId());
        transaction3.begin();
        em.remove(user);
        transaction3.commit();

        // Try to retrieve the user from the database again and assert that it is null
        User deletedUser = em.find(User.class, managedUser.getId());
        em.close();
        assertNull(deletedUser);
    }

    /**
     * Provides a {@link Stream} of {@link Arguments} for the testUserPersistenceWithInvalidData test.
     * <p>
     * Each {@code Arguments} object in the {@code Arguments} represents a set of invalid values
     * for the username, email, password, and balance fields of a User entity.
     * <p>
     * The Stream contains three Arguments objects, each with a different combination of invalid values
     * for the four fields.
     */
    private static Stream<Arguments> usernameEmailPasswordBalanceProvider() {
        return Stream.of(
                Arguments.of("", "testuser@example.com", "password", 100),
                Arguments.of(" ", "testuser@example.com", "password", 100),
                Arguments.of("username", "", "password", 0),
                Arguments.of("username", " ", "password", 0),
                Arguments.of("username", "testuser@example.com", "", -100),
                Arguments.of("username", "testuser@example.com", " ", -100)
        );
    }

    /**
     * Test for persisting a User entity with invalid data.
     * <p>
     * This test uses a parameterized test with a MethodSource to provide a range of invalid values
     * for the username, email, password, and balance fields of the User entity.
     * <p>
     * The test begins a transaction, creates a detached User entity with the provided invalid data,
     * and attempts to persist the entity using the EntityManager.
     * <p>
     * The test expects a ConstraintViolationException to be thrown when the invalid entity is persisted,
     * and rolls back the transaction to undo any changes made during the test.
     */
    @ParameterizedTest
    @MethodSource("usernameEmailPasswordBalanceProvider")
    public void testUserPersistenceWithInvalidData(String username, String email, String password, Integer balance) {
        // Create a detached User object with the specified invalid data
        User detachedUser = new User();
        detachedUser.setUsername(username);
        detachedUser.setEmail(email);
        detachedUser.setPassword(password);
        detachedUser.setBalance(balance);

        // Create an EntityManager
        EntityManager em = Objects.requireNonNull(entityManagerFactory.getObject()).createEntityManager();

        // Start a transaction and attempt to persist the detached User object, so Hibernate checks for constraints
        EntityTransaction transaction1 = em.getTransaction();
        transaction1.begin();
        assertThrows(ConstraintViolationException.class, () -> em.persist(detachedUser));

        // If an exception is thrown, rollback the transaction
        transaction1.rollback();
        em.close();
    }

}

