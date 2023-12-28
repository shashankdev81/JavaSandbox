package leetcode2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ServerCount {

    public static void main(String[] args) {
        ServerCount serverCount = new ServerCount();
        System.out.println(serverCount.countServers(new int[][]{{1, 0}, {1, 1}}));
    }

    public int countServers(int[][] grid) {
        int[] rowSum = new int[grid.length];
        int[] colSum = new int[grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            rowSum[i] = Arrays.stream(grid[i]).sum();
        }
        for (int j = 0; j < grid[0].length; j++) {
            int sum = 0;
            for (int i = 0; i < grid.length; i++) {
                sum += grid[i][j];
            }
            if (sum > 1) {
                colSum[j] = sum;
            } else {

            }
        }

        int[] arr = new int[2];
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : arr) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        List<Entry<Integer, Integer>> entries = countMap.entrySet().stream()
            .sorted((e1, e2) -> e1.getValue() - e1.getValue()).collect(
                Collectors.toList());
        int sum = Arrays.stream(rowSum).sum() + Arrays.stream(colSum).sum();
        List<Integer> reportees = Arrays.stream(new int[]{1}).filter(m -> m == 1)
            .mapToObj(i -> Integer.valueOf(i)).collect(Collectors.toList());

        return sum;

    }
}
