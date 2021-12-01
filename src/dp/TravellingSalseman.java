package dp;

import java.util.*;

public class TravellingSalseman {

    public int calculate(int[][] cities, int start) {
        Map<Integer, List<Integer>> citiesMap = new HashMap<>();
        Map<String, Integer> costMap = new HashMap<>();
        for (int i = 0; i < cities.length; i++) {
            citiesMap.putIfAbsent(cities[i][0], new ArrayList<>());
            citiesMap.get(cities[i][0]).add(cities[i][1]);
            costMap.put(cities[i][0] + "-" + cities[i][1], cities[i][2]);
        }
        int minCost = Integer.MAX_VALUE;
        for (Integer nextCity : citiesMap.get(start)) {
            minCost = Math.min(minCost, costMap.get(start + "-" + nextCity)
                    + calculateMinCostFromHere(nextCity, citiesMap, costMap, Arrays.asList(new Integer[]{start})));
        }

        return minCost;

    }

    private int calculateMinCostFromHere(Integer start, Map<Integer, List<Integer>> citiesMap, Map<String, Integer> costMap, List<Integer> exclusionList) {
        List<Integer> cities = new ArrayList<>();
        cities.addAll(citiesMap.get(start));
        cities.retainAll(exclusionList);
        int minCost = Integer.MAX_VALUE;
        for (Integer nextCity : cities) {
            List<Integer> newExclusionList = new ArrayList<>();
            newExclusionList.addAll(exclusionList);
            newExclusionList.add(nextCity);
            minCost = Math.min(minCost, costMap.get(start + "-" + nextCity)
                    + calculateMinCostFromHere(nextCity, citiesMap, costMap, newExclusionList));
        }

        return minCost;
    }
}
