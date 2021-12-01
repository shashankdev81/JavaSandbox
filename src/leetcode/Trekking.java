package leetcode;

import java.util.*;

//https://leetcode.com/problems/path-with-minimum-effort/
public class Trekking {


    private class Cell {
        private int row;
        private int col;

        public Cell(int r, int c) {
            this.row = r;
            this.col = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && col == cell.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }


    public int minimumEffortPath(int[][] heights) {
        Set<Cell> visited = new HashSet<Cell>();
        List<Integer> listOfMaxes = traverse(heights, 0, 0, heights.length - 1, heights[0].length - 1, visited, Integer.MIN_VALUE);
        listOfMaxes.remove(0);
        return Collections.min(listOfMaxes);
    }

    private List<Integer> traverse(int[][] heights, int r, int c, int destR, int destC, Set<Cell> visited, int maxSeenSoFar) {

        int maxSoFar = maxSeenSoFar;

        List<Integer> listOfMaxes = new ArrayList<Integer>();
        listOfMaxes.add(maxSoFar);
        if (r == destR && c == destC) {
            return listOfMaxes;
        }
        Cell thisCell = new Cell(r, c);
        visited.add(thisCell);


        if (c + 1 < heights[0].length && !visited.contains(new Cell(r, c + 1))) {
            if (Math.abs(heights[r][c + 1] - heights[r][c]) > maxSoFar) {
                maxSoFar = Math.abs(heights[r][c + 1] - heights[r][c]);
            }
            listOfMaxes.addAll(traverse(heights, r, c + 1, destR, destC, visited, maxSoFar));
        }


        if (c - 1 >= 0 && !visited.contains(new Cell(r, c - 1))) {
            if (Math.abs(heights[r][c - 1] - heights[r][c]) > maxSoFar) {
                maxSoFar = Math.abs(heights[r][c - 1] - heights[r][c]);
            }
            listOfMaxes.addAll(traverse(heights, r, c - 1, destR, destC, visited, maxSoFar));
        }

        if (r + 1 < heights.length && !visited.contains(new Cell(r + 1, c))) {
            if (Math.abs(heights[r + 1][c] - heights[r][c]) > maxSoFar) {
                maxSoFar = Math.abs(heights[r + 1][c] - heights[r][c]);
            }
            listOfMaxes.addAll(traverse(heights, r + 1, c, destR, destC, visited, maxSoFar));
        }

        if (r - 1 >= 0 && !visited.contains(new Cell(r - 1, c))) {
            if (Math.abs(heights[r - 1][c] - heights[r][c]) > maxSoFar) {
                maxSoFar = Math.abs(heights[r - 1][c] - heights[r][c]);
            }
            listOfMaxes.addAll(traverse(heights, r - 1, c, destR, destC, visited, maxSoFar));
        }

        visited.remove(thisCell);
        return listOfMaxes;
    }

    public static void main(String[] args) {
        int[][] heights1 = new int[][]{{1, 2, 2}, {3, 8, 2}, {5, 3, 5}};
        int[][] heights2 = new int[][]{{1, 2, 3}, {3, 8, 4}, {5, 3, 5}};
        int[][] heights3 = new int[][]{{1, 2, 1, 1, 1}, {1, 2, 1, 2, 1}, {1, 2, 1, 2, 1}, {1, 2, 1, 2, 1}, {1, 1, 1, 2, 1}};
        Trekking trekking = new Trekking();
        System.out.println(trekking.minimumEffortPath(heights1));
        System.out.println(trekking.minimumEffortPath(heights2));
        System.out.println(trekking.minimumEffortPath(heights3));

    }
}
