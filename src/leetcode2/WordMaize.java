package leetcode2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordMaize {

    public static void main(String[] args) {
        WordMaize wordMaize = new WordMaize();
        boolean isExists = wordMaize.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCCED");
        System.out.println(isExists);
    }

    private static final int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public boolean exist(char[][] board, String word) {
        boolean isExists = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                isExists = isExists || traverse(board, i, j, "", word, new HashSet<String>());
                System.out.println(i + "," + j + "," + isExists);
            }
        }
        return isExists;
    }

    public boolean traverse(char[][] board, int x, int y, String sequence, String word, Set<String> visited) {
        boolean isExists = false;
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
            return false;
        }

        visited.add(x + "-" + y);
        String newSequence = sequence + board[x][y];
        if(!word.startsWith(sequence)){
            return false;
        }
        if (word.equals(newSequence)) {
            return true;
        }
        for (int i = 0; i < directions.length; i++) {
            int newX = x + directions[i][0];
            int newY = y + directions[i][1];
            if (!visited.contains(newX + "-" + newY)) {
                isExists = isExists || traverse(board, newX, newY, newSequence, word, visited);
            }
            if (isExists) {
                break;
            }
        }
        visited.remove(x + "-" + y);
        return isExists;

    }
}