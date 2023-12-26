package leetcode2;

import java.util.Arrays;
import java.util.Stack;

public class NextGreater {

    public static void main(String[] args) {
        NextGreater nextGreater = new NextGreater();
        nextGreater.nextGreaterElements(new int[]{1, 5, 3, 6, 8});
    }

    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack = new Stack<Integer>();
        int[] nextGreater = new int[nums.length];
        int breakPt = 0;
        int i = 1;
        stack.push(0);
        while (i < nums.length) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                nextGreater[stack.pop()] = nums[i];
            }
            stack.push(i++);
        }
        i = 0;
        while (i < nums.length) {
            if (nums[i] > nums[stack.peek()]) {
                nextGreater[stack.pop()] = nums[i];
            } else {
                i++;
            }
        }
        //System.out.println(Arrays.toString(nextGreater));
        while (!stack.isEmpty()) {
            nextGreater[stack.pop()] = -1;
        }
        System.out.println(Arrays.toString(nextGreater));
        return nextGreater;
    }
}