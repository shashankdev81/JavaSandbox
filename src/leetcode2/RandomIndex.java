package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomIndex {

    private Map<Integer, PairObject<List<Integer>, Random>> numToIndexMap = new HashMap<>();

    public RandomIndex(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            numToIndexMap.putIfAbsent(nums[i], new PairObject<List<Integer>, Random>(
                new ArrayList<>(),
                new Random(0)));
            numToIndexMap.get(nums[i]).key.add(i);
        }

    }

    public int pick(int target) {
        PairObject<List<Integer>, Random> po = numToIndexMap.get(target);
        int index = po.value.nextInt(po.key.size());
        return index;
    }

    class PairObject<K, V> {

        K key;
        V value;

        public PairObject(K key, V value) {
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
