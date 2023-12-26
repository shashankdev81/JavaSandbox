package leetcode2;

import java.util.*;

public class SnakeLadder {

    public static void main(String[] args) {
        SnakeLadder snakeLadder = new SnakeLadder();
        //System.out.println(snakeLadder.snakesAndLadders(new int[][]{{-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, -1, -1, -1, -1, -1}, {-1, 35, -1, -1, 13, -1}, {-1, -1, -1, -1, -1, -1}, {-1, 15, -1, -1, -1, -1}}));
        System.out.println(snakeLadder.snakesAndLadders(
            new int[][]{{-1, -1, 19, 10, -1}, {2, -1, -1, 6, -1}, {-1, 17, -1, 19, -1},
                {25, -1, 20, -1, -1}, {-1, -1, -1, -1, 15}}));

    }

    public int snakesAndLadders(int[][] board) {
        Map<Integer, Integer> boardMap = new HashMap<>();
        int moves = -1;
        int n = board.length;
        int endSquare = n * n;
        int[] boardVisited = new int[endSquare + 1];
        boolean isRight = true;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int offset = isRight ? col : n - (col + 1);
                int cellNumber = endSquare - n * row - offset;
                //System.out.println("wo, col, offset, cellNumber="+row+","+col+","+offset+","+cellNumber);
                boardMap.put(cellNumber, board[row][col]);
            }
            isRight = false;
        }
        //System.out.println("boardMap=+"+boardMap);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(1);
        moves = bfs(boardMap, board.length, boardVisited, queue);
        //moves = dfs(boardMap, board.length, 1, 0, boardVisited);
        return moves;
    }

    private int bfs(Map<Integer, Integer> boardMap, int n, int[] boardVisited,
        Queue<Integer> queue) {
        int moves = 0;
        if (queue.peek() == n * n || Arrays.stream(boardVisited).sum() == n * n) {
            return 0;
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            moves++;
            while (size > 0 && !queue.isEmpty()) {
                int currPos = queue.poll();
                for (int i = 1; i <= 6; i++) {
                    int nextPos = Math.min(currPos + i, n * n);
                    if (boardMap.get(nextPos) != -1) {
                        boardVisited[nextPos] = 1;
                        nextPos = boardMap.get(nextPos);
                    }
                    if (boardVisited[nextPos] == 1) {
                        continue;
                    }
                    boardVisited[nextPos] = 1;
                    if (nextPos == n * n) {
                        return moves;
                    }
                    queue.offer(nextPos);
                }
                size--;
            }
        }
        return moves;
    }

    private int dfs(Map<Integer, Integer> boardMap, int n, int currPos, int count,
        int[] boardVisited) {
        if (currPos == n * n || Arrays.stream(boardVisited).sum() == n * n) {
            return 0;
        }
        int moves = count;
        for (int i = 1; i < 6; i++) {
            int nextPos = Math.min(currPos + i, n * n);
            if (boardMap.get(nextPos) != -1) {
                nextPos = boardMap.get(nextPos);
            }
            if (boardVisited[nextPos] == 1) {
                continue;
            }
            if (nextPos > 30) {
                System.out.println(nextPos);
            }
            boardVisited[nextPos] = 1;
            moves = Math.min(moves, dfs(boardMap, n, nextPos, count + 1, boardVisited));
            boardVisited[nextPos] = 0;
        }
        return moves;

    }


}