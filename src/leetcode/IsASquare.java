package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class IsASquare {

    private class Node {

        private Set<Node> edges;

        private int x;

        private int y;

        public Node(int[] p1) {
            x = p1[0];
            y = p1[1];
            edges = new HashSet<Node>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private Set<Node> points;

    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        if (!isValid(p1, p2, p3, p4)) {
            return false;
        }
        points = new HashSet<Node>();
        points.add(new Node(p1));
        points.add(new Node(p2));
        points.add(new Node(p3));
        points.add(new Node(p4));
        for (Node point : points) {
            point.edges.addAll(points.stream().filter(n -> !point.equals(n)).collect(Collectors.toList()));
        }
        for (Node point : points) {
            Set<Node> visited = new LinkedHashSet<Node>();
            if (isAllSidesEqual(point, point, visited, -1) && isDiagnalsEqual(visited)) {
                return true;
            }
        }
        return false;

    }

    private boolean isDiagnalsEqual(Set<Node> visited) {
        List<Node> visitedList = visited.stream().collect(Collectors.toList());
        int diag1 = getDistSq(visitedList.get(0), visitedList.get(2));
        int diag2 = getDistSq(visitedList.get(1), visitedList.get(3));
        return diag1 == diag2;
    }

    /*
     * Following invariants must hold true
     * 1. All sides must be equal (Rectangle is not square)
     * 2. Each pair of edge must be at 90 degrees (Rhombus is not square)
     * 3. The points can fall in any quadrants and square can be rotated to any degree (x and y cords cannot be used for invariants)
     * */

    private boolean isAllSidesEqual(Node point, Node target, Set<Node> visited, int dist) {
        visited.add(point);
        boolean isVisited = false;
        //if visited all nodes
        if (visited.size() == 4 && getDistSq(point, target) == dist) {
            return true;
        } else if (visited.size() != 4) {

            for (Node neighbour : point.edges) {
                int distSq = getDistSq(point, neighbour);
                //if dist of next edge is not equal to prev one or is alredady visited node
                if ((dist != -1 && distSq != dist) || visited.contains(neighbour)) {
                    continue;
                }
                isVisited = isAllSidesEqual(neighbour, target, visited, distSq);
                if (isVisited) {
                    return isVisited;
                }
            }

        }
        visited.remove(point);
        return isVisited;
    }

    private int getDistSq(Node point, Node edge) {
        return (edge.x - point.x) * (edge.x - point.x) + (edge.y - point.y) * (edge.y - point.y);
    }


    private boolean isValid(int[] p1, int[] p2, int[] p3, int[] p4) {
        return true;
    }

    public static void main(String[] args) {
        IsASquare isASquare = new IsASquare();
        System.out.println(isASquare.validSquare(new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1}));
        System.out.println(isASquare.validSquare(new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 0}, new int[]{0, 12}));
        System.out.println(isASquare.validSquare(new int[]{1, 1}, new int[]{5, 3}, new int[]{3, 5}, new int[]{7, 7}));

    }
}
