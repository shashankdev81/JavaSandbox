package stack_linked_Lists;

import java.util.Stack;

public class LargestRectangle {

    private static int[] arr = new int[]{2, 1, 5, 6, 2, 3};

    private static Stack<Integer> stack = new Stack<Integer>();

    public static void main(String[] args) {

        int ind = 0;
        int dist = 0;
        int maxSoFar = 0;
        int pos = 0;
        while (ind < arr.length) {


            while (stack.isEmpty() || arr[ind] > arr[stack.peek()]) {
                stack.push(ind);
                ind++;
                if (ind >= arr.length) {
                    break;
                }
            }

            dist = ind - 1;
            while (!stack.isEmpty() && arr[stack.peek()] > arr[ind]) {
                int top = stack.pop();
                int possibleMax = arr[top] * (dist - top + 1);
                if (maxSoFar < possibleMax) {
                    maxSoFar = possibleMax;
                    pos = top;
                }
            }

            if (!stack.isEmpty() && arr[stack.peek()] == arr[ind]) {
                ind++;
            }

        }

        System.out.println("max so far and po=" + maxSoFar + "," + pos);
    }
}
