package backtracking;

public class RatInMazeProblem {

    public static void main(String[] args) {

        int[][] maze = new int[][]{{1, 1, 0, 1}, {1, 1, 0, 1}, {1, 1, 0, 0}, {0, 1, 1, 1}};
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        traverseOnce(0, 0, maze, visited, "");

    }

    private static void traverse(int i, int j, int[][] maze, boolean[][] visited, String path) {
        int ROW = maze.length;
        int COL = maze[0].length;
        if (i < 0 || i >= ROW || j < 0 || j >= COL || visited[i][j] || maze[i][j] == 0) {
            return;
        }
        String newPath = path + "(" + i + "," + j + ")";
        if (i == ROW - 1 && j == COL - 1) {
            System.out.println("Reached destination" + path);
            return;
        }
        visited[i][j] = true;
        traverse(i - 1, j - 1, maze, visited, newPath);
        traverse(i - 1, j, maze, visited, newPath);
        traverse(i - 1, j + 1, maze, visited, newPath);
        traverse(i, j - 1, maze, visited, newPath);
        traverse(i, j + 1, maze, visited, newPath);
        traverse(i + 1, j - 1, maze, visited, newPath);
        traverse(i + 1, j, maze, visited, newPath);
        traverse(i + 1, j + 1, maze, visited, newPath);
        visited[i][j] = false;

    }

    private static boolean traverseOnce(int i, int j, int[][] maze, boolean[][] visited, String path) {
        int ROW = maze.length;
        int COL = maze[0].length;
        boolean isReachedDest = false;
        if (i < 0 || i >= ROW || j < 0 || j >= COL || visited[i][j] || maze[i][j] == 0) {
            return false;
        }
        String newPath = path + "(" + i + "," + j + ")";
        if (i == ROW - 1 && j == COL - 1) {
            System.out.println("Reached destination" + path);
            return true;
        }
        visited[i][j] = true;
        isReachedDest = traverseOnce(i - 1, j - 1, maze, visited, newPath);
        if (!isReachedDest) isReachedDest = traverseOnce(i - 1, j, maze, visited, newPath);
        if (!isReachedDest) isReachedDest = traverseOnce(i - 1, j + 1, maze, visited, newPath);
        if (!isReachedDest) isReachedDest = traverseOnce(i, j - 1, maze, visited, newPath);
        if (!isReachedDest) isReachedDest = traverseOnce(i, j + 1, maze, visited, newPath);
        if (!isReachedDest) isReachedDest = traverseOnce(i + 1, j - 1, maze, visited, newPath);
        if (!isReachedDest) isReachedDest = traverseOnce(i + 1, j, maze, visited, newPath);
        if (!isReachedDest) isReachedDest = traverseOnce(i + 1, j + 1, maze, visited, newPath);
        visited[i][j] = false;
        return isReachedDest;

    }
}
