package concurrency.nonblockingds;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentQueuePrac<T> implements Queue<T> {

    private AtomicReference<Node<T>> head;

    private AtomicReference<Node<T>> tail;

    public ConcurrentQueuePrac() {
        Node<T> sentinel = new Node<T>(null, null);
        head = new AtomicReference<Node<T>>(sentinel);
        tail = new AtomicReference<Node<T>>(sentinel);
    }

    private class Node<T> {
        private T value;
        private AtomicReference<Node<T>> next;

        public Node(T value, AtomicReference<Node<T>> next) {
            this.value = value;
            this.next = next == null ? new AtomicReference<>() : next;
        }
    }

    @Override
    public void put(T val) {
        Node<T> currTailNode = null;
        Node<T> newNode = new Node<T>(val, null);
        while (true) {
            currTailNode = tail.get();
            if (currTailNode.next.compareAndSet(null, newNode)) {
                tail.compareAndSet(currTailNode, newNode);
                break;
            } else {
                tail.compareAndSet(currTailNode, currTailNode.next.get());
            }
        }
    }

    @Override
    public boolean tryPut(T val) {
        return false;
    }

    @Override
    public T dequeue() {
        Node<T> headNode = null;
        while (true) {
            headNode = head.get().next.get();
            if (head.compareAndSet(head.get(), headNode)) {
                break;
            }
        }
        return headNode == null ? null : headNode.value;
    }

    @Override
    public T tryDequeue() {
        return null;
    }

    public static void main(String[] args) {
        ConcurrentQueuePrac<Integer> queue = new ConcurrentQueuePrac<Integer>();
        queue.put(3);
        queue.put(5);
        queue.put(2);
        queue.put(4);
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

    }
}
