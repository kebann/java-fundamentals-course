package com.bobocode.hw9;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeSortWithForkJoinTest {

    @Test
    void testArrayIsSorted() {
        Integer[] nums = new Random().ints(1_000_000).boxed().toArray(Integer[]::new);
        MergeSortTask<Integer> task = new MergeSortTask<>(nums);

        ForkJoinPool.commonPool().invoke(task);

        assertThat(nums).isSorted();
    }
}
