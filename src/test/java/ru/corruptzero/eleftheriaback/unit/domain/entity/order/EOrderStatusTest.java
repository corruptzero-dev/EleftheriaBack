package ru.corruptzero.eleftheriaback.unit.domain.entity.order;

import org.junit.jupiter.api.Test;
import ru.corruptzero.eleftheriaback.domain.entity.order.EOrderStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;


class EOrderStatusTest {

    @Test
    public void testEOrderStatusValuesLength() {
        // Test the correct number of constants
        assertEquals(3, EOrderStatus.values().length);
    }

    @Test
    public void testEOrderStatusConstantNames() {
        // Test the correct constant names
        assertEquals("PENDING", EOrderStatus.PENDING.name());
        assertEquals("ACTIVE", EOrderStatus.ACTIVE.name());
        assertEquals("INACTIVE", EOrderStatus.INACTIVE.name());
    }

    @Test
    public void testEOrderStatusConstantValues() {
        // Test the correct constant values
        assertEquals(EOrderStatus.PENDING, EOrderStatus.valueOf("PENDING"));
        assertEquals(EOrderStatus.ACTIVE, EOrderStatus.valueOf("ACTIVE"));
        assertEquals(EOrderStatus.INACTIVE, EOrderStatus.valueOf("INACTIVE"));
    }

    @Test
    public void testEOrderStatusToString() {
        // Test the correct behavior of toString()
        assertEquals("PENDING", EOrderStatus.PENDING.toString());
        assertEquals("ACTIVE", EOrderStatus.ACTIVE.toString());
        assertEquals("INACTIVE", EOrderStatus.INACTIVE.toString());
    }
}
