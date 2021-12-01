package leetcode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class VideoGame {

    public int maxPlanes(int[] startHeight, int[] descentRate) {

        Map<Integer, Integer> arrivalTimeAndCounts = new HashMap<>();
        for (int i = 0; i < startHeight.length; i++) {
            int arrival = startHeight[i] / descentRate[i] + (startHeight[i] % descentRate[i] == 0 ? 0 : 1);
            arrivalTimeAndCounts.putIfAbsent(arrival, 0);
            arrivalTimeAndCounts.put(arrival, arrivalTimeAndCounts.get(arrival) + 1);
        }
        int maxKills = 0;
        int nextAvailable = 0;
        int lastArrivalTime = Collections.max(arrivalTimeAndCounts.keySet());
        for (int i = 1; i <= lastArrivalTime; i++) {

            while (arrivalTimeAndCounts.get(nextAvailable) == null) {
                nextAvailable++;
                if (nextAvailable > lastArrivalTime) {
                    return maxKills;
                }
            }

            Integer arrivings = arrivalTimeAndCounts.get(nextAvailable);
            maxKills++;
            arrivings--;
            arrivalTimeAndCounts.put(i, arrivings == 0 ? null : arrivings);
        }

        return maxKills;
    }

    public static void main(String[] args) {
        VideoGame game = new VideoGame();
        System.out.println(game.maxPlanes(new int[]{1, 3, 5, 4, 8}, new int[]{1, 2, 2, 1, 2}));
    }
}
