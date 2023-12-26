package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CoinPath {

    public static void main(String[] args) {
        CoinPath coinPath = new CoinPath();
        System.out.println(coinPath.cheapestJump(new int[]{0,0,0,0,0,0}, 3));
    }

    public List<Integer> cheapestJump(int[] coins, int maxJump) {
        Pair<Integer, List<String>> minCost = cheapestJump(coins, maxJump, 0, "");
        System.out.println(minCost.getKey() + "," + minCost.getValue());
        List<String> sorted = minCost.getValue().stream().map(s -> s.substring(1, s.length()))
            .sorted().collect(Collectors.toList());
        System.out.println(sorted);
        if (sorted.isEmpty()) {
            return new ArrayList<>();
        }
        String[] arr = sorted.get(0).split(",");
        List<Integer> results = new ArrayList<>();
        for (String index : arr) {
            results.add(Integer.valueOf(index) + 1);
        }
        return results;
    }

    private Pair<Integer, List<String>> cheapestJump(int[] coins, int maxJump, int idx,
        String visited) {
        if (idx >= coins.length) {
            List<String> vs = new ArrayList<>();
            vs.add(visited);
            return new Pair<Integer, List<String>>(0, vs);
        }

        Pair<Integer, List<String>> minCost = new Pair(Integer.MAX_VALUE, new ArrayList<>());
        if (coins[idx] != -1) {
            for (int next = 1; next <= maxJump; next++) {
                Pair<Integer, List<String>> nextCost = cheapestJump(coins, maxJump, idx + next,
                    visited + "," + idx);
                int currCost = nextCost.getKey() == Integer.MAX_VALUE ? Integer.MAX_VALUE
                    : coins[idx] + nextCost.getKey();
                if (currCost < minCost.getKey()) {
                    minCost = new Pair(currCost, nextCost.getValue());
                } else if (currCost == minCost.getKey() && minCost.getKey() != Integer.MAX_VALUE) {
                    minCost.getValue().addAll(nextCost.getValue());
                }
            }
        }
        return minCost;
    }

    class Pair<K, V> {

        K key;
        V value;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}