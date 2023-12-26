package leetcode2;

public class MRUQueue {


    private int val;

    private Node head;

    private Node tail;

    int size = 0;

    public static void main(String[] args) {
        MRUQueue queue = new MRUQueue(8);
        System.out.println(queue.fetch(3));
        System.out.println(queue.fetch(5));
        System.out.println(queue.fetch(2));
        System.out.println(queue.fetch(8));

    }

    public MRUQueue(int n) {
        size = n;
        head = new Node(null, null, 1);
        if (n == 1) {
            tail = head;
            return;
        }
        Node curr = head;
        for (int i = 2; i <= n; i++) {
            Node node = new Node(curr, null, i);
            curr.next = node;
            curr = curr.next;
        }
        tail = curr;
    }

    public int fetch(int k) {
        int count = k;
        if (k == size) {
            return tail.val;
        }
        Node node = head;
        while (node != null && count > 1) {
            node = node.next;
            count--;
        }
        int res = node.val;
        if (k == 1) {
            head = head.next;
            head.prev = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.val + ",");
            curr = curr.next;
        }
        return res;

    }

    private class Node {

        Node prev;
        Node next;
        int val;

        public Node(Node p, Node n, int v) {
            this.prev = p;
            this.next = n;
            this.val = v;
        }
    }

}

/**
 * Your MRUQueue object will be instantiated and called as such: MRUQueue obj = new MRUQueue(n); int
 * param_1 = obj.fetch(k);
 */