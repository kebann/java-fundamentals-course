package com.bobocode.hw4;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HashTableTest {

    HashTable<Integer, String> table = new HashTable<>();

    @Test
    void testPutReturnsNullIfNewAddedNode() {
        String oldVal = table.put(1, "a");

        assertNull(oldVal);
        assertEquals(1, table.size());
    }

    @Test
    void testPutReturnsOldValueIfNodeUpdated() {
        table.put(2, "b");
        String oldVal = table.put(2, "c");

        assertEquals("b", oldVal);
        assertEquals(1, table.size());
    }

    @Test
    void testPutReturnsNullIfNodeWithNullValueUpdated() {
        String oldVal1 = table.put(3, null);
        assertNull(oldVal1);

        String oldVal2 = table.put(3, "d");
        assertNull(oldVal2);

        assertEquals(1, table.size());
    }
}