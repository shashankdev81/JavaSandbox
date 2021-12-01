package stack_linked_Lists;

import java.util.Stack;

public class RainWater {

    static int[] arr = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
    static int ind = 0;
    static int total = 0;
    static int bound = Integer.MAX_VALUE;
    static Stack<Integer> stack = new Stack<Integer>();

    public static void main(String[] args) {


        while (arr[ind + 1] > arr[ind]) {
            bound = arr[ind + 1];
            ind++;
        }

        while (ind < arr.length) {
            if (!stack.isEmpty() && stack.peek() != 0 && arr[ind] != 0 && stack.peek() > arr[ind]) {
                popAll();
                stack.push(arr[ind - 1]);
                bound = stack.peek();
            }
            stack.push(arr[ind]);
            ind++;
        }


        if (!stack.isEmpty()) {
            int poppped = stack.peek();
            while (!stack.isEmpty()) {
                poppped = stack.pop();
                if (poppped < stack.peek()) {
                    continue;
                } else {
                    break;
                }
            }
            if (poppped < bound) {
                bound = poppped;
            }
        }
        popAll();
        System.out.println("Total rainwater=" + total);

    }

    private static void popAll() {
        while (!stack.isEmpty()) {
            int top = stack.pop();
            total += (bound - top > 0) ? bound - top : 0;
        }

    }


}
