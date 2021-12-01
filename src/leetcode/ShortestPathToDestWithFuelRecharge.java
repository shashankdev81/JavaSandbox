package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class ShortestPathToDestWithFuelRecharge {


    private int minCost = Integer.MAX_VALUE;

    private Map<Integer, Point> intToPointMap = new HashMap<>();

    private class Point {
        boolean isFuelStation;
        int value;
        int visitNo = 0;
        int capacityDuringLastVisit = 0;


        public Point(int value) {
            this.value = value;
        }

        public Point(boolean isFuelStation, int value) {
            this.isFuelStation = isFuelStation;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return value == point.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    private int MAX_TANK_CAPACITY = 0;


    public int getMinTime(int source, int destination, List<List<Integer>> edges, int tankCapacity, List<Integer> gasStations) {
        MAX_TANK_CAPACITY = tankCapacity;
        Map<Point, List<Point>> adjList = new HashMap<>();
        for (List<Integer> edge : edges) {
            Point a = new Point(gasStations.contains(edge.get(0)), edge.get(0));
            Point b = new Point(gasStations.contains(edge.get(1)), edge.get(1));
            intToPointMap.putIfAbsent(a.value, a);
            intToPointMap.putIfAbsent(b.value, b);

            adjList.putIfAbsent(intToPointMap.get(a.value), new ArrayList<Point>());
            adjList.putIfAbsent(intToPointMap.get(b.value), new ArrayList<Point>());
            adjList.get(a).add(intToPointMap.get(b.value));
            adjList.get(b).add(intToPointMap.get(a.value));
        }
        traverse(1, 7, adjList, 4, 0);
        return minCost;
    }

    private void traverse(int source, int dest, Map<Point, List<Point>> adjList, int tankCapacity, int cost) {
        if (tankCapacity <= 0) {
            return;
        }
        if (source == dest) {
            minCost = Math.min(minCost, cost);
            return;
        }

        intToPointMap.get(source).visitNo++;
        intToPointMap.get(source).capacityDuringLastVisit = tankCapacity;
        for (Point point : adjList.get(new Point(source))) {
            if (isTraversible(tankCapacity, point)) {
                int currTankCapacity = intToPointMap.get(point.value).isFuelStation ? MAX_TANK_CAPACITY : tankCapacity - 1;
                traverse(point.value, dest, adjList, currTankCapacity, cost + 1);
            }
        }

        intToPointMap.get(source).visitNo--;

    }

    private boolean isTraversible(int tankCapacity, Point point) {
        return (point.capacityDuringLastVisit < tankCapacity) &&
                ((point.isFuelStation && point.visitNo <= 1) || (!point.isFuelStation && point.visitNo <= 2));
    }

    public static void main(String[] args) {
        int[][] edges = new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 8}, {8, 9}, {9, 4}, {4, 6}, {6, 7}};
        ShortestPathToDestWithFuelRecharge problem = new ShortestPathToDestWithFuelRecharge();
        List<List<Integer>> edgesList = Arrays.stream(edges).map(e -> Arrays.stream(e).boxed().collect(Collectors.toList())).collect(Collectors.toList());
        System.out.println(problem.getMinTime(1, 7, edgesList, 4, Arrays.asList(new Integer[]{5})));
    }
}
