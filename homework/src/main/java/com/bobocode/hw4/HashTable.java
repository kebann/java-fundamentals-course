package com.bobocode.hw4;

import lombok.NonNull;

import java.util.Objects;

/**
 * A simple implementation of the Hash Table that allows storing a generic key-value pair. The table itself is based
 * on the array of {@link Node} objects.
 * <p>
 * An initial array capacity is 16.
 * <p>
 * Every time a number of elements is equal to the array size that tables gets resized
 * (it gets replaced with a new array that it twice bigger than before). E.g. resize operation will replace array
 * of size 16 with a new array of size 32. PLEASE NOTE that all elements should be reinserted to the new table to make
 * sure that they are still accessible from the outside by the same key.
 *
 * @param <K> key type parameter
 * @param <V> value type parameter
 */
public class HashTable<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    @SuppressWarnings("unchecked")
    private Node<K, V>[] table = new Node[DEFAULT_CAPACITY];
    private int size;

    /**
     * Puts a new element to the table by its key. If there is an existing element by such key then it gets replaced
     * with a new one, and the old value is returned from the method. If there is no such key then it gets added and
     * null value is returned.
     *
     * @param key   element key
     * @param value element value
     * @return old value or null
     */
    public V put(@NonNull K key, V value) {
        int index = indexFor(key);
        Node<K, V> current = table[index];

//        insert a new node if no entry with such a key exists
        if (current == null) {
            table[index] = new Node<>(key, value);
        } else {
            Node<K, V> prev = null;
            while (current != null) {
//                update the value for the existing node
                if (current.key.equals(key) && !Objects.equals(current.value, value)) {
                    V oldValue = current.value;
                    current.value = value;
                    return oldValue;
                }
                prev = current;
                current = current.next;
            }
//            append a new node to the existing list of node in the bucket
            prev.next = new Node<>(key, value);
        }
        size++;

        if (size == table.length) {
            resize();
        }

        return null;
    }

    private int indexFor(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTable = table;
        table = new Node[table.length * 2];
        for (Node<K, V> current : oldTable) {
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public int size() {
        return size;
    }

    /**
     * Prints a content of the underlying table (array) according to the following format:
     * 0: key1:value1 -> key2:value2
     * 1:
     * 2: key3:value3
     * ...
     */
    public void printTable() {
        for (int index = 0; index < table.length; index++) {
            System.out.printf("%s: ", index);

            Node<K, V> current = table[index];
            while (current != null) {
                System.out.printf("%s:%s", current.key, current.value);
                if (current.next != null) {
                    System.out.print(" -> ");
                }
                current = current.next;
            }
            System.out.println();
        }
    }
}