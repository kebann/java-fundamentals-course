package com.bobocode.hw5;

import com.google.common.annotations.VisibleForTesting;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * By default, it compares only accessible fields, but this can be configured via a constructor property. If no field is
 * available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 */
public class RandomFieldComparator<T> implements Comparator<T> {

    private final Field randomField;
    private final Class<T> targetType;

    public RandomFieldComparator(Class<T> targetType) {
        this(targetType, true);
    }

    /**
     * A constructor that accepts a class and a property indicating which fields can be used for comparison. If property
     * value is true, then only public fields or fields with public getters can be used.
     *
     * @param targetType                  a type of objects that may be compared
     * @param compareOnlyAccessibleFields config property indicating if only publicly accessible fields can be used
     */
    public RandomFieldComparator(@NonNull Class<T> targetType, boolean compareOnlyAccessibleFields) {
        this.randomField = extractRandomField(targetType, compareOnlyAccessibleFields);
        this.targetType = targetType;
    }

    @SneakyThrows
    private Field extractRandomField(Class<T> targetType, boolean compareOnlyAccessibleFields) {
        T instance = targetType.getDeclaredConstructor().newInstance();

        var fields = Arrays.stream(targetType.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> f.canAccess(instance) == compareOnlyAccessibleFields)
                .filter(f -> f.getType().isPrimitive() || Comparable.class.isAssignableFrom(f.getType()))
                .toList();

        if (fields.isEmpty()) {
            throw new IllegalArgumentException("No field is available for comparison");
        }

        Field randomField = getRandomField(fields);
        randomField.setAccessible(true);

        return randomField;
    }

    public static Field getRandomField(List<Field> fields) {
        return fields.get(ThreadLocalRandom.current().nextInt(fields.size()));
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value grater than a non-null value (nulls last).
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public int compare(T o1, T o2) {
        Comparable val1 = (Comparable) randomField.get(o1);
        Comparable val2 = (Comparable) randomField.get(o2);

        if (val1 == null) {
            return (val2 == null) ? 0 : -1;
        } else if (val2 == null) {
            return 1;
        } else {
            return val1.compareTo(val2);
        }
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class '%s' is comparing '%s'",
                targetType.getName(), randomField.getName());
    }

    @VisibleForTesting
    Field getRandomField() {
        return randomField;
    }

}