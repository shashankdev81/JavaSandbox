package backtracking;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 *
 * 1. Divide 9*9 grid into sub-grids of 3*3
 * 2.
 *
 * */
public class Sudoku {

    private int BOARD_MAX = 4;

    private int GRID_SIZE = BOARD_MAX / 2;

    private Map<Integer, Set<Integer>> rowMap = new HashMap<Integer, Set<Integer>>();

    private Map<Integer, Set<Integer>> colMap = new HashMap<Integer, Set<Integer>>();

    private Set[][] grid = new Set[GRID_SIZE][GRID_SIZE];

    private int[][] board = new int[BOARD_MAX][BOARD_MAX];

    public Sudoku() {

        for (int c = 0; c < BOARD_MAX; c++) {
            rowMap.put(c, new HashSet<Integer>());
            colMap.put(c, new HashSet<Integer>());
        }

        for (int j = 0; j < GRID_SIZE; j++) {
            for (int k = 0; k < GRID_SIZE; k++) {
                grid[j][k] = new HashSet<Integer>();
            }
        }
    }


    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
        sudoku.execute();
    }

    public void execute() {
        print();
        sudoku(0, 0);
        print();
    }


    private boolean sudoku(int i, int j) {

        boolean isDone = false;
        print();

        for (int k = 1; k <= BOARD_MAX; k++) {
            if (isNotInRow(i, k) && isNotInCol(j, k) && isNotInGrid(i, j, k)) {
                addNum(i, j, k);
                if (j < BOARD_MAX - 1) {
                    isDone = sudoku(i, j + 1);
                } else if (i < BOARD_MAX - 1) {
                    isDone = sudoku(i + 1, 0);
                } else {
                    isDone = true;
                }
                if (isDone) {
                    break;
                } else {
                    removeNum(i, j, k);
                }

            }
        }
        return isDone;

    }

    private void removeNum(int i, int j, int k) {
        board[i][j] = 0;
        rowMap.get(i).remove(k);
        colMap.get(j).remove(k);
        grid[i / GRID_SIZE][j / GRID_SIZE].remove(k);
    }

    private void addNum(int i, int j, int k) {
        board[i][j] = k;
        rowMap.get(i).add(k);
        colMap.get(j).add(k);
        grid[i / GRID_SIZE][j / GRID_SIZE].add(k);
    }


    private void print() {
        System.out.println("======================================================");
        for (int i = 0; i < BOARD_MAX; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < BOARD_MAX; j++) {
                builder.append(board[i][j]).append("|");
            }
            System.out.println(builder.toString());
        }
    }

    private boolean isNotInRow(int row, int x) {
        boolean isNotInRow = !rowMap.get(row).contains(x);
        return isNotInRow;
    }

    private boolean isNotInCol(int col, int x) {
        boolean isNotInCol = !colMap.get(col).contains(x);
        return isNotInCol;
    }

    private boolean isNotInGrid(int x, int y, int num) {
        int row = x / GRID_SIZE;
        int col = y / GRID_SIZE;
        boolean isNotIngrid = !grid[row][col].contains(num);
        return isNotIngrid;
    }

}
