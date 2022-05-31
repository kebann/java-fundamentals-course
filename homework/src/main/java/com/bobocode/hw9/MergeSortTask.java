package com.bobocode.hw9;

import com.bobocode.hw6.NonNull;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask<T extends Comparable<T>> extends RecursiveAction {

    private final T[] array;

    public MergeSortTask(@NonNull T[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        int length = array.length;

        if (length > 1) {
            int mid = length / 2;
            T[] leftArray = Arrays.copyOfRange(array, 0, mid);
            T[] rightArray = Arrays.copyOfRange(array, mid, length);

            MergeSortTask<T> leftTask = new MergeSortTask<>(leftArray);
            MergeSortTask<T> rightTask = new MergeSortTask<>(rightArray);

            leftTask.fork();
//          reusing the current thread and calling compute method recursively instead of spawning a new thread
//          and other threads that can be potentially spawned from it.
            rightTask.compute();

            leftTask.join();

            merge(leftArray, rightArray, array);
        }
    }

    private void merge(T[] left, T[] right, T[] array) {
        int currentIndexFirst = 0;
        int currentIndexSecond = 0;
        int currentIndexArray = 0;

        while (currentIndexFirst < left.length && currentIndexSecond < right.length) {
            if (left[currentIndexFirst].compareTo(right[currentIndexSecond]) < 0) {
                array[currentIndexArray] = left[currentIndexFirst++];
            } else {
                array[currentIndexArray] = right[currentIndexSecond++];
            }
            currentIndexArray++;
        }

        System.arraycopy(left, currentIndexFirst, array, currentIndexArray, left.length - currentIndexFirst);
        System.arraycopy(right, currentIndexSecond, array, currentIndexArray, right.length - currentIndexSecond);
    }
}
