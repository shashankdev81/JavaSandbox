package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TimeMapSimpler {

    private ConcurrentHashMap<String, List<Pair>> store;

    /**
     * Initialize your data structure here.
     */
    public TimeMapSimpler() {
        store = new ConcurrentHashMap<String, List<Pair>>();

    }

    public void set(String key, String value, int timestamp) {
        store.putIfAbsent(key, new LinkedList<Pair>());
        store.get(key).add(new Pair(timestamp, value));
    }

    public String get(String key, int timestamp) {
        store.putIfAbsent(key, new LinkedList<Pair>());
        List<Pair> tsValuePairs = store.get(key);
        if (tsValuePairs.size() == 0) {
            return "";
        } else {
            for (int i = tsValuePairs.size() - 1; i >= 0; i--) {
                Pair pair = tsValuePairs.get(i);
                if (pair.timestamp <= timestamp) {
                    return pair.value;
                }
            }
        }
        return "";
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


    public static void main(String[] args) {
        TimeMapSimpler map = new TimeMapSimpler();
        map.set("love", "high", 10);
        map.set("love", "low", 20);
        System.out.println(map.get("love", 5));
        System.out.println(map.get("love", 10));
        System.out.println(map.get("love", 15));
        System.out.println(map.get("love", 20));
        System.out.println(map.get("love", 25));

    }
}
