package leetcode2;

import java.util.ArrayDeque;
import java.util.Queue;

public class BoardGame {

    int[][] DIRECTIONS = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

    public static void main(String[] args) {
        BoardGame game = new BoardGame();
        game.updateBoard(new char[][]{{'E', 'E', 'E', 'E', 'E'}, {'E', 'E', 'M', 'E', 'E'}, {'E', 'E', 'E', 'E', 'E'}, {'E', 'E', 'E', 'E', 'E'}}, new int[]{3, 0});
    }

    public char[][] updateBoard(char[][] board, int[] click) {
        Queue<int[]> queue = new ArrayDeque<>();
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        if (board[click[0]][click[1]] == 'B') {
            return board;
        }

        queue.offer(click);
        while (!queue.isEmpty()) {
            int count = queue.size();
            System.out.println("count=" + count);
            int mines = 0;
            while (count > 0) {
                int[] cell = queue.poll();
                //boolean isMineFound = false;
                System.out.println("cel=" + board[cell[0]][cell[1]]);
                if (board[cell[0]][cell[1]] == 'E' || board[cell[0]][cell[1]] == 'R') {
                    if (board[cell[0]][cell[1]] == 'R') {
                        mines = getMinesInNeighbourhood(cell, board);
                        if (mines > 0) {
                            board[cell[0]][cell[1]] = String.valueOf(mines).charAt(0);
                        } else {
                            board[cell[0]][cell[1]] = 'B';
                        }
                    }
                    for (int i = 0; i < DIRECTIONS.length; i++) {
                        int nextX = cell[0] + DIRECTIONS[i][0];
                        int nextY = cell[1] + DIRECTIONS[i][1];
                        if (nextX < 0 || nextY < 0 || nextX >= board.length || nextY >= board[0].length || board[nextX][nextY] == 'M') {
                            continue;
                        }
                        if (board[nextX][nextY] == 'E') {
                            board[nextX][nextY] = 'R';
                            queue.offer(new int[]{nextX, nextY});
                        }
                    }
                }
                count--;
            }
        }
        return board;
    }

    private int getMinesInNeighbourhood(int[] cell, char[][] board) {
        int mines = 0;
        for (int i = 0; i < DIRECTIONS.length; i++) {
            int nextX = cell[0] + DIRECTIONS[i][0];
            int nextY = cell[1] + DIRECTIONS[i][1];
            if (nextX < 0 || nextY < 0 || nextX >= board.length || nextY >= board[0].length) {
                continue;
            }
            if (board[nextX][nextY] == 'M') {
                mines++;
            }
        }
        return mines;
    }

}