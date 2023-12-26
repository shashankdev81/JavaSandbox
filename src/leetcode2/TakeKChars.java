package leetcode2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TakeKChars {


    public static void main(String[] args) {
        TakeKChars takeKChars = new TakeKChars();
        System.out.println(takeKChars.takeCharacters("a", 0));
    }

    public int takeCharacters(String s, int k) {
        Map<Character, Integer> countMap = new HashMap<Character, Integer>();
        countMap.put('a', 0);
        countMap.put('b', 0);
        countMap.put('c', 0);
        Set<Integer> cheeseEaten = new HashSet<>();


        if (k == 0) {
            return 0;
        }
        int time = take(0, s.length() - 1, s, k, countMap);
        return time <= 0 ? -1 : time;
    }

    private int take(int i, int j, String s, int k, Map<Character, Integer> countMap) {
        if (i > j && countMap.entrySet().stream().anyMatch(e -> e.getValue() < k)) {
            return Integer.MIN_VALUE;
        }
        if (i > j || countMap.entrySet().stream().allMatch(e -> e.getValue() >= k)) {
            return 0;
        }
        char c1 = s.charAt(i);
        char c2 = s.charAt(j);
        int time1 = 0;
        int time2 = 0;
        boolean isAdded = false;
        //System.out.println(countMap);
        countMap.put(c1, countMap.get(c1) + 1);
        time1 = 1 + take(i + 1, j, s, k, countMap);
        countMap.put(c1, countMap.get(c1) - 1);
        countMap.put(c2, countMap.get(c2) + 1);
        time2 = 1 + take(i, j - 1, s, k, countMap);
        countMap.put(c2, countMap.get(c2) - 1);
        return Math.min(time1, time2);

    }

}