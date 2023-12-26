package leetcode2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

public class LeastFrequentlyUsed {

    private Map<Integer, Pair<Integer, Integer>> cache = new HashMap<>();

    private TreeMap<Integer, HashSet<Integer>> freqMap = new TreeMap<>();

    private int size;

    public static void main(String[] args) {
        LeastFrequentlyUsed leastFrequentlyUsed = new LeastFrequentlyUsed(2);

        leastFrequentlyUsed.put(2, 1);
        leastFrequentlyUsed.put(3, 2);
        System.out.println(leastFrequentlyUsed.get(3));
        System.out.println(leastFrequentlyUsed.get(2));
        leastFrequentlyUsed.put(4, 3);
        System.out.println(leastFrequentlyUsed.get(2));
        System.out.println(leastFrequentlyUsed.get(3));
        System.out.println(leastFrequentlyUsed.get(4));

    }


    public LeastFrequentlyUsed(int capacity) {
        this.size = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        int ret = cache.get(key).getValue();
        int freq = cache.get(key).getKey() + 1;
        freqMap.putIfAbsent(freq, new LinkedHashSet<>());
        freqMap.get(freq).add(Integer.valueOf(key));
        if (freqMap.containsKey(freq - 1)) {
            freqMap.get(freq - 1).remove(Integer.valueOf(key));
            if (freqMap.get(freq - 1).isEmpty()) {
                freqMap.remove(freq - 1);
            }
        }
        return ret;
    }

    public void put(int key, int value) {
        int freq = 1;
        if (cache.containsKey(key)) {
            freq += cache.get(key).getKey();
        } else if (cache.size() >= size) {
            Integer removed = freqMap.firstEntry().getValue().iterator().next();
            freqMap.firstEntry().getValue().remove(removed);
            if (freqMap.firstEntry().getValue().size() == 0) {
                freqMap.remove(freqMap.firstEntry().getKey());
            }
            PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.reverseOrder());


            cache.remove(removed);
        }
        cache.put(key, new Pair<Integer, Integer>(freq, value));
        freqMap.putIfAbsent(freq, new HashSet<>());
        freqMap.get(freq).add(Integer.valueOf(key));
        if (freqMap.containsKey(freq - 1)) {
            freqMap.get(freq - 1).remove(Integer.valueOf(key));
            if (freqMap.get(freq - 1).isEmpty()) {
                freqMap.remove(freq - 1);
            }
        }
    }


    class Pair<K, V> {

        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
