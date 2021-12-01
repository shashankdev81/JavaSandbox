package leetcode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TimeMap {

    private ConcurrentHashMap<String, List<Pair>> store;

    /**
     * Initialize your data structure here.
     */
    public TimeMap() {
        store = new ConcurrentHashMap<String, List<Pair>>();

    }

    public void set(String key, String value, int timestamp) {
        store.putIfAbsent(key, new LinkedList<Pair>());
        store.get(key).add(new Pair(timestamp, value));
    }

    public String get(String key, int timestamp) {
        store.putIfAbsent(key, new LinkedList<Pair>());
        List<Pair> tsValuePairs = store.get(key);
        return tsValuePairs.size() == 0 ? "" : search(tsValuePairs, key, timestamp);
    }

    private class Pair {
        private int timestamp;
        private String value;

        public Pair(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return timestamp == pair.timestamp;
        }

        @Override
        public int hashCode() {
            return Objects.hash(timestamp);
        }
    }

    private String search(List<Pair> tsValuePairs, String key, int timestamp) {
        Pair first = tsValuePairs.get(0);
        Pair last = tsValuePairs.get(tsValuePairs.size() - 1);
        if (timestamp >= last.timestamp) {
            return last.value;
        } else if (timestamp < first.timestamp) {
            return "";
        } else if (first.timestamp <= timestamp && timestamp <= last.timestamp) {
            int ind = Collections.binarySearch(tsValuePairs.stream().map(e -> e.timestamp).collect(Collectors.toList()), timestamp);
            return tsValuePairs.get(ind).value;
            //return binarysearch(tsValuePairs, 0, tsValuePairs.size() - 1, key, timestamp);
        }
        return "";
    }

    private String binarysearch(List<Pair> tsValuePairs, int start, int end, String key, int timestamp) {
        if (end - start <= 1) {
            if (timestamp == end) {
                return tsValuePairs.get(end).value;
            } else {
                return tsValuePairs.get(start).value;
            }
        }
        int mid = (start + end) / 2;
        if (timestamp > tsValuePairs.get(mid).timestamp) {
            return binarysearch(tsValuePairs, mid, end, key, timestamp);
        } else {
            return binarysearch(tsValuePairs, start, mid, key, timestamp);
        }


    }

    public static void main(String[] args) {
        TimeMap map = new TimeMap();
        map.set("love", "high", 10);
        map.set("love", "low", 20);
        System.out.println(map.get("love", 5));
        System.out.println(map.get("love", 10));
        System.out.println(map.get("love", 15));
        System.out.println(map.get("love", 20));
        System.out.println(map.get("love", 25));

    }
}
