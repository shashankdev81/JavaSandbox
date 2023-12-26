package object_orinted_design;

import java.util.stream.Collectors;

public class Chess {

    private Piece[][] pieces;

    private int WIDTH = 8;

    public Chess() {
        pieces = new Piece[WIDTH][WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            pieces[1][i] = new Pawn(Color.WHITE);
        }
        for (int i = 0; i < WIDTH; i++) {
            pieces[6][i] = new Pawn(Color.BLACK);
        }

        pieces[0][0] = new Rook(Color.WHITE);
        pieces[0][WIDTH - 1] = new Rook(Color.WHITE);
        pieces[WIDTH - 1][0] = new Rook(Color.BLACK);
        pieces[WIDTH - 1][WIDTH - 1] = new Rook(Color.BLACK);

        pieces[0][1] = new Knight(Color.WHITE);
        pieces[0][WIDTH - 1 - 1] = new Knight(Color.WHITE);
        pieces[WIDTH - 1][1] = new Knight(Color.BLACK);
        pieces[WIDTH - 1][WIDTH - 1 - 1] = new Knight(Color.BLACK);

        pieces[0][2] = new Bishop(Color.WHITE);
        pieces[0][WIDTH - 1 - 2] = new Bishop(Color.WHITE);
        pieces[WIDTH - 1][2] = new Bishop(Color.BLACK);
        pieces[WIDTH - 1][WIDTH - 1 - 2] = new Bishop(Color.BLACK);

        pieces[0][3] = new King(Color.WHITE);
        pieces[0][WIDTH - 1 - 3] = new Queen(Color.WHITE);
        pieces[WIDTH - 1][3] = new King(Color.BLACK);
        pieces[WIDTH - 1][WIDTH - 1 - 3] = new Queen(Color.BLACK);


    }

    public enum Color {
        WHITE, BLACK;

    }

    public boolean move(int preX, int preY, int nextX, int nextY) {
        Piece piece = pieces[preX][preY];
        boolean isValid = piece.isValid(nextX, nextY);
        if (isValid) {
            pieces[preX][preY] = null;
            pieces[nextX][nextY] = piece;
        }
        return isValid;
    }

    public abstract class Piece {

        private int currX;

        private int currY;

        protected Color color;

        public Piece(Color color) {
            this.color = color;
        }

        public boolean isValid(int x, int y) {
            String prefix = "".substring(0, 3);
            prefix.chars().mapToObj(i -> (char) i).sorted().map(c -> "" + c)
                .reduce((c1, c2) -> c1 + c2).get();

            return false;
        }

        public boolean move(int x, int y) {
            pieces[currX][currY] = null;
            boolean isValid = isValid(x, y);
            if (isValid) {
                pieces[x][y] = this;
                currX = x;
                currY = y;
            }
            return isValid;
        }

        public Color getColor() {
            return color;
        }
    }

    public class Pawn extends Piece {

        public Pawn(Color color) {
            super(color);
        }

        @Override
        public boolean isValid(int x, int y) {
            return true;
        }
    }

    public class Rook extends Piece {

        public Rook(Color color) {
            super(color);
        }

        @Override
        public boolean isValid(int x, int y) {
            return true;
        }
    }

    public class Bishop extends Piece {

        public Bishop(Color color) {
            super(color);
        }

        @Override
        public boolean isValid(int x, int y) {
            return true;
        }
    }

    public class Knight extends Piece {

        public Knight(Color color) {
            super(color);
        }

        @Override
        public boolean isValid(int x, int y) {
            return true;
        }
    }

    public class King extends Piece {

        public King(Color color) {
            super(color);
        }

        @Override
        public boolean isValid(int x, int y) {
            return true;
        }
    }

    public class Queen extends Piece {

        public Queen(Color color) {
            super(color);
        }

        @Override
        public boolean isValid(int x, int y) {
            return true;
        }
    }

}
