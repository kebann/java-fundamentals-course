package com.bobocode.hw5;


import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

public class ReverseLinkedListTask {

    public static void main(String[] args) {
        var head = createLinkedList(4, 3, 9, 1);
        printReversed(head, ReverseLinkedListTask::printReversedRecursively);
        System.out.println();
        printReversed(head, ReverseLinkedListTask::printReversedUsingStack);
    }

    /**
     * Creates a list of linked {@link Node} objects based on the given array of elements and returns a head of the list.
     *
     * @param elements an array of elements that should be added to the list
     * @param <T>      elements type
     * @return head of the list
     */
    public static <T> Node<T> createLinkedList(T... elements) {
        if (elements == null || elements.length == 0) {
            return null;
        }

        var head = new Node<>(elements[0]);
        var current = head;
        for (int i = 1; i < elements.length; i++) {
            current.next = new Node<>(elements[i]);
            current = current.next;
        }

        return head;
    }

    /**
     * Prints a list in a reserved order using a recursion technique. Please note that it should not change the list,
     * just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedRecursively(Node<T> head) {
        if (head != null) {
            printReversedRecursively(head.next);
            System.out.printf("%s -> ", head.element);
        }
    }

    public static <T> void printReversed(Node<T> head, Consumer<Node<T>> printer) {
        if (head != null) {
            printer.accept(head.next);
            System.out.print(head.element);
        }
    }

    /**
     * Prints a list in a reserved order using a {@link java.util.Stack} instance. Please note that it should not change
     * the list, just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedUsingStack(Node<T> head) {
        Deque<T> stack = new LinkedList<>();

        while (head != null) {
            stack.push(head.element);
            head = head.next;
        }

        while (!stack.isEmpty()) {
            System.out.printf("%s -> ", stack.pop());
        }
    }
}