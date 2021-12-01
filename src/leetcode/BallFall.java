package leetcode;

import java.util.Arrays;

public class BallFall {


    private class Node {

        private int value;
        private int row;
        private int col;
        private Node left;
        private Node right;
        private Node top;
        private Node bottom;

        public Node() {
        }

        public Node(int val, int row, int col, Node left, Node right, Node top, Node bottom) {
            this.value = val;
            this.row = row;
            this.col = col;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        private Node traverse(boolean isTopLanding) {
            if (bottom == null && !isTopLanding) {
                return this;
            }
            Node result = traverseBottom(isTopLanding);
            if (result == null) {
                result = traverseLeft(isTopLanding);
                if (result == null) {
                    result = traverseRight(isTopLanding);
                }
            }

            return result;
        }

        private Node traverseLeft(boolean isTopLanding) {
            if (isTopLanding && value == -1 && left != null && left.value == -1) {
                return left.traverse(!isTopLanding);
            } else {
                return null;
            }

        }

        private Node traverseRight(boolean isTopLanding) {
            if (isTopLanding && value == 1 && right != null && right.value == 1) {
                return right.traverse(!isTopLanding);
            } else {
                return null;
            }
        }

        private Node traverseBottom(boolean isTopLanding) {
            if (!isTopLanding && bottom != null) {
                return bottom.traverse(!isTopLanding);
            } else {
                return null;
            }
        }

    }

    private Node[][] graph;

    public BallFall(int[][] grid) {
        int ROW_MAX = grid.length;
        int COL_MAX = grid[0].length;
        this.graph = new Node[ROW_MAX][COL_MAX];

        for (int i = 0; i < ROW_MAX; i++) {
            for (int j = 0; j < COL_MAX; j++) {

                Node left = null;
                Node right = null;
                Node top = null;
                Node bottom = null;
                if (j > 0) {
                    left = createIfNotExist(i, j - 1);
                }
                if (j < COL_MAX - 1) {
                    right = createIfNotExist(i, j + 1);
                }
                if (i > 0) {
                    top = createIfNotExist(i - 1, j);
                }
                if (i < ROW_MAX - 1) {
                    bottom = createIfNotExist(i + 1, j);
                }

                createIfNotExist(grid[i][j], i, j, left, right, top, bottom);

            }
        }
    }

    public int[] traverse() {
        int[] position = new int[graph[0].length];
        for (int c = 0; c < graph[0].length; c++) {
            Node node = graph[0][c].traverse(true);
            position[c] = node == null ? -1 : node.col;
        }
        return position;
    }

    private void createIfNotExist(int val, int i, int j, Node left, Node right, Node top, Node bottom) {
        if (graph[i][j] == null) {
            graph[i][j] = new Node(val, i, j, left, right, top, bottom);
        } else {
            graph[i][j].value = val;
            graph[i][j].row = i;
            graph[i][j].col = j;
            graph[i][j].left = left;
            graph[i][j].right = right;
            graph[i][j].top = top;
            graph[i][j].bottom = bottom;

        }
    }

    private Node createIfNotExist(int r, int c) {
        if (graph[r][c] == null) {
            graph[r][c] = new Node();
        }
        return graph[r][c];
    }

    public static void main(String[] args) {
        int[][] grid = new int[][]{{1, 1, 1, -1, -1}, {1, 1, 1, -1, -1}, {-1, -1, -1, 1, 1}, {1, 1, 1, 1, -1}, {-1, -1, -1, -1, -1}};
        int[] res = findBall(grid);
        Arrays.stream(res).forEach(r -> System.out.print(r + ","));
        System.out.println("");
        int[][] grid2 = new int[][]{{-1}};
        int[] res2 = findBall(grid2);
        Arrays.stream(res2).forEach(r -> System.out.print(r + ","));
        System.out.println("");
        int[][] grid3 = new int[][]{{1, 1, 1, 1, 1, 1}, {-1, -1, -1, -1, -1, -1}, {1, 1, 1, 1, 1, 1}, {-1, -1, -1, -1, -1, -1}};
        int[] res3 = findBall(grid3);
        Arrays.stream(res3).forEach(r -> System.out.print(r + ","));

    }

    public static int[] findBall(int[][] grid) {
        BallFall ballFall = new BallFall(grid);
        return ballFall.traverse();
    }

}
