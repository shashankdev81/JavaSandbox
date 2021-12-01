package backtracking;


/**
 * 1. Create a 2d array to represent the chess board and queen position
 * 3.   for v = 1..8 (x=7 to x=0, y=0) do the following
 * 4.       If current pos is safe for queen, place queen in that pos
 * 5.           Recursively proceed on subequent columns (y=1)
 * If the recursion in above step did  not yield a postive outcome then continue on step 4 else break
 */

public class NQueens {

    private int SIZE = 4;

    private boolean[][] chessBoard = new boolean[SIZE][SIZE];

    public static void main(String[] args) {
        NQueens nqueens = new NQueens();
        nqueens.execute();
    }

    public void execute() {
        //start with a and proceed to h
        print();
        placeQueens(chessBoard, 0);
        print();
    }

    private void print() {
        System.out.println("======================================================");
        for (int i = 0; i < SIZE; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < SIZE; j++) {
                builder.append(chessBoard[i][j] ? "X" : "0");
            }
            System.out.println(builder.toString());
        }
    }

    private boolean placeQueens(boolean[][] chessBoard, int y) {

        if (y > SIZE - 1) {
            return true;
        }

        boolean isDone = false;
        for (int i = SIZE - 1; i >= 0; i--) {
            if (isSafe(chessBoard, i, y)) {
                chessBoard[i][y] = true;
                isDone = placeQueens(chessBoard, y + 1);
                if (isDone) {
                    break;
                } else {
                    chessBoard[i][y] = false;
                }
            }
        }
        return isDone;

    }

    private boolean isSafeCell(boolean[][] chessBoard, int x, int y) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            return true;
        } else {
            return !chessBoard[x][y];
        }

    }

    private boolean isSafe(boolean[][] chessBoard, int x, int y) {
        boolean isRingFenced = isSafeCell(chessBoard, x, y + 1)
                && isSafeCell(chessBoard, x, y - 1)
                && isSafeCell(chessBoard, x - 1, y - 1)
                && isSafeCell(chessBoard, x - 1, y)
                && isSafeCell(chessBoard, x - 1, y + 1)
                && isSafeCell(chessBoard, x + 1, y - 1)
                && isSafeCell(chessBoard, x + 1, y)
                && isSafeCell(chessBoard, x + 1, y + 1);

        return isRingFenced && isNoneInSight(chessBoard, x, y) && isNoneDiagonally(chessBoard, x, y);

    }

    private boolean isNoneInSight(boolean[][] chessBoard, int x, int y) {

        boolean isNoneInSight = true;
        for (int i = 0; i < SIZE; i++) {
            if (i == y) {
                continue;
            }
            isNoneInSight = isNoneInSight && !chessBoard[x][i];
        }
        return isNoneInSight;
    }


    private boolean isNoneDiagonally(boolean[][] chessBoard, int x, int y) {

        boolean isNoneInSight = true;
        for (int i = 0; i < SIZE; i++) {
            if (i == y && i == y) {
                continue;
            }
            isNoneInSight = isNoneInSight && !chessBoard[i][i];
        }

        for (int i = SIZE - 1; i >= 0; i--) {
            if (i == y && i == y) {
                continue;
            }
            isNoneInSight = isNoneInSight && !chessBoard[i][SIZE - 1 - i];
        }

        return isNoneInSight;
    }
}
