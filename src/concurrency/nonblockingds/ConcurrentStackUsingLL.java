package concurrency.nonblockingds;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStackUsingLL<T> implements Stack<T> {

    private AtomicReference<Node<T>> head;

    private Queue<T> pushed = new ConcurrentLinkedQueue<T>();

    private Queue<T> popped = new ConcurrentLinkedQueue<T>();

    public ConcurrentStackUsingLL() {

        //test stack
        head = new AtomicReference<>();
    }

    public boolean tryPush(T val) {
        Node<T> currHead = head.get();
        return head.compareAndSet(currHead, new Node<T>(currHead, val));
    }

    public void push(T val) {
        pushed.add(val);
        Node<T> currHead = head.get();
        Node<T> newNode = new Node<T>(currHead, val);
        while (!head.compareAndSet(currHead, newNode)) {
            currHead = head.get(); //currHead =5
            newNode.next = currHead; //newNode 7->6->5
        }
    }


    public T pop() {
        boolean isPopped = false;
        Node<T> top = null;
        while (true) {
            top = head.get();
            if (top == null) {
                return null;
            }
            if (head.compareAndSet(top, top.next)) {
                break;
            }
        }
        popped.add(top == null ? null : top.value);
        return top == null ? null : top.value;
    }


    public T tryPop() {
        boolean isPopped = false;
        Node<T> top = head.get();
        Node<T> next = top.next;
        if (top == null) {
            return null;
        }
        if (head.compareAndSet(top, next)) {
            return top.value;
        } else {
            return null;
        }
    }

    private class Node<T> {


        private Node<T> next;

        public T value;


        public Node(T value) {
            this.value = value;
        }

        public Node(Node<T> next, T value) {
            this.next = next;
            this.value = value;
        }

    }

    private static ConcurrentStackUsingLL<Integer> stack = new ConcurrentStackUsingLL<Integer>();

    public static void main(String[] args) {

        Pusher pusher1 = new Pusher(stack);
        Pusher pusher2 = new Pusher(stack);
        Popper popper1 = new Popper(stack);
        Popper popper2 = new Popper(stack);

        new Thread(pusher1).start();
        new Thread(pusher2).start();
        new Thread(popper1).start();
        new Thread(popper2).start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pusher1.stop();
        pusher2.stop();
        popper1.stop();
        popper2.stop();

        System.out.println("Done");
    }


}