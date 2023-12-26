package leetcode2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class FreqStack {

    private Map<Integer, Integer> itemFreqMap;

    private Stack<Integer> stack = new Stack<>();

    private TreeMap<Integer, LinkedList<Integer>> freqCountMap;


    private int currMaxFreqNum;

    public FreqStack() {
        itemFreqMap = new HashMap<>();
        freqCountMap = new TreeMap<>(Comparator.reverseOrder());
    }

    public static void main(String[] args) {
        FreqStack freqStack = new FreqStack();
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(4);
        freqStack.push(5);
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());

    }

    public void push(int val) {
        itemFreqMap.putIfAbsent(val, 0);
        itemFreqMap.put(val, itemFreqMap.get(val) + 1);
        freqCountMap.putIfAbsent(itemFreqMap.get(val), new LinkedList<>());
        freqCountMap.get(itemFreqMap.get(val)).add(val);
        if (freqCountMap.containsKey(itemFreqMap.get(val) - 1)) {
            freqCountMap.get(itemFreqMap.get(val) - 1).remove(Integer.valueOf(val));
            if (freqCountMap.get(itemFreqMap.get(val) - 1).isEmpty()) {
                freqCountMap.remove(itemFreqMap.get(val) - 1);
            }
        }
        stack.push(val);
    }

    public int pop() {
        System.out.println(freqCountMap);
        Integer maxFreqNum = freqCountMap.firstEntry().getValue().removeFirst();
        if (freqCountMap.get(freqCountMap.firstEntry().getKey()).isEmpty()) {
            freqCountMap.remove(freqCountMap.firstEntry().getKey());
        }
        itemFreqMap.put(maxFreqNum, itemFreqMap.get(maxFreqNum) - 1);
        freqCountMap.putIfAbsent(itemFreqMap.get(maxFreqNum), new LinkedList<>());
        freqCountMap.get(itemFreqMap.get(maxFreqNum)).add(maxFreqNum);
        Stack<Integer> temp = new Stack<>();
        while (!stack.isEmpty() && stack.peek() != maxFreqNum) {
            temp.push(stack.pop());
        }
        int res = stack.pop();
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
        return res;
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
