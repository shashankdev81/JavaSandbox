package leetcode2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class AppleCost {


    public static void main(String[] args) {
        AppleCost appleCost = new AppleCost();
        long[] costArr = appleCost.minCost(4,
            new int[][]{{1, 2, 4}, {2, 3, 2}, {2, 4, 5}, {3, 4, 1}, {1, 3, 4}},
            new int[]{56, 42, 102, 301}, 2);
        System.out.println(Arrays.stream(costArr).boxed().collect(Collectors.toList()));
    }

    private Map<Integer, Map<Integer, Integer>> costMap = new HashMap<>();

    public long[] minCost(int n, int[][] roads, int[] appleCost, int k) {
        for (int i = 0; i < roads.length; i++) {
            costMap.putIfAbsent(roads[i][0], new HashMap<>());
            costMap.putIfAbsent(roads[i][1], new HashMap<>());
            costMap.get(roads[i][0]).put(roads[i][1], roads[i][2]);
            costMap.get(roads[i][1]).put(roads[i][0], roads[i][2]);
        }
        long[] minCostPerCity = new long[n];
        Arrays.fill(minCostPerCity, Long.MAX_VALUE);
        for (Integer city : costMap.keySet()) {
            minCostPerCity[city - 1] = Math.min(appleCost[city - 1],
                travel(city, costMap, appleCost, k));
        }
        return minCostPerCity;
    }

    private long travel(int startCity, Map<Integer, Map<Integer, Integer>> costMap, int[] appleCost,
        int k) {
        long cost = Long.MAX_VALUE;
        Queue<int[]> travelQueue = new ArrayDeque<>();
        travelQueue.offer(new int[]{startCity, 0});
        Set<Integer> visited = new HashSet<>();
        while (!travelQueue.isEmpty()) {
            int cities = travelQueue.size();
            while (cities > 0 && !travelQueue.isEmpty()) {
                int[] city = travelQueue.poll();
                if (visited.contains(city[0])) {
                    cities--;
                    continue;
                }
                visited.add(city[0]);
                for (Integer neighbouringCity : costMap.get(city[0]).keySet()) {
                    int commuteCost = costMap.get(city[0]).get(neighbouringCity) * (k + 1);
                    // System.out.println(Arrays.toString(appleCost));
                    // System.out.println(costMap);
                    // System.out.println(city[0]+","+neighbouringCity);
                    int appleCostInCity = appleCost[neighbouringCity - 1];
                    int accCommuteCostFromParent = city[1];
                    cost = Math.min(cost, commuteCost + appleCostInCity + accCommuteCostFromParent);
                    travelQueue.offer(new int[]{neighbouringCity, commuteCost});

                }
            }
            cities--;
        }
        return cost;
    }
}