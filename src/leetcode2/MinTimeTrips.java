package leetcode2;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MinTimeTrips {
    public static void main(String[] args) {
        MinTimeTrips minTimeTrips = new MinTimeTrips();
        minTimeTrips.minimumTime(new int[]{1, 2, 3}, 5);
    }

    public long minimumTime(int[] time, int totalTrips) {
        int n = time.length;
        //     Arrays.sort(time);
        long low = 1, high = Arrays.stream(time).max().getAsInt();
        System.out.println(high);
        long ans = 0;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (isPossible(time, totalTrips, mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    private boolean isPossible(int[] arr, int trips, long mid) {
        long poss = 0;
        for (int x : arr) {
            poss += mid / x;
        }
        if (poss >= trips) return true;
        return false;
    }
}