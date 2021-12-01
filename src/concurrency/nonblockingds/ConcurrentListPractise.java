package concurrency.nonblockingds;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class ConcurrentListPractise<T> {

    private AtomicMarkableReference<Node<T>> head;

    private AtomicMarkableReference<Node<T>> tail;

    private AtomicInteger size;

    public ConcurrentListPractise() {
        head = new AtomicMarkableReference<Node<T>>(null, false);
        tail = new AtomicMarkableReference<Node<T>>(null, false);
        size = new AtomicInteger(0);
    }

    @Override
    public String toString() {
        boolean[] flag = new boolean[1];
        Node<T> headNode = head.get(flag);
        return headNode == null || flag[0] ? "" : headNode.toString();
    }

    private class Node<T> {
        private T value;
        private AtomicMarkableReference<Node<T>> next;

        public Node(T value) {
            this.value = value;
            next = new AtomicMarkableReference<Node<T>>(null, false);
        }

        @Override
        public String toString() {
            boolean[] flag = new boolean[1];
            Node<T> nextNode = next.get(flag);
            String value = flag[0] ? "(Deleted)" + this.value : this.value.toString();
            return value + "->" + ((nextNode == null) ? "null" : nextNode.toString());
        }
    }

    private class Pair {
        private Node<T> prev;
        private Node<T> curr;

        public Pair(Node<T> prev, Node<T> curr) {
            this.prev = prev;
            this.curr = curr;
        }

        @Override
        public String toString() {
            return prev.value + "->" + (curr == null ? "null" : curr.value);
        }
    }

    public void add(T value) {
        boolean[] flag = new boolean[1];
        Node<T> headNode = head.get(flag);
        Node<T> tailNode = tail.get(flag);
        Node<T> newNode = new Node<T>(value);
        while (headNode == null && !head.compareAndSet(null, newNode, false, false)) {
            headNode = head.get(flag);
        }
        if (tail.getReference() == null) {
            tail.compareAndSet(null, newNode, false, false);
        } else {
            while (!tailNode.next.compareAndSet(null, newNode, false, false)) {
                tailNode = tail.get(flag);
            }
        }
        tail.compareAndSet(tail.getReference(), newNode, false, false);
        size.incrementAndGet();
    }

    public T remove(T value) {
        T result = null;
        while (true) {
            Pair deletable = find(value);
            if (deletable == null) {
                return null;
            }
            //only one node in set and its the one we want to delete
            if (deletable.curr == deletable.prev) {
                Node<T> headNode = head.getReference();
                Node<T> succ = headNode.next.getReference();
                result = headNode.value;
                //mark head node fo deletion
                if (!head.compareAndSet(headNode, headNode.next.getReference(), false, false)) {
                    result = null;
                    break;
                }
            }
            boolean[] flag = new boolean[1];
            Node<T> succ = deletable.curr.next.get(flag);
            result = deletable.curr.value;
            if (!deletable.curr.next.compareAndSet(succ, succ, false, true)) {
                result = null;
                break;
            }
            if (deletable.prev.next.compareAndSet(deletable.curr, succ, false, false)) {
                break;
            }
            //else we have lost prev reference; so we start all over again

        }
        return result;
    }

    private Pair find(T value) {
        while (true) {
            boolean[] flag = new boolean[1];
            Node<T> prev = head.get(flag);
            //empty set
            if (prev == null) {
                return null;
            }
            Node<T> curr = prev.next.getReference();
            if (prev.value == value) {
                return new Pair(prev, prev);
            } else if (curr == null) {
                return null;
            }
            while (true) {
                Node<T> succ = curr.next.get(flag);
                /* if current node is being deleted then we don't know if succ is null or still holding
                reference to the next node in chain.
                 */
                while (flag[0]) {
                    /* We try to point prev's next to succ
                        if operation successful means
                            1. prev is not being deleted and succ is the next node we are looking for
                        if operation unsuccessful means
                            1. prev's next can be pointing to new node other than curr (Good news!!!)
                            2. prev itself is being deleted (Bad news !!!)
                            Since we can't distinguish 1 from 2 we break and start all over again
                     *
                     */
                    if (!prev.next.compareAndSet(curr, succ, false, false)) {
                        break;
                    } else {
                        // reset curr to point to next of prev
                        curr = prev.next.get(flag);
                    }
                }
                prev = curr;
                curr = succ;
                if (curr == null) {
                    return null;
                }
                if (curr.value == value) {
                    return new Pair(prev, curr);
                }
            }

        }
    }

    public static void main(String[] args) {
        ConcurrentListPractise<Integer> concurrentSet = new ConcurrentListPractise<Integer>();
        concurrentSet.add(10);
        concurrentSet.add(4);
        concurrentSet.add(16);
        concurrentSet.add(6);
        concurrentSet.add(18);
        System.out.println(concurrentSet.toString());
        System.out.println(concurrentSet.find(16));
        System.out.println(concurrentSet.find(10));
        System.out.println(concurrentSet.find(18));
        System.out.println(concurrentSet.find(57));

        concurrentSet.remove(10);
        System.out.println(concurrentSet.toString());

    }

    private int size() {
        return size.get();
    }
}
