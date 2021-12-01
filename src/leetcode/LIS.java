package leetcode;

import java.util.ArrayList;
import java.util.Arrays;

public class LIS {

    public int lengthOfLIS(int[] nums) {
        ArrayList<Integer> sub = new ArrayList<>();
        sub.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (num > sub.get(sub.size() - 1)) {
                sub.add(num);
            } else {
                // Find the first element in sub that is greater than or equal to num
                int j = 0;
                while (num > sub.get(j)) {
                    j += 1;
                }

                sub.set(j, num);
            }
        }
        System.out.println(sub);
        return sub.size();
    }

    public int CeilIndex(int A[], int l, int r, int key) {
        while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (A[m] >= key)
                r = m;
            else
                l = m;
        }

        return r;
    }

    public int getLis(int A[]) {
        // Add boundary case, when array size is one
        int size = A.length;
        int[] tailTable = new int[size];
        int len; // always points empty slot

        tailTable[0] = A[0];
        len = 1;
        for (int i = 1; i < size; i++) {
            if (A[i] < tailTable[0])
                // new smallest value
                tailTable[0] = A[i];

            else if (A[i] > tailTable[len - 1])
                // A[i] wants to extend largest subsequence
                tailTable[len++] = A[i];

            else
                // A[i] wants to be current end candidate of an existing
                // subsequence. It will replace ceil value in tailTable
                tailTable[CeilIndex(tailTable, -1, len - 1, A[i])] = A[i];
        }
        Arrays.stream(tailTable).forEach(n -> System.out.print(n + ","));
        return len;
    }

    public static void main(String[] args) {
        LIS lis = new LIS();
        int[] nums = new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        lis.lengthOfLIS(nums);
        lis.getLis(nums);
        int[] right = new int[]{0, 2, 6, 9, 11, 15};
        int[] wrong = new int[]{0, 1, 3, 7, 11, 15};
        System.out.println("----");
        lis.getLis(new int[]{0, 8, 4, 12, 2});


    }
}
