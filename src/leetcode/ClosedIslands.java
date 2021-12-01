package leetcode;

public class ClosedIslands {


    public int closedIsland(int[][] grid) {

        int rows = grid.length;
        int cols = grid[0].length;
        int closedIslands = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean isFoundIsland = false;
                if (grid[i][j] == 0) {
                    isFoundIsland = dfs(grid, i, j);
                }
                if (isFoundIsland) {
                    closedIslands++;
                }
            }
        }
        return closedIslands;
    }

    private boolean dfs(int[][] grid, int i, int j) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            return false;
        }

        if (grid[i][j] == 1) {
            return true;
        }

        grid[i][j] = 1;

        /*
         * The reason why we don't and all dfs calls is this - irrespective of whether a dfs in one direction is successful or not
         * we have to make sure we "flood fill" all 0s to 1s so that there are no leftover 0s that can be mistakenly considered as valid islands
         *  */
        boolean isTop = dfs(grid, i, j - 1);
        boolean isBottom = dfs(grid, i, j + 1);
        boolean isLeft = dfs(grid, i - 1, j);
        boolean isRight = dfs(grid, i + 1, j);
        return isTop && isBottom && isLeft && isRight;

    }

    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 0, 1, 1, 0, 1, 0, 0, 1, 0},
                {1, 1, 0, 1, 1, 0, 1, 1, 1, 0},
                {1, 0, 1, 1, 1, 0, 0, 1, 1, 0},
                {0, 1, 1, 0, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 0, 1, 1, 0, 1, 1, 0}};
        //{{1, 1, 1, 1, 1, 1, 1, 0}, {1, 0, 0, 0, 0, 1, 1, 0}, {1, 0, 1, 0, 1, 1, 1, 0}, {1, 0, 0, 0, 0, 1, 0, 1}, {1, 1, 1, 1, 1, 1, 1, 0}};
        ClosedIslands closedIslands = new ClosedIslands();
        System.out.println(closedIslands.closedIsland(grid));
    }
}
