package graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TravellingSalesmanProblem {


    public static int tspRecursion(int[][] graph, int current, int[] visited,
        Map<String, Integer> memo) {
        if (Arrays.stream(visited).allMatch(v -> v == 1)) {
            return graph[current][0]; // Return to the starting city
        }

        String key = current + Arrays.toString(visited);

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        int minCost = Integer.MAX_VALUE;

        for (int nextCity = 0; nextCity < graph.length; nextCity++) {
            if (visited[nextCity] == 0) {
                visited[nextCity] = 1;
                int cost =
                    graph[current][nextCity] + tspRecursion(graph, nextCity, visited, memo);
                minCost = Math.min(minCost, cost);
                visited[nextCity] = 0;
            }
        }

        memo.put(key, minCost);
        return minCost;
    }

    public static int solveTSP(int[][] graph) {
        int numCities = graph.length;
        int[] visited = new int[numCities];
        visited[0] = 1; // Start from the first city

        // Initialize memoization map
        Map<String, Integer> memo = new HashMap<>();

        return tspRecursion(graph, 0, visited, memo);
    }

    public static void main(String[] args) {
        int[][] graph = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        System.out.println(solveTSP(graph));
    }
}
