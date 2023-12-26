package leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OverlappingIntervals {


    public int countWays(int[][] ranges) {
        Arrays.sort(ranges, (r1, r2) -> r1[0] - r2[0]);
        List<int[]> mergedList = new ArrayList<>();
        int[] merged = null;
        for (int i = 0; i < ranges.length; i++) {
            boolean isMerge = merged != null && isOverLap(merged, ranges[i]);
            if (merged == null) {
                merged = new int[2];
                merged[0] = ranges[i][0];
                merged[1] = ranges[i][1];
                mergedList.add(merged);
            } else if (merged != null && !isMerge) {
                mergedList.add(merged);
                merged = new int[2];
                merged[0] = ranges[i][0];
                merged[1] = ranges[i][1];
            } else {
                merged[0] = Math.min(merged[0], ranges[i][0]);
                merged[1] = Math.max(merged[1], ranges[i][1]);
            }
        }
        mergedList.add(merged);
        StringBuilder nodeList = new StringBuilder();

        int size = mergedList.size();
        System.out.println(size);
        long result = factorial(size) / (2 * factorial(size - 2)) % 1000000007;
        return (int) result;
    }

    private boolean isOverLap(int[] m1, int[] m2) {
        return (m1[0] < m2[0] && m2[1] < m1[1]) || (m1[1] == m2[0]) || (m1[1] > m2[0]);
    }

    private long factorial(int num) {
        long result = 1;
        for (int i = 1; i <= num; i++) {
            result *= num;
        }
        return result;

    }
}