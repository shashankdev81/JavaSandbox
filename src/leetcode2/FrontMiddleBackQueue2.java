package leetcode2;

import java.util.*;

public class FrontMiddleBackQueue2 {
    int size;
    float middle = 0;
    float back = 0;
    float front = 0;
    ArrayList<Float> list;
    Map<Float, Integer> map;

    public FrontMiddleBackQueue2() {
        map = new TreeMap<>();
        list = new ArrayList<>();
    }

    public static void main(String[] args) {
        FrontMiddleBackQueue2 queue = new FrontMiddleBackQueue2();
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
        front = isEmpty() ? 0 : list.get(list.indexOf(front)) - 1;
        map.put(front, val);
        size++;
        list.add(0, front);
        back = list.get(size - 1);
        middle = list.get(size / 2);
    }

    private boolean isEmpty() {
        return size == 0;
    }

    public void pushMiddle(int val) {
        if (isEmpty()) {
            pushFront(val);
            return;
        }
        if (size == 1) {
            pushBack(val);
            return;
        }
        size++;
        int midIndex = list.indexOf(middle);
        float indexVal = (list.get(midIndex) + list.get(midIndex - 1)) / 2;
        list.add(midIndex, indexVal);
        middle = list.get(size / 2);
        back = list.get(size - 1);
        return;
    }

    public void pushBack(int val) {
        if (isEmpty()) {
            pushFront(val);
            return;
        }
        map.put(list.get(size - 1) + 1, val);
        size++;
        middle = list.get((int) size / 2);
        back = list.get(size - 1);
        return;
    }

    public int popFront() {
        return 0;


    }

    public int popMiddle() {
        return 0;
    }

    public int popBack() {
        return 0;

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