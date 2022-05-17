package com.bobocode.hw5;

import java.util.Objects;

/**
 * A generic node that holds an element of any type and a reference to the next element.
 *
 * @param <T> element type
 */
public class Node<T> {
    T element;
    Node<T> next;

    public Node(T element) {
        this.element = element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(element, node.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element);
    }
}