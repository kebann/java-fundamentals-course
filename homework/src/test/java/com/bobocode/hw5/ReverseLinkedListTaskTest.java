package com.bobocode.hw5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReverseLinkedListTaskTest {

    @Test
    void shouldCreateCorrectLinkedList() {
        int[] numbers = {4, 3, 9, 1};
        var head = ReverseLinkedListTask.createLinkedList(4, 3, 9, 1);

        for (int expected : numbers) {
            Assertions.assertEquals(expected, head.element);
            head = head.next;
        }
    }

    @Test
    void shouldReturnNullHeadIfNullArrayProvided() {
        var head = ReverseLinkedListTask.createLinkedList(null);
        Assertions.assertNull(head);
    }

    @Test
    void shouldReturnNullHeadIfEmptyArrayProvided() {
        var head = ReverseLinkedListTask.createLinkedList();
        Assertions.assertNull(head);
    }
}