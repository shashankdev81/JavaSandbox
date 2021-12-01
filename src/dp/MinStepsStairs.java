package dp;

import java.util.HashMap;
import java.util.Map;

public class MinStepsStairs {

    private Map<Integer, Integer> stairToWays = new HashMap<>();

    private int getSteps(int n) {
        if (stairToWays.containsKey(n)) {
            return stairToWays.get(n);
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int steps = getSteps(n - 1) + getSteps(n - 2);
        stairToWays.putIfAbsent(n, steps);
        return steps;
    }

    public static void main(String[] args) {
        MinStepsStairs stepsStairs = new MinStepsStairs();
        System.out.println(stepsStairs.getSteps(5));
    }
}
