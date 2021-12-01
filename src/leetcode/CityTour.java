package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class CityTour {

    private class City {
        private int id;
        private String name;
        private List<City> neighbours;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            City city = (City) o;
            return id == city.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        public City(int id, String name) {
            this.id = id;
            this.name = name;
            this.neighbours = new ArrayList<>();
        }

        public List<List<Integer>> getPathToTarget(int target, int hops, String path) {
            List<List<Integer>> result = new ArrayList<>();
            if (hops == 1 && id == target) {
                String finalPath = path + id;
                result.add(getList(finalPath));
                return result;
            }
            if (hops <= 0) {
                result.add(getList(path.substring(0, path.length() - 1)));
                return result;
            }
            for (City city : neighbours) {
                result.addAll(city.getPathToTarget(target, hops - 1, path + id + ","));
            }
            return result;
        }

        public List<List<Integer>> getPathToTargetMemoized(int target, int hops) {
            String key = id + "," + target + "," + hops;
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            List<List<Integer>> result = new LinkedList<>();
            if (hops == 1 && id == target) {
                result.add(Arrays.asList(new Integer[]{id}));
                return result;
            }
            if (hops <= 0) {
                result.add(Arrays.asList(new Integer[]{id}));
                return result;
            }
            for (City city : neighbours) {
                List<List<Integer>> partialResults = city.getPathToTargetMemoized(target, hops - 1);
                if (partialResults.isEmpty()) {
                    List<Integer> fullResult = new ArrayList<>();
                    fullResult.add(id);
                    result.add(fullResult);
                    return result;
                }
                for (List<Integer> partialResult : partialResults) {
                    List<Integer> fullResult = new LinkedList<>();
                    fullResult.add(id);
                    fullResult.addAll(partialResult);
                    result.add(fullResult);
                }
            }
            cache.putIfAbsent(key, result);
            return result;
        }

        private List getList(String finalPath) {
            return Arrays.stream(finalPath.split(",")).map(str -> Integer.valueOf(str)).collect(Collectors.toList());
        }
    }

    private Map<Integer, City> cityMap = new HashMap<Integer, City>();

    private Map<String, List<Integer>> namesMap = new HashMap<>();

    private Map<String, List<List<Integer>>> cache = new HashMap<>();

    public List<Integer> mostSimilar(int n, int[][] roads, String[] names, String[] targetPath) {
        for (int i = 0; i < roads.length; i++) {
            int c1 = roads[i][0];
            int c2 = roads[i][1];
            cityMap.putIfAbsent(c1, new City(c1, names[c1]));
            cityMap.putIfAbsent(c2, new City(c2, names[c2]));
            City city1 = cityMap.get(c1);
            City city2 = cityMap.get(c2);
            city1.neighbours.add(city2);
            city2.neighbours.add(city1);

        }
        for (int i = 0; i < names.length; i++) {
            namesMap.putIfAbsent(names[i], new ArrayList<>());
            namesMap.get(names[i]).add(i);
        }
        List<List<Integer>> result = new ArrayList<>();

        List<Integer> starts = getCityIndex(targetPath[0]);
        List<Integer> destinations = getCityIndex(targetPath[targetPath.length - 1]);

        for (Integer start : starts) {
            for (Integer dest : destinations) {
                result.addAll(cityMap.get(start).getPathToTargetMemoized(dest, targetPath.length));
                //result.addAll(cityMap.get(start).getPathToTarget(dest, targetPath.length, ""));

            }
        }
        int minEditDist = Integer.MAX_VALUE;
        List<Integer> minPathList = null;
        for (List<Integer> path : result) {
            int editDist = getEditDistance(names, path, targetPath);
            if (minEditDist > editDist) {
                minEditDist = editDist;
                minPathList = path;
            }
        }
        return minPathList;
    }

    private List<Integer> getCityIndex(String city) {
        if (namesMap.containsKey(city)) {
            return namesMap.get(city);
        } else {
            return namesMap.values().stream().flatMap(l -> l.stream()).collect(Collectors.toList());
        }
    }

    private Integer getEditDistance(String[] names, List<Integer> list, String[] targetPath) {
        String[] pathNames = list.stream().map(i -> names[i]).toArray(String[]::new);
        int dist = 0;
        for (int i = 0; i < pathNames.length; i++) {
            if (!pathNames[i].equalsIgnoreCase(targetPath[i])) {
                dist++;
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        CityTour cityTour = new CityTour();
//        System.out.println(cityTour.mostSimilar(5, new int[][]{{0, 2}, {0, 3}, {1, 2}, {1, 3}, {1, 4}, {2, 4}},
//                new String[]{"ATL", "PEK", "LAX", "DXB", "HND"}, new String[]{"ATL", "DXB", "HND", "LAX"}));
//        System.out.println(cityTour.mostSimilar(4, new int[][]{{1, 0}, {2, 0}, {3, 0}, {2, 1}, {3, 1}, {3, 2}},
//                new String[]{"ATL", "PEK", "LAX", "DXB"}, new String[]{"ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWX"}));
//        System.out.println(cityTour.mostSimilar(2, new int[][]{{0, 1}},
//                new String[]{"EVV", "PLS"}, new String[]{"EVV", "EVV", "EVV", "PLS", "PLS", "EVV", "EVV", "PLS", "PLS", "PLS", "PLS", "PLS", "EVV", "EVV", "PLS"}));
        String[] names = new String[]{"BJS", "BJS", "BJS"};
        String[] path = new String[]{"BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS", "BJS"};
        System.out.println(cityTour.mostSimilar(3, new int[][]{{0, 1}, {1, 2}}, names, path));


    }
}
