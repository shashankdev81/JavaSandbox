package leetcode2;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MaizeProb {


    int[][] DIRECTIONS = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        MaizeProb maizeProb = new MaizeProb();
        System.out.println(maizeProb.nearestExit(new char[][]{{'+', '+', '.', '+'}, {'.', '.', '.', '+'}, {'+', '+', '+', '.'}}, new int[]{1, 2}));
    }

    public int nearestExit(char[][] maze, int[] entrance) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(entrance);
        int moves = -1;
        int[] cell = null;
        boolean isFound = false;
        while (!queue.isEmpty() && !isFound) {
            int count = queue.size();
            moves++;
            while (count > 0 && !isFound) {
                cell = queue.poll();
                System.out.println("cell=" + cell[0] + "," + cell[1]);
                char cellValue = maze[cell[0]][cell[1]];
                boolean isEntrance = cell[0] == entrance[0] && cell[1] == entrance[1];
                boolean isWall = cellValue == '+';
                boolean isBoundary = cell[0] == 0 || cell[0] == maze.length - 1 || cell[1] == 0 || cell[1] == maze[0].length - 1;
                isFound = !isEntrance && !isWall && isBoundary;
                if (isFound) {
                    break;
                }
                System.out.println("count,moves=" + count + "," + moves);
                for (int i = 0; i < DIRECTIONS.length; i++) {
                    int[] next = new int[2];
                    int x = cell[0] + DIRECTIONS[i][0];
                    int y = cell[1] + DIRECTIONS[i][1];
                    if (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length) {
                        System.out.println("x,y,val=" + x + "," + y + "," + maze[x][y] + "," + entrance[0] + "," + entrance[1]);
                        if (maze[x][y] == '.' && !(x == entrance[0] && y == entrance[1])) {
                            System.out.println("next=" + x + "," + y);
                            next[0] = x;
                            next[1] = y;
                            maze[x][y] = 'V';
                            queue.offer(next);
                        }
                    }
                }
                count--;
            }
        }
        boolean isBoundary = cell[0] == 0 || cell[0] == maze.length - 1 || cell[1] == 0 || cell[1] == maze[0].length - 1;
        return moves == 0 || !isBoundary ? -1 : moves;
    }
}