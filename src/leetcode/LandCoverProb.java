package leetcode;

import java.util.HashSet;
import java.util.Set;

public class LandCoverProb {

    public static void main(String[] args) {
        LandCoverProb landCoverProb = new LandCoverProb();
        System.out.println(landCoverProb.getLandMass(new int[][]{
                {0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 0},
                {0, 1, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0}
        }));

        System.out.println(landCoverProb.getLandMass(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0}
        }));
        System.out.println(landCoverProb.getLandMass(new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        }));
        System.out.println(landCoverProb.getLandMass(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1}
        }));
        System.out.println(landCoverProb.getLandMass(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        }));
    }

    private int getLandMass(int[][] matrix) {
        Set<String> visited = new HashSet<>();
        Set<String> notRecoverable = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (!visited.contains(i + "," + j) && matrix[i][j] == 0) {
                    landFill(matrix, i, j, visited, notRecoverable);
                }
            }
        }

        visited = new HashSet<>();
        int land = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (!visited.contains(i + "," + j) && matrix[i][j] == 1) {
                    land += landCost(matrix, i, j, visited);
                }
            }
        }

        return land;
    }

    private int landCost(int[][] matrix, int i, int j, Set<String> visited) {
        if (i < 0 || j < 0 || i >= matrix.length || j >= matrix[0].length || visited.contains(i + "," + j) || matrix[i][j] == 0) {
            return 0;
        }
        int cost = 0;
        visited.add(i + "," + j);
        cost += 1 + landCost(matrix, i, j + 1, visited)
                + landCost(matrix, i, j - 1, visited)
                + landCost(matrix, i - 1, j, visited)
                + landCost(matrix, i + 1, j, visited);
        return cost;
    }

    private boolean landFill(int[][] matrix, int i, int j, Set<String> visited, Set<String> notRecoverable) {
        if (i < 0 || j < 0 || i >= matrix.length || j >= matrix[0].length) {
            return false;
        }
        if (notRecoverable.contains(i + "," + j)) {
            return false;
        }
        if (matrix[i][j] == 1 || visited.contains(i + "," + j)) {
            return true;
        }
        visited.add(i + "," + j);
        matrix[i][j] = 1;
        boolean isRightFilled = landFill(matrix, i, j + 1, visited, notRecoverable);
        if (!isRightFilled) {
            matrix[i][j] = 0;
            notRecoverable.add(i + "," + j);
            return false;
        }
        boolean isDownFilled = landFill(matrix, i + 1, j, visited, notRecoverable);
        if (!isDownFilled) {
            matrix[i][j] = 0;
            notRecoverable.add(i + "," + j);
            return false;
        }
        boolean isLefFilled = landFill(matrix, i, j - 1, visited, notRecoverable);
        if (!isDownFilled) {
            matrix[i][j] = 0;
            notRecoverable.add(i + "," + j);
            return false;
        }
        boolean isTopFilled = landFill(matrix, i - 1, j, visited, notRecoverable);
        if (!isTopFilled) {
            matrix[i][j] = 0;
            notRecoverable.add(i + "," + j);
            return false;
        }

        return true;
    }
}
