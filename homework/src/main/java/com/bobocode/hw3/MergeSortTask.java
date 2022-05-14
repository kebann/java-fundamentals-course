package com.bobocode.hw3;

import java.util.Arrays;

public class MergeSortTask {
    public static void mergeSort(int[] array) {
        if (array.length > 1) {
            int mid = array.length / 2;
            int[] firstHalf = Arrays.copyOfRange(array, 0, mid);
            int[] secondHalf = Arrays.copyOfRange(array, mid, array.length);

            mergeSort(firstHalf);
            mergeSort(secondHalf);
            merge(firstHalf, secondHalf, array);
        }
    }

    public static void merge(int[] firstHalf, int[] secondHalf, int[] array) {
        int currentIndexFirst = 0;
        int currentIndexSecond = 0;
//      this pointer is for convenience;
        int currentIndexArray = 0;

        while (currentIndexFirst < firstHalf.length && currentIndexSecond < secondHalf.length) {
            if (firstHalf[currentIndexFirst] < secondHalf[currentIndexSecond]) {
                array[currentIndexArray] = firstHalf[currentIndexFirst++];
            } else {
                array[currentIndexArray] = secondHalf[currentIndexSecond++];
            }
            currentIndexArray++;
        }

        System.arraycopy(firstHalf, currentIndexFirst, array, currentIndexArray, firstHalf.length - currentIndexFirst);
        System.arraycopy(secondHalf, currentIndexSecond, array, currentIndexArray, secondHalf.length - currentIndexSecond);
    }
}
