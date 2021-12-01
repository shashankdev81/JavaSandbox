package dp;

import java.util.HashMap;
import java.util.Map;

public class MaximumTipProblem {

    private Map<String, Integer> memtable = new HashMap<String, Integer>();


    public int getMaxPossibleTip(int[] tips1, int[] tips2, int x, int y) {
        return getMaxTip(tips1, tips2, 0, 0, 0, x, y, 0);
    }

    private int getMaxTip(int[] tips1, int[] tips2, int i, int cx, int cy, int x, int y, int sum) {
        if (memtable.containsKey(i + "," + cx + "," + cy)) {
            return memtable.get(i + "," + cx + "," + cy);
        }
        int sum1 = 0;
        int sum2 = 0;
        if ((cx == x && cy == y) || i >= tips1.length || i >= tips2.length) {
            return sum;
        }
        if (i < tips1.length && (cx + 1) <= x) {
            sum1 = getMaxTip(tips1, tips2, i + 1, cx + 1, cy, x, y, sum + tips1[i]);
        }

        if (i < tips2.length && (cy + 1) <= y) {
            sum2 = getMaxTip(tips1, tips2, i + 1, cx, cy + 1, x, y, sum + tips2[i]);
        }
        int max = Math.max(sum1, sum2);

        memtable.putIfAbsent(i + "," + cx + "," + cy, max);
        return max;

    }

    public static void main(String[] args) {
        MaximumTipProblem problem = new MaximumTipProblem();
        //System.out.println(problem.getMaxPossibleTip(new int[]{5, 2, 5}, new int[]{2, 10, 2}, 2, 1));
        System.out.println(problem.getMaxPossibleTip(new int[]{1, 2, 3, 4, 5}, new int[]{5, 4, 3, 2, 1}, 3, 3));
        //System.out.println(problem.getMaxPossibleTip(new int[]{8, 7, 15, 19, 16, 16, 18}, new int[]{1, 7, 15, 11, 12, 31, 9}, 3, 4));
    }
}
