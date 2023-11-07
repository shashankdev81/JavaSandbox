package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

public class HighestPeak {

    public static void main(String[] args) {
        HighestPeak highestPeak = new HighestPeak();
//       highestPeak.highestPeak(new int[][]{{0, 0}, {0, 1}});
        int[][] peaks = highestPeak.highestPeak(new int[][]{{0, 0, 1}, {1, 0, 0}, {0, 0, 0}});
        System.out.println(Arrays.stream(peaks).map(i -> Arrays.stream(i).boxed().collect(Collectors.toList())).collect(Collectors.toList()));
    }

    public int[][] highestPeak(int[][] isWater) {

        for (int i = 0; i < isWater.length; i++) {
            for (int j = 0; j < isWater[0].length; j++) {
                isWater[i][j] = isWater[i][j] == 1 ? 0 : -1;
            }
        }
        traverse(isWater, 0);
        return isWater;
    }

    private void traverse(int[][] isWater, int k) {
        if (isAllSet(isWater)) {
            return;
        }
        boolean isChanged = false;
        for (int i = 0; i < isWater.length; i++) {
            for (int j = 0; j < isWater[0].length; j++) {
                if (isWater[i][j] == k) {
                    isChanged = changeNeighbour(isWater, i - 1, j, k + 1) || isChanged;
                    isChanged = changeNeighbour(isWater, i + 1, j, k + 1) || isChanged;
                    isChanged = changeNeighbour(isWater, i, j - 1, k + 1) || isChanged;
                    isChanged = changeNeighbour(isWater, i, j + 1, k + 1) || isChanged;
                }
            }
        }
        if (isChanged) {
            traverse(isWater, k + 1);
        }


    }

    private boolean changeNeighbour(int[][] isWater, int i, int j, int k) {
        if (i < 0 || j < 0 || i >= isWater.length || j >= isWater[0].length || isWater[i][j] != -1) {
            return false;
        }
        isWater[i][j] = k;
        return true;
    }

    private boolean isAllSet(int[][] isWater) {
        return Arrays.stream(isWater).allMatch(arr -> Arrays.stream(arr).allMatch(e -> e != -1));
    }

    private int max(int[][] isWater) {
        return Collections.max(Arrays.stream(isWater).map(arr -> Arrays.stream(arr).max().getAsInt()).collect(Collectors.toList()));
    }
}