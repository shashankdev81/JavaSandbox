package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

public class LegalMove {
    public static void main(String[] args) {
        LegalMove legalMove = new LegalMove();
        legalMove.checkMove(new char[][]{{'B', 'B', 'B', '.', 'W', 'W', 'B', 'W'}, {'B', 'B', '.', 'B', '.', 'B', 'B', 'B'}, {'.', 'W', '.', '.', 'B', '.', 'B', 'W'}, {'B', 'W', '.', 'W', 'B', '.', 'B', '.'}, {'B', 'W', 'W', 'B', 'W', '.', 'B', 'B'}, {'.', '.', 'W', '.', '.', 'W', '.', '.'}, {'W', '.', 'W', 'B', '.', 'W', 'W', 'B'}, {'B', 'B', 'W', 'W', 'B', 'W', '.', '.'}}, 5, 6, 'B');

    }

    private int[][] DIR = new int[][]{{-1, 0}, {1, 0}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}};
    private int[][] HOR = new int[][]{{-1, 0}, {1, 0}};
    private int[][] VERT = new int[][]{{0, 1}, {0, -1}};
    private int[][] DIAG = new int[][]{{-1, -1}, {1, 1}, {1, -1}, {-1, 1}};

    public boolean checkMove(char[][] board, int rMove, int cMove, char color) {
        if (board[rMove][cMove] != '.') {
            return false;
        }
        boolean isLegalVert = recurse(board, rMove, cMove, color, VERT, 0);
        boolean isLegalHor = recurse(board, rMove, cMove, color, HOR, 0);
        boolean isLegalDiag = recurse(board, rMove, cMove, color, DIAG, 0);
        System.out.println("isLegal=" + isLegalHor + "," + isLegalVert + "," + isLegalDiag);
        return isLegalHor || isLegalVert || isLegalDiag;
        //return recurse(board, rMove, cMove, color, DIR, false);
    }

    private boolean recurse(char[][] board, int rMove, int cMove, char color, int[][] DIR, int count) {
        String[] responses = new String[2];
        final Map<String, Integer> termFrequency = new LinkedHashMap<>();
        Arrays.stream(responses[2].split(" ")).filter(s -> termFrequency.containsKey(s)).distinct().forEach(s -> {
            termFrequency.put(s, termFrequency.get(s) + 1);
        });

        PriorityQueue<Long> kthLargest = new PriorityQueue<Long>(Comparator.reverseOrder());

        termFrequency.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue() == e2.getValue() ? e1.getKey().compareTo(e2.getKey()) : e1.getValue() - e2.getValue())
                .map(e -> e.getKey()).collect(Collectors.toList())
                .toArray(new String[termFrequency.size()]);
        List<Map.Entry<String, Integer>> results = termFrequency.entrySet().stream().collect(Collectors.toList());
        results.sort((e1, e2) -> e1.getValue() == e2.getValue() ? e1.getKey().compareTo(e2.getKey()) : e1.getValue() - e2.getValue());
        results.stream().map(e -> e.getKey()).collect(Collectors.toList()).toArray(new String[termFrequency.size()]);

        boolean isLegal = false;
        for (int i = 0; i < DIR.length; i++) {
            int x = rMove + DIR[i][0];
            int y = cMove + DIR[i][1];
            if (!isValid(x, y, board)) {
                continue;
            }
            if (board[x][y] == color && count > 0) {
                return true;
            } else if (board[x][y] != color) {
                char c = board[x][y];
                board[x][y] = 'X';
                isLegal = isLegal || recurse(board, x, y, color, DIR, count + 1);
                board[x][y] = c;
            }

        }

        return isLegal;
    }

    private boolean isValid(int x, int y, char[][] board) {
        return x >= 0 && y >= 0 && x < board.length && y < board[0].length && board[x][y] != '.' && board[x][y] != 'X';
    }
}
