package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

public class MinCities {


    private Map<Integer, City> idToCityMap = new HashMap<>();

    public static void main(String[] args) {
        MinCities minCities = new MinCities();
        minCities.findTheCity(4, new int[][]{{0, 1, 3}, {1, 2, 1}, {1, 3, 4}, {2, 3, 1}}, 4);
    }

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        for (int i = 0; i < edges.length; i++) {
            int id1 = edges[i][0];
            int id2 = edges[i][1];
            idToCityMap.putIfAbsent(id1, new City(id1));
            idToCityMap.putIfAbsent(id1, new City(id2));
            idToCityMap.get(id1).addNeighbour(id2, edges[i][2]);
            idToCityMap.get(id2).addNeighbour(id1, edges[i][2]);
        }

        Map<Integer, List<City>> reachablesMap = new HashMap<>();

        for (City city : idToCityMap.values()) {
            Set<City> visited = new HashSet<>();
            visited.add(city);
            reachablesMap.put(city.id, maxReachables(city, distanceThreshold, visited));
            System.out.println("reachablesMap=" + city.id + "," + reachablesMap.get(city.id).size());
        }

        int min = Integer.MAX_VALUE;
        int minCity = -1;
        for (Map.Entry<Integer, List<City>> entry : reachablesMap.entrySet()) {
            if (entry.getValue().size() <= min) {
                minCity = entry.getKey();
                min = entry.getValue().size();
            }
        }

        return minCity;

    }

    private List<City> maxReachables(City city, int maxDist, Set<City> visited) {
        Set<City> reachables = new HashSet<>();
        for (City neighbour : city.neighbours.keySet()) {
            if (!visited.contains(neighbour) && maxDist - city.neighbours.get(neighbour) >= 0) {
                visited.add(neighbour);
                reachables.add(neighbour);
                reachables.addAll(maxReachables(neighbour, maxDist - city.neighbours.get(neighbour), visited));
                visited.remove(neighbour);
            }
        }
        return reachables.stream().map(c -> c).collect(Collectors.toList());
    }


    public class City {
        int id;
        Map<City, Integer> neighbours;

        public City(int i) {
            id = i;
            neighbours = new HashMap<>();
        }

        public void addNeighbour(int nid, int dist) {
            idToCityMap.putIfAbsent(nid, new City(nid));
            neighbours.put(idToCityMap.get(nid), dist);
        }

        public boolean equals(Object obj) {
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            City ext = (City) obj;
            return id == ext.id;
        }

        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
