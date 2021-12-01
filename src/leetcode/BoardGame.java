package leetcode;


import java.util.*;

public class BoardGame {

    public static final int COLS = 5;

    private class Letter {
        Character c;
        String path = "";
        String code = "";
        int x, y;

        public Letter(Character c, int x, int y) {
            this.c = c;
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Letter letter = (Letter) o;
            return c.equals(letter.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c);
        }

        public void reset() {
            this.path = "";
            this.code = "";
        }
    }

    public String alphabetBoardPath(String target) {
        String[] boardStringArr = new String[]{"abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"};

        Map<Character, Letter> charToLetterMap = new HashMap<>();
        Letter[][] board = new Letter[boardStringArr.length][COLS];

        init(boardStringArr, charToLetterMap, board);


        Letter curr = charToLetterMap.get('a');
        StringBuilder builder = new StringBuilder();
        for (char c : target.toCharArray()) {
            Letter next = charToLetterMap.get(c);
            bfs(curr, next, board, new HashSet<Character>(), builder);
            curr = charToLetterMap.get(c);
            reset(board);
        }

        return builder.toString();
    }

    private void init(String[] boardStringArr, Map<Character, Letter> charToLetterMap, Letter[][] board) {
        for (int i = 0; i < boardStringArr.length; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i == boardStringArr.length - 1 && j > 0) {
                    break;
                }
                board[i][j] = new Letter(boardStringArr[i].charAt(j), i, j);
                charToLetterMap.put(board[i][j].c, board[i][j]);
            }
        }
    }

    private void reset(Letter[][] board) {
        Arrays.stream(board).forEach(arr -> Arrays.stream(arr).filter(e -> e != null).forEach(e -> e.reset()));
    }

    private void bfs(Letter start, Letter target, Letter[][] board, HashSet<Character> visited, StringBuilder builder) {
        Queue<Letter> queue = new LinkedList<Letter>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Letter curr = queue.poll();
            if (curr.c == target.c) {
                target.path += curr.c;
                builder.append(curr.code).append("!");
                break;
            }
            addIfNotVisited(curr, queue, visited, board, curr.x - 1, curr.y, "U");
            addIfNotVisited(curr, queue, visited, board, curr.x + 1, curr.y, "D");
            addIfNotVisited(curr, queue, visited, board, curr.x, curr.y - 1, "L");
            addIfNotVisited(curr, queue, visited, board, curr.x, curr.y + 1, "R");
        }
    }

    private void addIfNotVisited(Letter curr, Queue<Letter> queue, HashSet<Character> visited, Letter[][] board, int x, int y, String code) {
        if (x < 0 || y < 0 || x >= board.length || y >= COLS) {
            return;
        }
        Letter position = board[x][y];
        if (position == null) {
            return;
        }
        position.path = curr.path + position.c;
        position.code = curr.code + code;
        if (!visited.contains(position.c)) {
            visited.add(position.c);
            queue.add(position);
        }
    }

    public static void main(String[] args) {
        BoardGame boardGame = new BoardGame();
        System.out.println(boardGame.alphabetBoardPath("leet"));
        System.out.println(boardGame.alphabetBoardPath("z"));

    }
}
