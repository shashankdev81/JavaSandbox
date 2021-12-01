package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class CityTravelItinenary {

    private Map<String, List<String>> cityMap = new HashMap<String, List<String>>();

    private Map<String, Integer> costMap = new HashMap<>();

    public CityTravelItinenary() {


        cityMap.putIfAbsent("AAA", new ArrayList<>());
        cityMap.putIfAbsent("XBB", new ArrayList<>());
        cityMap.putIfAbsent("CCC", new ArrayList<>());
        cityMap.putIfAbsent("DDY", new ArrayList<>());

        cityMap.get("AAA").add("XBB");
        cityMap.get("XBB").add("CCC");
        cityMap.get("CCC").add("DDY");
        cityMap.get("DDY").add("AAA");

    }


    public int getValidationCost(String[] cities) {

        int cost = Integer.MAX_VALUE;
        List<String> travelled = Arrays.stream(cities).collect(Collectors.toList());
        String start = travelled.get(0);
        for (String city : cityMap.keySet()) {
            if (cityMap.get(start) != null) {
                cost = Math.min(cost, getCost(start, travelled.subList(1, travelled.size())));
            } else {
                if (getEditDistance(city, start) <= 1) {
                    cost = Math.min(cost, 1 + getCost(start, travelled.subList(1, travelled.size())));
                }
            }

        }

        return cost;
    }

    private int getCost(String start, List<String> travelled) {
        if (travelled.isEmpty()) {
            return 0;
        }
        String nextCityInItin = travelled.get(0);

        int cost = Integer.MAX_VALUE;
        String match = null;
        if (cityMap.get(start).contains(nextCityInItin)) {
            match = nextCityInItin;
            cost = 0;
        } else {
            for (String actualCity : cityMap.get(start)) {
                cost = Math.min(cost, getEditDistance(nextCityInItin, actualCity));
                if (cost <= 1) {
                    match = actualCity;
                    break;
                }
            }
        }
        if (cost > 1) {
            return Integer.MAX_VALUE;
        }
        int res = cost + getCost(match, travelled.subList(1, travelled.size()));
        //costMap.putIfAbsent(start+",")
        return res;

    }

    private int getEditDistance(String travelled, String actual) {
        int dist = 0;
        for (int i = 0; i < travelled.length(); i++) {
            if (travelled.charAt(i) == actual.charAt(i)) {
                dist++;
            }
        }
        return travelled.length() - dist;
    }


    public static void main(String[] args) {
        CityTravelItinenary prob = new CityTravelItinenary();
        System.out.println(prob.getValidationCost(new String[]{"AAA", "BBB", "CCC", "DDD"}));
    }

}
