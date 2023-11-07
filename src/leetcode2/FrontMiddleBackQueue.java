package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

public class FrontMiddleBackQueue {
    private Node front;

    private Node back;

    private Node middle;

    private int ind = -1;

    public FrontMiddleBackQueue() {

    }

    public static void main(String[] args) {
        FrontMiddleBackQueue queue = new FrontMiddleBackQueue();
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

    public void pushFront(int val) {
        ind++;
        if (front == null) {
            front = new Node(val, null, null);
            back = front;
            middle = front;
        } else {
            Node temp = front;
            front = new Node(val, null, front);
            temp.prev = front;
        }
    }

    public void pushMiddle(int val) {
        ind++;
        if (ind > 1) {
            Node temp = new Node(val, middle.prev, middle);
            if (middle.prev != null) {
                middle.prev.next = temp;
            }
            if (middle == back) {
                back.prev = middle;
            }
            middle = temp;
        } else if (ind == 1) {
            Node temp = new Node(val, middle, null);
            middle = temp;
            back = middle;
        } else {
            pushFront(val);
            middle = front;
        }
    }

    public void pushBack(int val) {
        ind++;
        if (back == null) {
            pushFront(val);
            return;
        }
        Node temp = back;
        back = new Node(val, temp, null);
        back.prev.next = back;
        middle = middle.next;
    }

    public int popFront() {
        if (front == null) {
            return -1;
        } else {
            ind--;
            int v = front.val;
            front = front.next;
            front.prev = null;
            middle = middle.next;
            return v;
        }

    }

    public int popMiddle() {
        if (middle == null) {
            return -1;
        } else {
            ind--;
            int v = middle.val;
            if (middle.prev != null) {
                Node temp = middle.next;
                middle.prev.next = middle.next;
                if (middle.next != null) {
                    middle.next.prev = middle.prev;
                }
                if (ind > 0) {
                    middle = temp;
                } else {
                    middle = front;
                    back = front;
                }
            }
            return v;
        }
    }

    public int popBack() {
        if (back == null) {
            return -1;
        } else {
            ind--;
            int v = back.val;
            if (back.prev != null) {
                Node temp = back.prev;
                back.prev.next = null;
                middle = temp;
            } else {
                back = null;
                middle = null;
                front = null;
            }
            return v;
        }
    }

    public class Node {
        Node prev;
        Node next;
        int val;

        public Node(int v, Node p, Node n) {
            val = v;
            prev = p;
            next = n;
        }
    }
}

/**
 * Your FrontMiddleBackQueue object will be instantiated and called as such:
 * FrontMiddleBackQueue obj = new FrontMiddleBackQueue();
 * obj.pushFront(val);
 * obj.pushMiddle(val);
 * obj.pushBack(val);
 * int param_4 = obj.popFront();
 * int param_5 = obj.popMiddle();
 * int param_6 = obj.popBack();
 */