package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

public class MobilePattern {

    public static void main(String[] args) {

        MobilePatternSolution solution = new MobilePatternSolution();
        System.out.println(solution.numberOfPatterns(1, 1));
        System.out.println(solution.numberOfPatterns(1, 2));
    }
}


class MobilePatternSolution {

    TreeMap<String, Integer> map = new TreeMap<>();
    Set<String> patterns = new HashSet<>();
    int num = 0;

    public int numberOfPatterns(int m, int n) {
        int[][] grid = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        map.put("1-3", 2);
        map.put("3-1", 2);
        map.put("1-9", 5);
        map.put("9-1", 5);
        map.put("1-7", 4);
        map.put("7-1", 4);
        map.put("7-9", 8);
        map.put("9-7", 8);
        map.put("9-3", 6);
        map.put("3-9", 6);
        map.put("7-3", 5);
        map.put("3-7", 5);
        map.put("4-6", 5);
        map.put("6-4", 5);
        map.put("2-8", 5);
        map.put("8-2", 5);
        traverse(grid, new Stack<Cell>(), new HashSet<Integer>(), m, n);
        return patterns.size();
    }

    private void traverse(int[][] grid, Stack<Cell> pattern, Set<Integer> seen, int m, int n) {
        if (pattern.size() >= m && pattern.size() <= n) {
            String pat = toStr(grid, pattern);
            //System.out.println(pat);
            patterns.add(pat);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = pattern.isEmpty() ? null : pattern.peek();
                if (cell != null && seen.contains(grid[i][j])) {
                    continue;
                }
                if (cell == null || isNeighBour(cell.x, cell.y, i, j) || (cell != null && isConnectingDotSeen(cell.x, cell.y, i, j, grid, seen))) {
                    pattern.push(new Cell(i, j));
                    seen.add(grid[i][j]);
                    traverse(grid, pattern, seen, m, n);
                    pattern.pop();
                    seen.remove(grid[i][j]);
                }

            }
        }

    }

    private String toStr(int[][] grid, Stack<Cell> pattern) {
        List<Integer> results = Arrays.stream(pattern.toArray()).map(o -> {
            return grid[((Cell) o).x][((Cell) o).y];
        }).collect(Collectors.toList());
        String result = "";
        for (Integer i : results) {
            result += i;
        }
        return result;
    }

    private boolean isNeighBour(int x, int y, int i, int j) {
        return Math.abs(x - i) <= 1 && Math.abs(i - j) <= 1;
    }

    class Cell {
        int x, y;

        public Cell(int a, int b) {
            x = a;
            y = b;
        }
    }

    private boolean isConnectingDotSeen(int x, int y, int a, int b, int[][] grid, Set<Integer> seen) {
        boolean isSeen = false;
        String key = grid[x][y] + "-" + grid[a][b];
        return seen.contains(map.get(key));

    }

    private Stack<Integer> copyOf(Stack<Integer> in) {
        Stack<Integer> out = new Stack<Integer>();
        out.addAll(in);
        return out;
    }
}