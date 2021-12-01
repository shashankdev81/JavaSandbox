package leetcode;


import java.util.*;

public class AsteroidCollision {

    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> queue = new LinkedList<>();
        Arrays.stream(asteroids).forEach(a -> queue.add(a));
        Queue<Integer> transfer = new LinkedList<>();
        while (true) {
            int origSize = queue.size();
            transfer = new LinkedList<>();
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                if (queue.peek() == null) {
                    transfer.add(curr);
                    continue;
                }
                int next = queue.poll();
                if (curr > 0 && next < 0) {
                    if (Math.abs(next) == curr) {
                        continue;
                    } else if (Math.abs(next) > curr) {
                        queue.addFirst(next);
                    } else {
                        queue.addFirst(curr);
                    }
                } else if ((curr > 0 && next > 0) || (curr < 0 && next > 0)) {
                    transfer.add(curr);
                    queue.addFirst(next);
                } else if (curr < 0 && next < 0) {
                    transfer.add(curr);
                    transfer.add(next);
                }
            }
            if (transfer.size() == origSize) {
                break;
            }
            queue.addAll(transfer);
        }
        return transfer.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        AsteroidCollision asteroidCollision = new AsteroidCollision();
        int[] res1 = asteroidCollision.asteroidCollision(new int[]{5, 10, -5});
        int[] res2 = asteroidCollision.asteroidCollision(new int[]{8, -8});
        int[] res3 = asteroidCollision.asteroidCollision(new int[]{10, 2, -5});
        int[] res4 = asteroidCollision.asteroidCollision(new int[]{-2, -1, 1, 2});
    }
}
