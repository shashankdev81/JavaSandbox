package leetcode2;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class RaceCar {


    public static void main(String[] args) {
        RaceCar raceCar = new RaceCar();
        System.out.println(raceCar.racecar(3));
        System.out.println(raceCar.racecar(6));
    }

    public int racecar(int target) {
        return move(0, 1, target);
    }

    private int move(int pos, int speed, int target) {
        Queue<Pair> queue = new ArrayDeque<>();
        queue.offer(new Pair<Integer, Integer>(0, 1));
        int steps = 0;
        Set<Pair> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            int count = queue.size();
            boolean isDone = false;
            while (count > 0) {
                Pair<Integer, Integer> leg = queue.poll();
                visited.add(leg);
                if (leg.getKey() == target) {
                    isDone = true;
                    break;
                }

                Pair<Integer, Integer> nextLeg1 = new Pair<Integer, Integer>(
                    leg.getKey() + leg.getValue(),
                    leg.getValue() * 2);
                Pair<Integer, Integer> nextLeg2 = new Pair<Integer, Integer>(leg.getKey(),
                    leg.getValue() > 0 ? -1 : 1);
                if (!visited.contains(nextLeg1)) {
                    queue.offer(nextLeg1);
                }
                if (!visited.contains(nextLeg2)) {
                    queue.offer(nextLeg2);
                }
                count--;
            }
            if (isDone) {
                break;
            }
            steps++;
        }

        return steps;
    }

    class Pair<K, V> {

        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}