package leetcode2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RangeFreqQuery {


    private List<Integer> numbers;

    public static void main(String[] args) {
        RangeFreqQuery rangeFreqQuery = new RangeFreqQuery(new int[]{12, 33, 4, 56, 22, 2, 34, 33, 22, 12, 34, 56});
        System.out.println(rangeFreqQuery.query(1, 2, 4));
        System.out.println(rangeFreqQuery.query(0, 11, 33));
    }

    public RangeFreqQuery(int[] arr) {
        numbers = Arrays.stream(arr).boxed().collect(Collectors.toList());
    }

    public int query(int left, int right, int value) {
        int low = left;
        int high = right + 1;
        int count = 0;
        List<Integer> subList = numbers;
        while (low < high && low < subList.size()) {
            subList = subList.subList(low, high);
            List<Integer> sortedList = subList.stream().sorted().collect(Collectors.toList());
            int ind = Collections.binarySearch(sortedList, value);
            if (ind < 0) {
                break;
            }
            count++;
            low = subList.indexOf(sortedList.get(ind)) + 1;
            high = subList.size();
        }
        return count;
    }
}

/**
 * Your RangeFreqQuery object will be instantiated and called as such:
 * RangeFreqQuery obj = new RangeFreqQuery(arr);
 * int param_1 = obj.query(left,right,value);
 */