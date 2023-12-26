package leetcode2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SurroundedRegions {

    private int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) {
        SurroundedRegions surroundedRegions = new SurroundedRegions();
        char[][] board = new char[][]{{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'},
            {'X', 'X', 'O', 'O'},
            {'X', 'O', 'X', 'X'}};
        surroundedRegions.solve(board);
        System.out.println("Done");
    }

    public void solve(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, new HashSet<>(), true);
            }
        }
    }

    private boolean dfs(char[][] board, int x, int y, Set<String> visited, boolean isRegionFlag) {
        boolean isRegion = isRegionFlag;
        if (visited.contains(x + "," + y)) {
            return false;
        }
        visited.add(x + "," + y);
        for (int i = 0; i < directions.length; i++) {
            int newx = x + directions[i][0];
            int newy = y + directions[i][1];
            if (newx < 0 || newy < 0 || newx >= board.length || newy >= board[0].length) {
                continue;
            }
            if (!isRegion && board[newx][newy] == 'O') {
                isRegion = dfs(board, newx, newy, visited, isRegion);
            } else if (board[newx][newy] == 'O' && (newx == 0 || newy == 0
                || newx == board.length - 1 || newy == board[0].length - 1)) {
                isRegion = false;
                isRegion = dfs(board, newx, newy, visited, isRegion);
            } else if (board[newx][newy] == 'O') {
                board[newx][newy] = 'X';
                isRegion = dfs(board, newx, newy, visited, isRegion);
                if (!isRegion) {
                    board[newx][newy] = 'O';
                }
            }

        }
        return isRegion;
    }

}
