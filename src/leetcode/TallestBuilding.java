package leetcode;

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TallestBuilding {

    public int[] tallestBuilding1(int[] heights) {
        Stack<int[]> blockingBuildings = new Stack<>();
        int[] tallest = new int[heights.length];
        for (int index = heights.length - 1; index >= 0; index--) {
            int currentBuildingHeight = heights[index];
            while (!blockingBuildings.isEmpty() && blockingBuildings.peek()[0] <= currentBuildingHeight) {//remove all building with smaller than or equal to the current height from the stack
                blockingBuildings.pop();
            }
            tallest[index] = blockingBuildings.isEmpty() ? -1 : blockingBuildings.peek()[1];
            blockingBuildings.push(new int[]{currentBuildingHeight, index});
        }

        return Arrays.stream(tallest).map(e -> e == -1 ? -1 : heights[e]).toArray();
    }

    public static void main(String[] args) {
        int[] heights = new int[]{1, 5, 2, 3, 2, 1, 10, 11, 9, 8, 7};
        //int[] heights = new int[]{10, 5, 4, 3, 2, 1, 10, 2, 5, 8};
        TallestBuilding solution = new TallestBuilding();
        System.out.println(
                IntStream.of(solution.tallestBuilding(heights)).boxed().collect(Collectors.toList())
        );
    }

    public int[] tallestBuilding(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int[] tallest = new int[heights.length];
        Arrays.fill(tallest, -1);
        for (int i = 0; i < heights.length - 1; i++) {
            if (heights[i + 1] > heights[i]) {
                tallest[i] = heights[i + 1];
            } else {
                stack.push(i);
            }
            if (i == heights.length - 2) {
                break;
            }
            while (!stack.isEmpty() && heights[i + 1] > heights[stack.peek()]) {
                tallest[stack.pop()] = heights[i + 1];
            }
        }

        return tallest;
    }
}
