package leetcode2;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;

public class SORTracker {

    private PriorityQueue<Pair> sortedLocationsHead = new PriorityQueue<>(
        getPairComparator().reversed());

    private PriorityQueue<Pair> sortedLocationsTail = new PriorityQueue<>(getPairComparator());

    private Comparator<Pair> getPairComparator() {
        return (p1, p2) -> {
            if (p1.score == p2.score) {
                return p1.name.compareTo(p2.name);
            } else {
                return p2.score - p1.score;
            }
        };
    }

    public SORTracker() {
    }

    public void add(String name, int score) {
        Pair next = new Pair(name, score);
        sortedLocationsHead.offer(next);
        sortedLocationsTail.offer(sortedLocationsHead.poll());
    }

    public String get() {
        Pair best = new Pair("", -1);
        best = sortedLocationsTail.poll();
        sortedLocationsHead.offer(best);
        return best.name;
    }

    class Pair {

        String name;
        int score;

        public Pair(String n, int s) {
            name = n;
            score = s;
        }
    }
}
