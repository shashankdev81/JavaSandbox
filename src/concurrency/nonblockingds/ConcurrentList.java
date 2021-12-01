package concurrency.nonblockingds;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class ConcurrentList<T> {

    AtomicMarkableReference<Node<T>> head = new AtomicMarkableReference<Node<T>>(null, false);

    AtomicMarkableReference<Node<T>> tail = new AtomicMarkableReference<Node<T>>(null, false);


    public void put(T val) {
        Node<T> node = new Node<T>(val);
        boolean[] headMarked = new boolean[1];
        while (true) {
            //empty queue
            if (head.get(headMarked) == null) {
                if (head.compareAndSet(null, node, false, true)) {
                    tail.compareAndSet(null, node, false, false);
                    head.attemptMark(node, false);
                    break;
                }
            } else if (!headMarked[0]) {
                boolean[] currMarked = new boolean[1];
                Node<T> currNode = tail.getReference();
                AtomicMarkableReference<Node<T>> afterTail = tail.getReference().next;
                if (!currMarked[0] && afterTail.compareAndSet(null, node, false, false)) {
                    tail.compareAndSet(currNode, node, false, false);
                    break;
                }
            }
        }

    }

    public boolean remove(T val) {
        while (true) {
            Window window = find(val);
            if (window == null) {
                return false;
            }
            AtomicMarkableReference<Node<T>> succ = window.curr.next;
            if (succ.attemptMark(succ.getReference(), true)) {
                //head node
                if (window.prev.value == window.curr.value) {
                    if (head.compareAndSet(window.prev, succ.getReference(), false, true)) {
                        tail.set(head.getReference(), false);
                        head.attemptMark(head.getReference(), false);
                    }
                    return true;
                }
                if (window.prev.next.compareAndSet(window.curr, succ.getReference(), false, false)) {
                    return true;
                }
                //else either prev node is being deleted or some thread is doing same delete
            }
            //else someone trying to delete curr- helper class?
        }
    }

    public Window find(T val) {
        while (true) {
            boolean[] prevMarked = new boolean[1];
            boolean[] currMarked = new boolean[1];
            //at least head
            Node<T> prev = head.get(prevMarked);
            Node<T> curr = head.get(currMarked);
            if (prev == null || curr == null) {
                return null;
            }
            boolean isFirst = true;
            //start all over again
            while (curr.value != val) {
                curr = curr.next.get(currMarked);
                if (!isFirst) {
                    prev = prev.next.get(prevMarked);
                } else {
                    isFirst = !isFirst;
                }
                //node not found
                if (curr == null) {
                    return null;
                }
            }
            //if both nodes are ok then step out
            if (!prevMarked[0] && !currMarked[0]) {
                return new Window(prev, curr);
            } else if (currMarked[0]) {
                return null;
            }
        }
    }

    public class Window {
        private Node<T> prev;
        private Node<T> curr;

        public Window(Node<T> prev, Node<T> curr) {
            this.prev = prev;
            this.curr = curr;
        }


        @Override
        public String toString() {
            return "Window{" +
                    "prev=" + prev.value +
                    ", curr=" + curr.value +
                    '}';
        }
    }

    @Override
    public String toString() {
        boolean[] marked = new boolean[]{false};
        Node<T> node = head.get(marked);
        return node == null ? "Empty" : node.toString();
    }


    private class Node<T> {


        private AtomicMarkableReference<Node<T>> next;

        private T value;


        public Node(T value) {
            this.value = value;
            this.next = new AtomicMarkableReference<Node<T>>(null, false);
        }

        public Node(T value, boolean isSentinel) {
            this.value = value;
        }


        @Override
        public String toString() {
            boolean[] marked = new boolean[]{false};
            Node<T> n = next.get(marked);
            return value + "->" + ((n == null) ? "null" : n.toString());
        }

        public Node(Node<T> next, T value, boolean isSentinel) {
            this.next = new AtomicMarkableReference<Node<T>>(next, false);
            this.value = value;

        }


    }


}
