package com.bobocode.hw2;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class HeterogeneousMaxHolder {
    private final Map<Class<?>, Object> maxHolder = new HashMap<>();

    public <T extends Comparable<T>> void put(@NonNull Class<T> key, @NonNull T value) {
        T currentMax = getMax(key);
        if (currentMax == null || currentMax.compareTo(value) < 0) {
            maxHolder.put(key, value);
        }
    }

    public <T> T getMax(Class<T> key) {
        return key.cast(maxHolder.get(key));
    }
}
