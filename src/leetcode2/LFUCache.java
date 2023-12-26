package leetcode2;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class LFUCache {


    private TreeMap<Integer, LinkedList<Integer>> cacheCounter = new TreeMap<>();

    private Map<Integer, Integer[]> cacheMap = new TreeMap<>();

    private int limit = 0;

    public static void main(String[] args) {
        LFUCache lruCache = new LFUCache(2);
        lruCache.put(2, 1);
        lruCache.put(2, 2);
        System.out.println(lruCache.get(2));
        lruCache.put(1, 1);
        lruCache.put(4, 1);
        System.out.println(lruCache.get(2));

    }

    public LFUCache(int capacity) {
        this.limit = capacity;
    }

    public int get(int key) {
        Integer[] valueArr = cacheMap.get(key);
        if (valueArr == null) {
            return -1;
        }

        LinkedList<Integer> keysWithSameCount = cacheCounter.get(valueArr[1]);
        if (keysWithSameCount != null) {
            keysWithSameCount.removeFirst();
            if (keysWithSameCount.isEmpty()) {
                cacheCounter.remove(valueArr[1]);
            }
        }
        System.out.println(
            "Get=" + key + "," + valueArr[0] + "," + valueArr[1] + "," + cacheCounter);
        cacheCounter.putIfAbsent(valueArr[1] + 1, new LinkedList<>());
        cacheCounter.get(valueArr[1] + 1).add(key);
        valueArr[1] = valueArr[1] + 1;
        return valueArr[0];
    }

    public void put(int key, int value) {
        if (!cacheMap.containsKey(key) && cacheMap.size() == limit) {
            Integer countKey = cacheCounter.firstEntry().getKey();
            Integer removeKey = cacheCounter.firstEntry().getValue().removeFirst();
            cacheMap.remove(removeKey);
            if (cacheCounter.firstEntry().getValue().isEmpty()) {
                cacheCounter.remove(countKey);
            }
        } else if (cacheMap.containsKey(key)) {
            Integer[] valueArr = cacheMap.get(key);
            LinkedList<Integer> keysWithSameCount = cacheCounter.get(valueArr[1]);
            if (keysWithSameCount != null) {
                keysWithSameCount.remove(Integer.valueOf(key));
                if (keysWithSameCount.isEmpty()) {
                    cacheCounter.remove(valueArr[1]);
                }
            }
        }
        cacheCounter.putIfAbsent(0, new LinkedList<>());
        cacheCounter.get(0).add(key);
        cacheMap.put(key, new Integer[]{value, 0});
        System.out.println("Put=" + key + "," + value + "," + cacheCounter);
    }
}

/**
 * Your LFUCache object will be instantiated and called as such: LFUCache obj = new
 * LFUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
 */