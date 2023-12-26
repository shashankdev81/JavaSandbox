package leetcode2;

import java.util.Arrays;

public class MaxFreq {

    public static void main(String[] args) {
        MaxFreq maxFreq = new MaxFreq();
        maxFreq.maxFrequency(new int[]{1, 4, 8, 13}, 5);
        int[] chalk = new int[5];

    }

    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums); // Sort the input array in ascending order

        int l = 0, r = 0; // Initialize two pointers, l and r, representing the left and right boundaries of the sliding window
        int res = 0; // Initialize a variable to store the maximum frequency
        long total = 0; // Initialize a variable to keep track of the sum of elements within the sliding window

        while (r < nums.length) { // Iterate through the array using the right pointer until it reaches the end
            total += nums[r]; // Add the current number to the total sum

            while (nums[r] * (r - l + 1) > total + k) {
                // While the product of the current number and the length of the window is greater than the total sum plus the allowed difference k,
                // reduce the window size by moving the left pointer and subtracting the element at the left index from the total sum.
                total -= nums[l];
                l++;
            }

            res = Math.max(res, r - l + 1); // Update the maximum frequency by comparing the current window size with the previous maximum

            r++; // Expand the window by moving the right pointer to the next element
        }

        return res; // Return the maximum frequency achieved
    }
}
