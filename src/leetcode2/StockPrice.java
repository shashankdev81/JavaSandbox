package leetcode2;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class StockPrice {


    private TreeMap<Integer, Integer> updateTimeMap = new TreeMap<>();

    private TreeMap<Integer, Integer> priceMap = new TreeMap<>();

    private int currPrice;

    public StockPrice() {

    }

    public void update(int timestamp, int price) {
        if (updateTimeMap.containsKey(timestamp)) {
            Integer cp = updateTimeMap.get(timestamp);
            priceMap.put(cp, priceMap.get(cp) - 1);
            if (priceMap.get(cp) == 0) {
                priceMap.remove(cp);
            }
        }
        updateTimeMap.put(timestamp, price);
        priceMap.putIfAbsent(price, 0);
        priceMap.put(price, priceMap.get(price) + 1);
        currPrice = price;
    }

    public int current() {
        TreeSet<Integer> sorted = new TreeSet<>();
        return currPrice;
    }

    public int maximum() {
        return priceMap.lastEntry().getKey();
    }

    public int minimum() {
        return priceMap.firstEntry().getKey();
    }
}

/**
 * Your StockPrice object will be instantiated and called as such: StockPrice obj = new
 * StockPrice(); obj.update(timestamp,price); int param_2 = obj.current(); int param_3 =
 * obj.maximum(); int param_4 = obj.minimum();
 */