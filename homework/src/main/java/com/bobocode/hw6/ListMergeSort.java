package com.bobocode.hw6;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement a generic method that accepts and sorts a List of Comparable
 * elements using a recursive Merge Sort algorithm.
 */
public class ListMergeSort {

    public static <T extends Comparable<T>> void mergeSort(@NonNull List<T> elements) {
        if (elements.size() > 1) {
            int mid = elements.size() / 2;
            List<T> firstHalf = new ArrayList<>(elements.subList(0, mid));
            List<T> secondHalf = new ArrayList<>(elements.subList(mid, elements.size()));

            mergeSort(firstHalf);
            mergeSort(secondHalf);
            merge(firstHalf, secondHalf, elements);
        }
    }

    private static <T extends Comparable<T>> void merge(List<T> firstHalf, List<T> secondHalf, List<T> elements) {
        int currentIndexFirst = 0;
        int currentIndexSecond = 0;
        int currentIndexArray = 0;

        while (currentIndexFirst < firstHalf.size() && currentIndexSecond < secondHalf.size()) {
            if (firstHalf.get(currentIndexFirst).compareTo(secondHalf.get(currentIndexSecond)) < 0) {
                elements.set(currentIndexArray, firstHalf.get(currentIndexFirst));
                currentIndexFirst++;
            } else {
                elements.set(currentIndexArray, secondHalf.get(currentIndexSecond));
                currentIndexSecond++;
            }
            currentIndexArray++;
        }

        for (int i = currentIndexFirst; i < firstHalf.size(); i++) {
            elements.set(currentIndexArray++, firstHalf.get(i));
        }

        for (int j = currentIndexSecond; j < secondHalf.size(); j++) {
            elements.set(currentIndexArray++, secondHalf.get(j));
        }
    }
}
