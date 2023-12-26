package leetcode2;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SeatManager {

    PriorityQueue<Integer> queue = new PriorityQueue<>();

    int lastAssigned;

    int max;



    public SeatManager(int n) {
        max = n;
    }

    public int reserve() {
        if (queue.isEmpty()) {
            return ++lastAssigned;
        } else {
            return queue.poll();
        }
    }

    public void unreserve(int seatNumber) {
        queue.offer(seatNumber);
    }
}
