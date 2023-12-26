package leetcode2;

import java.util.ArrayDeque;

public class MaximumPoints {


    public static void main(String[] args) {
        MaximumPoints maximumPoints = new MaximumPoints();
        System.out.println(maximumPoints.maxScore(new int[]{30, 88, 33, 37, 18, 77, 54, 73, 31, 88, 93, 25, 18, 31, 71, 8, 97, 20, 98, 16, 65, 40, 18, 25, 13, 51, 59}, 26));
    }


    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;

        // Calculate the sum of the first k elements from the end of the array
        int total = 0;
        for (int i = 0; i < n - k; i++) {
            total += cardPoints[i];
        }

        // Initialize the minimum sum with the total sum
        int minSum = total;

        // Iterate through the circular array by sliding a window of size (n - k)
        for (int i = n - 1; i >= n - k; i--) {
            total += cardPoints[i] - cardPoints[i - (n - k)];
            minSum = Math.min(minSum, total);
        }

        // Calculate the sum of all elements in the array
        int totalSum = 0;
        for (int cardPoint : cardPoints) {
            totalSum += cardPoint;
        }

        // Calculate the maximum score by subtracting the minimum sum from the total sum
        return totalSum - minSum;
    }


    public int maxScore2(int[] cardPoints, int k) {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < cardPoints.length; i++) {
            queue.offer(cardPoints[i]);
        }
        return maximise(queue, k, k, "");

    }

    private int maximise(ArrayDeque<Integer> queue, int k, int count, String soFar) {
        System.out.println("soFar=" + soFar);
        if (count == 0) {
            return 0;
        }
        int head = queue.pollFirst();
        String max1Str = soFar + "," + head;
        int max1 = head + (queue.isEmpty() ? 0 : maximise(queue, k, count - 1, max1Str));
        queue.offerFirst(head);
        int tail = queue.pollLast();
        String max2Str = soFar + "," + tail;
        int max2 = tail + (queue.isEmpty() ? 0 : maximise(queue, k, count - 1, max2Str));
        queue.offerLast(tail);
        return Math.max(max1, max2);
    }
}