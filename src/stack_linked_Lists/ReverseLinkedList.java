package stack_linked_Lists;

public class ReverseLinkedList {

    private Node head;

    public ReverseLinkedList() {
        Node tail = new Node(5, null);
        head = new Node(1, new Node(2, new Node(3, new Node(4, tail))));

    }

    private class Node {
        private int value;
        private Node next;

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    public void printReverse() {
        System.out.println("LL in reverse=" + reverse(head));
    }

    private String reverse(Node curr) {
        if (curr.next == null) {
            return String.valueOf(curr.value);
        }
        return reverse(curr.next) + "->" + curr.value;
    }

    public static void main(String[] args) {
        ReverseLinkedList ll = new ReverseLinkedList();
        ll.printReverse();
    }
}
