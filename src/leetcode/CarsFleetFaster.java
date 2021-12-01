package leetcode;


import java.util.*;

public class CarsFleetFaster {

    static int target = 12;

    static int[] position = new int[]{10, 8, 0, 5, 3};

    static Map<Integer, List<Integer>> posToCars = new HashMap<Integer, List<Integer>>();

    static int[] speed = new int[]{2, 4, 1, 1, 3};

    static Set<Integer> arrivedOrCaughtUp = new HashSet<Integer>();

    public static void main(String[] args) {
        for (int car = 0; car < position.length; car++) {
            int pos = position[car];
            while (pos <= target) {
                pos += speed[car];
                if (pos > target) {
                    pos = target;
                }
                posToCars.putIfAbsent(pos, new ArrayList<Integer>());
                posToCars.get(pos).add(car);
            }
        }


        System.out.println(posToCars.size());

    }
}
