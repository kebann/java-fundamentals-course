package com.bobocode.hw6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.bobocode.hw6.ListMergeSort.mergeSort;

class ListMergeSortTest {

    @Test
    void testCustomMergeSortSortsProperly() {
        List<Integer> actual = Arrays.asList(2, -3, 99, 22, -100, 99, 104, 70);
        List<Integer> expected = new ArrayList<>(actual);

        mergeSort(actual);
        Collections.sort(expected);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testSortListWithSingleElement() {
        List<Integer> actual = List.of(2);
        mergeSort(actual);

        Assertions.assertEquals(actual, actual);
    }

    @Test
    void testSortEmptyList() {
        List<Integer> actual = List.of(2);
        mergeSort(actual);

        Assertions.assertEquals(actual, actual);
    }

    @Test
    void testExceptionIsThrownForImmutableList() {
        List<Integer> actual = List.of(2, -3, 99, 22, -100, 99, 104, 70);
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> mergeSort(actual));
    }

    @Test
    void testNPEIsThrownForNullableList() {
        Assertions.assertThrows(NullPointerException.class,
                () -> mergeSort(null));
    }
}