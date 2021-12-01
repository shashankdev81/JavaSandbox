package concurrency.nonblockingds;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

//Micheal & Scott
public class ConcurrentQueueUsingLL<T> implements Stack<T> {

    private AtomicReference<Node<T>> tail;

    private AtomicReference<Node<T>> head;

    private Queue<T> pushed = new ConcurrentLinkedQueue<T>();

    private Queue<T> popped = new ConcurrentLinkedQueue<T>();

    public ConcurrentQueueUsingLL() {

        //test stack
        Node<T> sentinel = new Node<T>(new AtomicReference<Node<T>>(), null);
        tail = new AtomicReference<Node<T>>(sentinel);
        head = new AtomicReference<Node<T>>(sentinel);
    }

    public boolean tryPush(T val) {
        pushed.add(val);
        Node<T> newNode = new Node<T>(new AtomicReference<Node<T>>(), val);
        Node<T> currTail = tail.get();
        if (currTail.next.compareAndSet(null, newNode)) {
            tail.compareAndSet(currTail, newNode);
            return true;
        } else {
            tail.compareAndSet(currTail, currTail.next.get());
            return false;
        }


    }

    public void push1(T val) {
        pushed.add(val);
        Node<T> newNode = new Node<T>(null, val);
        while (true) {
            Node<T> currTail = tail.get();
            AtomicReference<Node<T>> nextAfterTail = currTail.next;
            if (nextAfterTail.get() == null) {
                if (nextAfterTail.compareAndSet(null, newNode)) {
                    tail.compareAndSet(currTail, newNode);
                    return;
                }
            } else {
                //helper
                tail.compareAndSet(currTail, nextAfterTail.get());
            }

        }

    }

    //simpler version
    public void push(T val) {
        pushed.add(val);
        Node<T> newNode = new Node<T>(new AtomicReference<Node<T>>(), val);
        while (true) {
            Node<T> currTail = tail.get();
            if (currTail.next.compareAndSet(null, newNode)) {
                tail.compareAndSet(currTail, newNode);
                break;
            }

        }

    }

    public T pop() {
        while (true) {
            Node<T> currHead = head.get();
            Node<T> currTail = tail.get();
            if (currHead == currTail) {
                return null;
            } else {
                Node<T> nextAfterHead = currHead.next.get();
                T val = nextAfterHead.value;
                if (head.compareAndSet(currHead, nextAfterHead)) {
                    return val;
                }
            }

        }

    }

    public T pop1() {
        while (true) {
            Node<T> currHead = head.get();
            Node<T> currTail = tail.get();
            if (currHead == currTail) {
                return null;
            } else {
                Node<T> nextAfterHead = currHead.next.get();
                if (nextAfterHead != tail.get()) {
                    T val = nextAfterHead.value;
                    head.compareAndSet(currHead, nextAfterHead.next.get());
                    return val;
                } else {
                    return null;
                }
            }

        }

    }


    public T tryPop() {
        Node<T> currHead = head.get();
        Node<T> currTail = tail.get();
        if (currHead == currTail) {
            return null;
        } else {
            Node<T> nextAfterHead = currHead.next.get();
            T val = nextAfterHead.value;
            if (head.compareAndSet(currHead, nextAfterHead)) {
                return val;
            } else {
                return null;
            }
        }


    }


    private class Node<T> {


        private AtomicReference<Node<T>> next;

        public T value;


        public Node(T value) {
            this.value = value;
        }

        public Node(AtomicReference<Node<T>> next, T value) {
            this.next = next;
            this.value = value;
        }

    }


    public static void main(String[] args) {

        ConcurrentQueueUsingLL<Integer> stack = new ConcurrentQueueUsingLL<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

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