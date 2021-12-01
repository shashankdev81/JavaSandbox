package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class IsASquareSimpler {


    private class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        if (!isValid(p1, p2, p3, p4)) {
            return false;
        }
        Map<Integer, Integer> sidesAndDiagonals = new HashMap<Integer, Integer>();
        int[] distances = new int[6];
        distances[0] = getDistSq(p1[0], p2[0], p1[1], p2[1]);
        distances[1] = getDistSq(p2[0], p3[0], p2[1], p3[1]);
        distances[2] = getDistSq(p3[0], p4[0], p3[1], p4[1]);
        distances[3] = getDistSq(p4[0], p1[0], p4[1], p1[1]);
        distances[4] = getDistSq(p1[0], p3[0], p1[1], p3[1]);
        distances[5] = getDistSq(p2[0], p4[0], p2[1], p4[1]);

        for (int i = 0; i < 6; i++) {
            sidesAndDiagonals.putIfAbsent(distances[i], 0);
            sidesAndDiagonals.put(distances[i], sidesAndDiagonals.get(distances[i]) + 1);
        }

        List<Integer> sides = sidesAndDiagonals.values().stream().filter(e -> e == 4).collect(Collectors.toList());
        List<Integer> diagonals = sidesAndDiagonals.values().stream().filter(e -> e == 2).collect(Collectors.toList());

        return sidesAndDiagonals.size() == 2 && !sides.isEmpty() && !diagonals.isEmpty();
    }

    private int getDistSq(int x1, int x2, int y1, int y2) {
        return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
    }


    private boolean isValid(int[] p1, int[] p2, int[] p3, int[] p4) {
        Set<Point> uniquePoints = new HashSet<Point>();
        uniquePoints.add(new Point(p1[0], p1[1]));
        uniquePoints.add(new Point(p2[0], p2[1]));
        uniquePoints.add(new Point(p3[0], p3[1]));
        uniquePoints.add(new Point(p4[0], p4[1]));

        return uniquePoints.size() == 4;
    }

    public static void main(String[] args) {
        IsASquareSimpler isASquare = new IsASquareSimpler();
        System.out.println(isASquare.validSquare(new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1}));
        System.out.println(isASquare.validSquare(new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 0}, new int[]{0, 12}));
        System.out.println(isASquare.validSquare(new int[]{1, 1}, new int[]{5, 3}, new int[]{3, 5}, new int[]{7, 7}));
        System.out.println(isASquare.validSquare(new int[]{0, 0}, new int[]{1, 1}, new int[]{0, 0}, new int[]{1, 1}));
    }
}
