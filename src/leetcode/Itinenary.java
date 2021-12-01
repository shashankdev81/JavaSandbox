package leetcode;


import java.util.*;
import java.util.stream.Collectors;

public class Itinenary {


    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, List<String>> itin = new HashMap<String, List<String>>();

        for (List<String> it : tickets) {
            itin.putIfAbsent(it.get(0), new ArrayList<>());
            itin.get(it.get(0)).add(it.get(1));
        }
        for (List<String> it : itin.values()) {
            Collections.sort(it);
        }
        /*Why is this a deque?
         * 1. Duplicates allowed
         * 2. Add and remove from tail
         * 3. Remove only last added and not by city or id
         * */
        Deque<String> visited = new ArrayDeque<>();
        String start = "JFK";
        visited.add(start);
        travel(start, itin, visited);
        return visited.stream().collect(Collectors.toList());

    }

    private boolean travel(String curr, Map<String, List<String>> itin, Deque<String> visited) {
        List<String> cities = itin.get(curr);

        //if there are no more cities then you should have exhausted the entire itinenary
        if (cities == null || cities.isEmpty()) {
            return isDone(itin);
        }

        Set<String> clonedCities = new TreeSet<String>();
        clonedCities.addAll(cities);

        for (String city : clonedCities) {
            //if current leg of itinenary has been exhausted then return
            if (cities.isEmpty()) {
                return isDone(itin);
            }
            cities.remove(city);
            visited.add(city);
            if (!travel(city, itin, visited)) {
                visited.pollLast();
                cities.add(city);
            }
        }
        //if current leg of itinenary has been exhausted then return
        return isDone(itin);
    }

    private boolean isDone(Map<String, List<String>> itin) {
        return itin.values().stream().allMatch(s -> s.isEmpty());
    }


    public static void main(String[] args) {
        Itinenary itin = new Itinenary();
        String[][] itArray = new String[][]{{"EZE", "TIA"}, {"EZE", "HBA"}, {"AXA", "TIA"}, {"JFK", "AXA"}, {"ANU", "JFK"}, {"ADL", "ANU"}, {"TIA", "AUA"}, {"ANU", "AUA"}, {"ADL", "EZE"}, {"ADL", "EZE"}, {"EZE", "ADL"}, {"AXA", "EZE"}, {"AUA", "AXA"}, {"JFK", "AXA"}, {"AXA", "AUA"}, {"AUA", "ADL"}, {"ANU", "EZE"}, {"TIA", "ADL"}, {"EZE", "ANU"}, {"AUA", "ANU"}};
        System.out.println(itin.findItinerary(Arrays.stream(itArray).map(a -> Arrays.stream(a).collect(Collectors.toList())).collect(Collectors.toList())));
    }
}
