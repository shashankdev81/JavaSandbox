package leetcode;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//https://leetcode.com/problems/car-fleet/
public class CarsFleet {

    static int target = 12;

    static int[] position = new int[]{10, 8, 0, 5, 3};

    static int[] newPosition = new int[]{10, 8, 0, 5, 3};

    static Map<Integer, List<Integer>> currPosToCars = new HashMap<Integer, List<Integer>>();

    static int[] speed = new int[]{2, 4, 1, 1, 3};

    static Set<Integer> arrivedOrCaughtUp = new HashSet<Integer>();

    public static void main(String[] args) {
        int fleets = 0;
        while (true) {
            for (int car = 0; car < position.length; car++) {
                if (arrivedOrCaughtUp.contains(car)) {
                    continue;
                }
                newPosition[car] = newPosition[car] + speed[car];
                if (newPosition[car] >= target) {
                    arrivedOrCaughtUp.add(car);
                    newPosition[car] = target;
                }
                currPosToCars.putIfAbsent(newPosition[car], new ArrayList<Integer>());
                currPosToCars.get(newPosition[car]).add(car);

            }
            List<Map.Entry<Integer, List<Integer>>> caughtUpInLastRun = currPosToCars.entrySet()
                    .stream().filter(e -> e.getValue().size() > 1).collect(Collectors.toList());
            fleets += caughtUpInLastRun.size();
            currPosToCars.entrySet().removeAll(caughtUpInLastRun);
            fleets += currPosToCars.get(target) != null ? currPosToCars.get(target).size() : 0;
            currPosToCars.entrySet().remove(currPosToCars.get(target));
            arrivedOrCaughtUp.addAll(caughtUpInLastRun.stream().flatMap(e -> e.getValue().stream()).collect(Collectors.toList()));
            if (arrivedOrCaughtUp.size() == position.length) {
                break;
            }
        }
        System.out.println(fleets);

    }
}
