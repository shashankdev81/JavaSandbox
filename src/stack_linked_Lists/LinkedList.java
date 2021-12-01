package stack_linked_Lists;

import java.util.Objects;

public class LinkedList<T> {

    private Node<T> head;

    private Node<T> curr;

    private LinkedList(Node<T> head) {
        this.head = head;
    }

    private class Node<T> {

        private T value;
        private Node<T> next;

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        public Node(T value) {
            this.value = value;
        }
    }

    public LinkedList<T> getReversedList() {
        Node<T> clonedHead = clone(head);
        return new LinkedList<T>(reverse(clonedHead).head);
    }

    private Node<T> clone(Node<T> head) {
        Node<T> cloned = null;
        Node<T> tail = null;
        Node<T> tailMinusOne = null;

        Node<T> curr = head;
        while (curr != null) {
            if (cloned == null) {
                cloned = new Node<T>(curr.value);
                tail = curr;
                tailMinusOne = cloned;
            } else {
                curr = curr.next;
                if (curr != null) {
                    tailMinusOne.next = new Node<T>(curr.value);
                    tailMinusOne = tailMinusOne.next;
                }
            }
        }

        return cloned;
    }

    private Pair<T> reverse(Node<T> curr) {

        if (curr.next == null) {
            return new Pair<T>(curr, curr);
        }
        Pair<T> newTail = reverse(curr.next);
        newTail.curr.next = curr;
        curr.next = null;
        return new Pair<T>(newTail.head, curr);
    }

    private class Pair<T> {
        private Node<T> head;
        private Node<T> curr;

        public Pair(Node<T> head, Node<T> curr) {
            this.head = head;
            this.curr = curr;
        }
    }

    public void print() {
        Node<T> curr = head;
        StringBuilder builder = new StringBuilder();
        while (curr != null) {
            builder.append(curr.value).append("->");
            curr = curr.next;
        }
        builder.append("NULL");
        System.out.println("Linked List=" + builder.toString());
    }

    public static void main(String[] args) {

        LinkedList<Integer> linkedList = new LinkedList<Integer>(null);
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.print();
        LinkedList<Integer> reversed = linkedList.getReversedList();
        reversed.print();
        reversed.getReversedList().print();
    }

    private void add(T val) {
        if (head == null) {
            head = new Node<T>(val);
            curr = head;
        } else {
            curr.next = new Node<T>(val);
            curr = curr.next;
        }
    }
}


