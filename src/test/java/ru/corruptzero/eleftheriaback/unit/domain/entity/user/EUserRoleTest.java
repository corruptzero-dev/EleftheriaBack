package ru.corruptzero.eleftheriaback.unit.domain.entity.user;

import org.junit.jupiter.api.Test;
import ru.corruptzero.eleftheriaback.domain.entity.user.EUserRole;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EUserRoleTest {
    @Test
    public void testEUserRoleValuesLength() {
        // Test the correct number of constants
        assertEquals(4, EUserRole.values().length);
    }

    @Test
    public void testEUserRoleConstantNames() {
        // Test the correct constant names
        assertEquals("USER", EUserRole.USER.name());
        assertEquals("ADMIN", EUserRole.ADMIN.name());
        assertEquals("MODERATOR", EUserRole.MODERATOR.name());
        assertEquals("CLIENT", EUserRole.CLIENT.name());
    }

    @Test
    public void testEUserRoleConstantValues() {
        // Test the correct constant values
        assertEquals(EUserRole.USER, EUserRole.valueOf("USER"));
        assertEquals(EUserRole.ADMIN, EUserRole.valueOf("ADMIN"));
        assertEquals(EUserRole.MODERATOR, EUserRole.valueOf("MODERATOR"));
        assertEquals(EUserRole.CLIENT, EUserRole.valueOf("CLIENT"));
    }

    @Test
    public void testEUserRoleToString() {
        // Test the correct behavior of toString()
        assertEquals("USER", EUserRole.USER.toString());
        assertEquals("ADMIN", EUserRole.ADMIN.toString());
        assertEquals("MODERATOR", EUserRole.MODERATOR.toString());
        assertEquals("CLIENT", EUserRole.CLIENT.toString());
    }
}
