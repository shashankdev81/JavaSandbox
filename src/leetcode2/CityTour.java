package leetcode2;

import java.util.ArrayList;
import java.util.List;

public class CityTour {

    long fuel;

    public static void main(String[] args) {
        CityTour cityTour = new CityTour();
        cityTour.minimumFuelCost(new int[][]{{3, 1}, {3, 2}, {1, 0}, {0, 4}, {0, 5}, {4, 6}}, 2);
    }

    public long minimumFuelCost(int[][] roads, int seats) {
        if (roads.length == 0) return 0;
        fuel = 0;
        List<List<Integer>> ls = new ArrayList<>();
        for (int i = 0; i <= roads.length; i++) ls.add(new ArrayList<>());
        for (int road[] : roads) {
            ls.get(road[0]).add(road[1]);
            ls.get(road[1]).add(road[0]);
        }
        boolean vis[] = new boolean[roads.length + 1];
        dfs(0, roads.length, ls, seats, vis);
        return fuel;
    }

    private long dfs(int city, int n, List<List<Integer>> ls, int seats, boolean[] vis) {
        if (city > n || vis[city]) return 0;
        vis[city] = true;
        int nodes = 0;
        for (int x : ls.get(city)) {
            nodes += dfs(x, n, ls, seats, vis);
        }
        nodes++;
        if (city != 0)
            fuel += nodes % seats == 0 ? nodes / seats : nodes / seats + 1;
        return nodes;
    }
}