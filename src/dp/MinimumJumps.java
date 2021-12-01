package dp;

import java.util.HashMap;
import java.util.Map;

public class MinimumJumps {


    private Map<Integer, Integer> minSteps = new HashMap<Integer, Integer>();

    public static void main(String[] args) {
        MinimumJumps minimumJumps = new MinimumJumps();
        //System.out.println(minimumJumps.getMinJumps(new int[]{1, 2, 4, 2, 9, 8, 6}, 0));
        //System.out.println(minimumJumps.getMinJumps(new int[]{1, 5, 4, 2, 9, 8, 6}, 0));
        System.out.println(minimumJumps.getMinJumps(new int[]{1, 2, 2, 2, 9, 8, 6}, 0));

    }

    private int getMinJumps(int[] hops, int start) {
        System.out.println("");
        if (minSteps.containsKey(start)) {
            return minSteps.get(start);
        }
        if (start >= hops.length) {
            return Integer.MAX_VALUE;
        }
        if (start == hops.length - 1) {
            return 1;
        }
        int min = Integer.MAX_VALUE;
        int begin = start + 1;
        int farthest = start + hops[start];
        int distance = hops.length - 1 - start;
        if (distance <= hops[start]) {
            return 1;
        }
        for (int i = begin; i <= farthest && i < hops.length; i++) {
            int stepsFromStart = 1 + getMinJumps(hops, i);
            min = Math.min(min, stepsFromStart);
        }

        minSteps.putIfAbsent(start, min);
        return min;
    }
}
