package ru.corruptzero.eleftheriaback.unit.domain.entity.withdraw;


import org.junit.jupiter.api.Test;
import ru.corruptzero.eleftheriaback.domain.entity.withdraw.EWithdrawStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EWithdrawStatusTest {
    @Test
    public void testEWithdrawStatusValuesLength() {
        // Test the correct number of constants
        assertEquals(3, EWithdrawStatus.values().length);
    }

    @Test
    public void testEWithdrawStatusConstantNames() {
        // Test the correct constant names
        assertEquals("PENDING", EWithdrawStatus.PENDING.name());
        assertEquals("COMPLETED", EWithdrawStatus.COMPLETED.name());
        assertEquals("CANCELLED", EWithdrawStatus.CANCELLED.name());
    }

    @Test
    public void testEWithdrawStatusConstantValues() {
        // Test the correct constant values
        assertEquals(EWithdrawStatus.PENDING, EWithdrawStatus.valueOf("PENDING"));
        assertEquals(EWithdrawStatus.COMPLETED, EWithdrawStatus.valueOf("COMPLETED"));
        assertEquals(EWithdrawStatus.CANCELLED, EWithdrawStatus.valueOf("CANCELLED"));
    }

    @Test
    public void testEWithdrawStatusToString() {
        // Test the correct behavior of toString()
        assertEquals("PENDING", EWithdrawStatus.PENDING.toString());
        assertEquals("COMPLETED", EWithdrawStatus.COMPLETED.toString());
        assertEquals("CANCELLED", EWithdrawStatus.CANCELLED.toString());
    }
}
