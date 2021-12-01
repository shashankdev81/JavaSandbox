package dp;

import java.util.HashMap;
import java.util.Map;

public class LIS {

    public static void main(String[] args) {
        LIS lis = new LIS();
        Map<String, Integer> cache = new HashMap<>();
        System.out.println(lis.getLIS(cache, new int[]{2, 5, 1, 7, 3, 8}, 0, 0));
        cache = new HashMap<>();
        System.out.println(lis.getLIS(cache, new int[]{1, 2, 3, 4, 5}, 0, 0));
        cache = new HashMap<>();
        System.out.println(lis.getLIS(cache, new int[]{5, 4, 3, 2, 1}, 0, 0));
        cache = new HashMap<>();
        System.out.println(lis.getLIS(cache, new int[]{5}, 0, 0));
        cache = new HashMap<>();
        System.out.println(lis.getLIS(cache, new int[]{}, 0, 0));


    }

    private int getLIS(Map<String, Integer> cache, int[] arr, int inCurr, int g) {
        int curr = inCurr;
        if (cache.containsKey(inCurr + "," + g)) {
            return cache.get(inCurr + "," + g);
        }
        if (curr >= arr.length) {
            return 0;
        }
        int count = 0;
        int bound = g;
        while (curr < arr.length && arr[curr] >= bound) {
            count++;
            bound = arr[curr];
            curr++;
        }
        if (curr == arr.length) {
            cache.putIfAbsent(inCurr + "," + g, count);
            return count;
        }
        int lis = Math.max((count + getLIS(cache, arr, curr + 1, bound)), (1 + getLIS(cache, arr, curr + 1, arr[curr])));
        cache.putIfAbsent(inCurr + "," + g, lis);
        return lis;
    }
}
