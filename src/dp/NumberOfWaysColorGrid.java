package dp;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class NumberOfWaysColorGrid {

    public int numOfWays(int n) {
        int[][] ways = new int[n][3];
        Arrays.stream(ways).forEach(arr -> Arrays.fill(arr, -1));
        return calculate(ways, 0, 0);
    }

    private int calculate(int[][] ways, int i, int j) {
        if (i >= ways.length || j >= ways[0].length || i < 0 || j < 0) {
            return 0;
        }
        if (ways[i][j] != -1) {
            return 0;
        }
        int noOfWays = 1;
        for (int k = 0; k <= 2; k++) {
            if (isValid(ways, i, j, k)) {
                ways[i][j] = k;
                noOfWays += calculate(ways, i + 1, j);
                noOfWays += calculate(ways, i - 1, j);
                noOfWays += calculate(ways, i, j - 1);
                noOfWays += calculate(ways, i, j + 1);
            }
        }
        ways[i][j] = -1;
        return noOfWays;
    }

    private boolean isValid(int[][] ways, int i, int j, int col) {
        boolean isTopDiff = i - 1 < 0 || ways[i - 1][j] != col;
        boolean isBottomDiff = i + 1 >= ways.length || ways[i + 1][j] != col;
        boolean isLeftDiff = j - 1 < 0 || ways[i][j - 1] != col;
        boolean isRightDiff = j + 1 >= ways[0].length || ways[i][j + 1] != col;
        return isTopDiff && isBottomDiff && isLeftDiff && isRightDiff;
    }

    public static void main(String[] args) {
        NumberOfWaysColorGrid ways = new NumberOfWaysColorGrid();
        System.out.println(ways.numOfWays(3));
    }


}
