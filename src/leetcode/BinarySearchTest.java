package leetcode;

import java.util.Arrays;
import java.util.List;

public class BinarySearchTest {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(4, 6, 12, 23, 39, 45);
        int found = binarysearch(list, 0, list.size() - 1, 1);
        System.out.println(found);

    }

    private static int binarysearch(List<Integer> tsValuePairs, int start, int end, int timestamp) {
        if (end - start <= 1) {
            if (timestamp == start) {
                return tsValuePairs.get(start);
            } else {
                return tsValuePairs.get(start);
            }
        }
        int mid = (start + end) / 2;
        if (timestamp > tsValuePairs.get(mid)) {
            return binarysearch(tsValuePairs, mid, end, timestamp);
        } else {
            return binarysearch(tsValuePairs, start, mid, timestamp);
        }
    }
}
