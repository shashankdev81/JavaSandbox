package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

public class Triplets {


    public static void main(String[] args) {
        Triplets triplets = new Triplets();
        System.out.println(triplets.numberOfTriplets(8, new int[]{-3, -2, 1, 6, 5, 3, 4, 2}));


    }

    private int numberOfTriplets(int t, int[] numbers) {
//        List<Integer> input = Arrays.stream(numbers).boxed().collect(Collectors.toList());
//        input.sort(Comparator.naturalOrder());
//        ArrayDeque<Integer> queue = new ArrayDeque<>(input);
//        int result = recurse(queue, 8, 0, 0);
        Arrays.sort(numbers);
        int result = 0;
        for (int i = 0; i < numbers.length; i++) {
            result += sumOfTwo(Arrays.copyOfRange(numbers, i + 1, numbers.length), t - numbers[i]);
        }
        return result;

    }

    private int recurse(ArrayDeque<Integer> queue, int t, int sum, int count) {
        if (queue.isEmpty()) {
            return 0;
        }
        if (count == 3) {
            if (sum <= t) {
                return 1;
            }
        }
        int head = queue.pollFirst();
        int triplets = 0;
        triplets += recurse(queue, t, sum + head, count + 1);
        triplets += recurse(queue, t, sum, count);
        return triplets;

    }

    private int sumOfTwo(int[] nums, int target) {
        int pairs = 0;
        int low = 0;
        int high = nums.length - 1;
        int sum = 0;
        while (low < high) {
            sum = nums[low] + nums[high];
            if (sum < target) {
                pairs++;
            }
            high--;

        }
        return pairs;
    }
}
