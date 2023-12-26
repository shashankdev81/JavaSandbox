package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MaxImportanceRoads {

    public static void main(String[] args) {
        MaxImportanceRoads maxImportanceRoads = new MaxImportanceRoads();
        System.out.println(maxImportanceRoads.maximumImportance(5,
            new int[][]{{0, 1}, {1, 2}, {2, 3}, {0, 2}, {1, 3}, {2, 4}}));
    }

    public long maximumImportance(int n, int[][] roads) {
        Map<Integer, City> cityFactory = new HashMap<>();
        for (int i = 0; i < n; i++) {
            cityFactory.put(i, new City(i, 0));
        }
        for (int j = 0; j < roads.length; j++) {
            int c1 = roads[j][0];
            int c2 = roads[j][1];
            cityFactory.get(c1).addCity(cityFactory.get(c2));
            cityFactory.get(c2).addCity(cityFactory.get(c1));
        }
        List<City> cities = cityFactory.values().stream().sorted().collect(Collectors.toList());
        int count = n;
        for (City city : cities) {
            city.newValue = count;
            count--;
        }
        int maxImp = 0;
        for (int j = 0; j < roads.length; j++) {
            int c1 = roads[j][0];
            int c2 = roads[j][1];
            maxImp += cityFactory.get(c1).newValue + cityFactory.get(c2).newValue;
        }
        System.out.println(cities);
        return maxImp;

    }

    private class City implements Comparable<City> {

        int val;
        int roads;
        int newValue;
        List<City> cities;

        public City(int val, int roads) {
            this.val = val;
            this.roads = roads;
            cities = new ArrayList<>();
        }

        public void addCity(City city) {
            cities.add(city);
            roads++;
        }

        public int hashCode() {
            return Objects.hash(val);
        }

        public boolean equals(Object obj) {
            if (obj == null || !this.getClass().equals(obj.getClass())) {
                return false;
            }
            City ext = (City) obj;
            return this.val == ext.val;
        }

        public int compareTo(City ext) {
            if (this.roads == ext.roads) {
                return ext.val - this.val;
            } else {
                return ext.roads - this.roads;
            }
        }

        public String toString() {
            return "val=" + val + ",roads=" + roads + ",newVal=" + newValue;
        }
    }
}