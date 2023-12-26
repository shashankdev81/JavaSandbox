package leetcode2;

public class MinJumps7 {

    public static void main(String[] args) {
        MinJumps7 minJumps7 = new MinJumps7();
        System.out.println(minJumps7.minCost(new int[]{0, 1, 2}, new int[]{1, 1, 1}));

    }

    public long minCost(int[] nums, int[] costs) {
        return Math.min(recurse(nums, 0, 0, 1, costs, 0), recurse(nums, 0, 1, 2, costs, 0));
    }

    private int recurse(int[] nums, int i, int k, int j, int[] costs, int costSoFar) {
        System.out.println("recurse=" + i + "," + k + "," + j + "," + costSoFar);
        int cost = 0;
        int minCost = Integer.MAX_VALUE;
        if (k > nums.length - 2 || j > nums.length - 1) {
            return minCost;
        }
        if (i == k || (nums[k] < nums[i] && nums[i] <= nums[j]) || (nums[k] >= nums[i] && nums[i] > nums[j])) {
            if (j == nums.length - 1) {
                return costSoFar + costs[j];
            }
            cost += costSoFar + costs[j];
            minCost = Math.min(minCost, recurse(nums, j, j, j + 1, costs, costSoFar));
            minCost = Math.min(minCost, recurse(nums, j, j + 1, j + 2, costs, costSoFar));
        } else {
            minCost = Math.min(minCost, recurse(nums, i, k + 1, j + 1, costs, costSoFar));
        }
        return minCost;
    }


}

//nums[k] < nums[i] <= nums[j]. i < k < j
//nums[k] >= nums[i] > nums[j]. i < k < j

//3,2,4,4,1. (peak to peak jump)curr is greater than or equal to start and greater than end of window
//4,5,3,4,1. (valley to peak to valley jump)curr is smaller than start
