package com.bobocode.hw4;

public class ReverseListTask {

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    /**
     * Accepts a linked list head, reverses all elements and returns a new head (the last element).
     * PLEASE NOTE that it should not create new nodes, only change the next references of the existing ones.
     * E.g. you have a like "head:5 -> 7 -> 1 -> 4" should this method will return "head:4 -> 1 -> 7 -> 5"
     *
     * @param head the first element of the list
     * @param <T>  element type
     * @return new head
     */
    public static <T> Node<T> reverseLinkedList(Node<T> head) {
        Node<T> prev = null;
        while (head != null) {
            Node<T> next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    public static <T> void printList(Node<T> head) {
        while (head != null) {
            System.out.print(head.value);
            if (head.next != null) {
                System.out.print(" -> ");
            }
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node<Integer> head = new Node<>(0);
        Node<Integer> node1 = new Node<>(1);
        Node<Integer> node2 = new Node<>(2);
        Node<Integer> node3 = new Node<>(3);
        Node<Integer> node4 = new Node<>(4);

        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        printList(head);
        Node<Integer> newHead = reverseLinkedList(head);
        printList(newHead);
    }
}
