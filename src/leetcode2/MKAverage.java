package leetcode2;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class MKAverage {

    TreeMap<Integer, Integer> sorted = new TreeMap<>();

    int mVal;

    int kVal;

    public MKAverage(int m, int k) {
        this.mVal = m;
        this.kVal = k;
    }

    public void addElement(int num) {
        sorted.putIfAbsent(num, 0);
        sorted.put(num, sorted.get(num) + 1);
    }

    public int calculateMKAverage() {
        if (sorted.size() < mVal) {
            return -1;
        }
        int avg = 0;
        Iterator<Entry<Integer, Integer>> itr = sorted.entrySet().iterator();
        int k = 0;
        int size = sorted.values().stream().mapToInt(i -> i.intValue()).sum();
        while (itr.hasNext() && k < size - kVal) {
            Entry<Integer, Integer> num = itr.next();
            int count = num.getValue();
            while (count > 0) {
                if (k >= kVal) {
                    avg += num.getKey();
                }
                count--;
                k++;
            }

        }
        avg = Double.valueOf(Math.ceil((float) avg / (float) (k - 1))).intValue();
        return avg;
    }
}
