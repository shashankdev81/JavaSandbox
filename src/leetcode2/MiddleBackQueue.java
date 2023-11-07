package leetcode2;

public class MiddleBackQueue {

    public static void main(String[] args) {
        MiddleBackQueue queue = new MiddleBackQueue();
        queue.pushFront(1);
        queue.pushBack(2);
        queue.pushMiddle(3);
        queue.pushMiddle(4);
        System.out.println(queue.popFront());
        System.out.println(queue.popMiddle());
        System.out.println(queue.popMiddle());
        System.out.println(queue.popBack());
        System.out.println(queue.popFront());
    }

    Node sentinelHead;

    Node sentinelTail;

    Node mid;

    int leftNodes = 0;

    int rightNodes = 0;

    public MiddleBackQueue() {
        sentinelTail = new Node();
        sentinelHead = new Node(null, sentinelTail, 0);
        sentinelTail.prev = sentinelHead;
        mid = sentinelTail;
    }

    public void pushFront(int val) {
        Node next = sentinelHead.next;
        Node temp = new Node(sentinelHead, next, val);
        next.prev = temp;
        sentinelHead.next = temp;
        leftNodes++;
        balanceMid();
    }

    public int popFront() {
        Node front = sentinelHead.next;
        if (front == null) {
            return -1;
        }
        sentinelHead.next = front.next;
        gcTemp(front);
        leftNodes--;
        balanceMid();
        return front.val;
    }

    public int popBack() {
        Node back = sentinelTail.prev;
        if (back == sentinelHead) {
            return -1;
        }
        sentinelTail.prev = back.prev;
        back.prev.next = sentinelTail;
        gcTemp(back);
        rightNodes--;
        balanceMid();
        return back.val;
    }

    public int popMiddle() {
        Node temp = null;
        if (mid == sentinelTail && mid.prev == sentinelHead) {
            return -1;
        }
        if (mid == sentinelTail) {
            temp = mid.prev;
            gcTemp(temp);
            leftNodes--;
        } else {
            temp = mid;
            Node prev = mid.prev;
            Node next = mid.next;
            prev.next = mid.next;
            next.prev = prev;
            mid = next;
            gcTemp(temp);
            rightNodes--;
        }
        balanceMid();
        return temp.val;

    }

    private void gcTemp(Node temp) {
        temp.prev = null;
        temp.next = null;
    }

    private void balanceMid() {
        if (leftNodes - rightNodes > 1) {
            mid = mid.prev;
            leftNodes--;
            rightNodes++;
        } else if (rightNodes - leftNodes > 1) {
            mid = mid.next;
            rightNodes--;
            leftNodes--;
        }
    }

    public void pushBack(int val) {
        Node prev = sentinelTail.prev;
        Node temp = new Node(prev, sentinelTail, val);
        prev.next = temp;
        sentinelTail.prev = temp;
        if (leftNodes + rightNodes > 1) {
            rightNodes++;
        } else {
            leftNodes++;
        }
        balanceMid();
    }

    public void pushMiddle(int val) {
        Node prev = mid.prev;
        Node next = mid.next;
        Node temp = new Node(prev, mid, val);
        prev.next = temp;
        mid.prev = temp;
        leftNodes++;
        balanceMid();
    }

    public class Node {
        Node prev;
        Node next;
        int val;

        public Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }

        public Node() {
        }
    }
}
