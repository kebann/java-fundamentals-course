package com.bobocode.cs;

import lombok.NonNull;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 * <p><p>
 * <strong>TODO: to get the most out of your learning, <a href="https://www.bobocode.com/learn">visit our website</a></strong>
 * <p>
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private static class Node<T> {
        T element;
        Node<T> left;
        Node<T> right;

        Node(T element) {
            this.element = element;
        }
    }

    private Node<T> root;
    private int size = 0;

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree<T> tree = new RecursiveBinarySearchTree<>();
        Stream.of(elements).forEach(tree::insert);
        return tree;
    }

    @Override
    public boolean insert(@NonNull T element) {
        if (root == null) {
            root = new Node<>(element);
            size++;
            return true;
        }

        boolean isInserted = insertIntoSubtree(root, element);
        if (isInserted) {
            size++;
        }

        return isInserted;
    }

    private boolean insertIntoSubtree(Node<T> root, T element) {
        if (root.element.compareTo(element) > 0) {
            return insertIntoLeftSubtree(root, element);
        } else if (root.element.compareTo(element) < 0) {
            return insertIntoRightSubtree(root, element);
        } else {
            return false;
        }
    }

    private boolean insertIntoRightSubtree(Node<T> root, T element) {
        if (root.right == null) {
            root.right = new Node<>(element);
            return true;
        } else {
            return insertIntoSubtree(root.right, element);
        }
    }

    private boolean insertIntoLeftSubtree(Node<T> root, T element) {
        if (root.left == null) {
            root.left = new Node<>(element);
            return true;
        } else {
            return insertIntoSubtree(root.left, element);
        }
    }

    @Override
    public boolean contains(@NonNull T element) {
        return findNode(root, element) != null;
    }

    private Node<T> findNode(Node<T> root, T element) {
        if (root == null) {
            return null;
        } else if (root.element.compareTo(element) > 0) {
            return findNode(root.left, element);
        } else if (root.element.compareTo(element) < 0) {
            return findNode(root.right, element);
        } else {
            return root;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        return root == null ? 0 : depth(root) - 1;
    }

    private int depth(Node<T> root) {
        return root == null ? 0 : Math.max(depth(root.left), depth(root.right)) + 1;
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversal(root, consumer);
    }

    private void inOrderTraversal(Node<T> root, Consumer<T> consumer) {
        if (root != null) {
            inOrderTraversal(root.left, consumer);
            consumer.accept(root.element);
            inOrderTraversal(root.right, consumer);
        }
    }

    public void inOrderIterativeTraversal(Consumer<T> consumer) {
        Deque<Node<T>> stack = new ArrayDeque<>();
        Node<T> current = root;

        while (!stack.isEmpty() || current != null) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            consumer.accept(current.element);
            current = current.right;
        }
    }
}
