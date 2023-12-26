package leetcode2;

import java.util.ArrayDeque;

public class ComposedFrontMiddleBackQueue {


    private ArrayDeque<Integer> frontQueue = new ArrayDeque<>();

    private ArrayDeque<Integer> backQueue = new ArrayDeque<>();


    public void pushFront(int val) {
        frontQueue.offerFirst(val);
        balance();
        print();
    }

    public void pushMiddle(int val) {
        if (frontQueue.size() <= backQueue.size()) {
            frontQueue.offerLast(val);
        } else {
            backQueue.offerFirst(val);
        }
        balance();
        print();
    }

    public void pushBack(int val) {
        backQueue.offerLast(val);
        balance();
        print();
    }

    private void balance() {
        if (backQueue.size() - frontQueue.size() > 1) {
            frontQueue.offerLast(backQueue.pollFirst());
        } else if (frontQueue.size() - backQueue.size() > 1) {
            backQueue.offerFirst(frontQueue.pollLast());
        }
    }

    public int popFront() {
        if (frontQueue.isEmpty()) {
            return -1;
        }
        int val = frontQueue.pollFirst();
        balance();
        print();
        return val;
    }

    public int popMiddle() {
        int val = -1;
        if (frontQueue.size() <= backQueue.size()) {
            val = backQueue.pollLast();
        } else {
            val = frontQueue.pollLast();
        }
        if (backQueue.size() - frontQueue.size() >= 1) {
            frontQueue.offerLast(backQueue.pollFirst());
        }
        print();
        return val;
    }

    public int popBack() {
        if (backQueue.isEmpty() && frontQueue.isEmpty()) {
            return -1;
        }
        int val = backQueue.isEmpty() ? frontQueue.pollLast() : backQueue.pollLast();
        balance();
        print();
        return val;
    }

    private void print() {
        System.out.println(frontQueue + "," + backQueue);
    }
}

/**
 * Your FrontMiddleBackQueue object will be instantiated and called as such: FrontMiddleBackQueue
 * obj = new FrontMiddleBackQueue(); obj.pushFront(val); obj.pushMiddle(val); obj.pushBack(val); int
 * param_4 = obj.popFront(); int param_5 = obj.popMiddle(); int param_6 = obj.popBack();
 */