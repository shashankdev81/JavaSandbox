package leetcode;

public class ClosedIslandsCorrect {

    int[][] matrix;
    int m;
    int n;
    boolean flag;

    private void dfs(int x, int y) {
        if (x < 0 || x >= m || y < 0 || y >= n) {
            flag = false;
            return;
        }

        if (matrix[x][y] == 1) {
            return;
        }

        matrix[x][y] = 1;

        dfs(x - 1, y);
        dfs(x, y - 1);
        dfs(x + 1, y);
        dfs(x, y + 1);
    }

    public int closedIsland(int[][] grid) {
        this.matrix = grid;
        m = grid.length;
        n = grid[0].length;
        int count = 0;
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                if (matrix[x][y] == 0) {
                    flag = true;
                    dfs(x, y);
                    if (flag) {
                        count++;
                    }
                }
            }
        }
        return count;
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
        ClosedIslandsCorrect closedIslands = new ClosedIslandsCorrect();
        System.out.println(closedIslands.closedIsland(grid));
    }
}
