package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KnightPosition {

    private static int SIZE = 4;

    private boolean[][] board = new boolean[SIZE][SIZE];

    private Position start = new Position(1, 1);

    private Position dest = new Position(3, 3);

    public static void main(String[] args) {
        KnightPosition knightPosition = new KnightPosition();
        test(knightPosition);
    }

    private static void test(KnightPosition knightPosition) {
        Position start = new Position(1, 1);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Position end = new Position(i, j);
                System.out.println("isReachable position=" + end.x + "," + end.y + ":" + knightPosition.isReachable(start, end));
            }

        }
    }

    public boolean isReachable(Position start, Position dest) {
        board[start.x][start.y] = true;
        return traverse(board, start, dest);
    }

    private void print() {
        System.out.println("======================================================");
        for (int i = 0; i < SIZE; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < SIZE; j++) {
                builder.append(board[i][j] ? "X" : "0");
            }
            System.out.println(builder.toString());
        }
    }

    private boolean traverse(boolean[][] board, Position currPos, Position destPos) {
        if (currPos.equals(destPos)) {
            return true;
        }
        for (Position nextPos : getValidPositions(currPos)) {

            if (board[nextPos.x][nextPos.y]) {
                continue;
            }
            board[nextPos.x][nextPos.y] = true;
            if (!traverse(board, nextPos, destPos)) {
                board[nextPos.x][nextPos.y] = false;
            } else {
                return true;
            }
        }
        return false;
    }

    private List<Position> getValidPositions(Position position) {
        List<Position> validPositions = new ArrayList<Position>();

        if (isValid(position.x + 2, position.y - 1)) {
            validPositions.add(new Position(position.x + 2, position.y - 1));
        }
        if (isValid(position.x + 2, position.y + 1)) {
            validPositions.add(new Position(position.x + 2, position.y + 1));
        }
        if (isValid(position.x - 2, position.y - 1)) {
            validPositions.add(new Position(position.x - 2, position.y - 1));
        }
        if (isValid(position.x - 2, position.y + 1)) {
            validPositions.add(new Position(position.x - 2, position.y + 1));
        }

        if (isValid(position.x + 1, position.y - 2)) {
            validPositions.add(new Position(position.x + 1, position.y - 2));
        }
        if (isValid(position.x + 1, position.y + 2)) {
            validPositions.add(new Position(position.x + 1, position.y + 2));
        }
        if (isValid(position.x - 1, position.y - 2)) {
            validPositions.add(new Position(position.x - 1, position.y - 2));
        }
        if (isValid(position.x - 1, position.y + 2)) {
            validPositions.add(new Position(position.x - 1, position.y + 2));
        }


        return validPositions;
    }

    private boolean isValid(int x, int y) {
        return x < SIZE && y < SIZE && x >= 0 && y >= 0;

    }

    private static class Position {

        private int x;
        private int y;


        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
