package leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class AbsoluteDiff {

    public static void main(String[] args) {
        AbsoluteDiff absoluteDiff = new AbsoluteDiff();
        System.out.println(absoluteDiff.minDifference(new int[]{1, 3, 4, 8},
            new int[][]{{0, 1}, {1, 2}, {2, 3}, {0, 3}}));
    }

    public int[] minDifference(int[] nums, int[][] queries) {
        Segment tree = new Segment(null, null, 0, nums.length - 1, -1);
        tree.build(nums, 0, nums.length - 1);
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int answer = tree.find(nums, queries[i][0], queries[i][1]);
            ans[i] = answer == Integer.MAX_VALUE ? -1 : answer;
        }
        return ans;
    }

    private class Segment {

        int start;
        int end;
        int value;
        Segment left;
        Segment right;

        public Segment(Segment l, Segment r, int s, int e, int v) {
            start = s;
            end = e;
            left = l;
            right = r;
            value = v;
        }

        public void build(int[] nums, int l, int r) {
            //System.out.println(l+","+r);
            if (l == r) {
                this.value = Integer.MAX_VALUE;
                return;
            }
            int mid = (l + r) / 2;
            this.left = new Segment(null, null, l, mid, -1);
            this.left.build(nums, l, mid);
            this.right = new Segment(null, null, mid + 1, r, -1);
            this.right.build(nums, mid + 1, r);
            if (r - l == 1) {
                this.value = Math.abs(nums[l] - nums[r]);
            } else {
                value = Math.min(Math.abs(nums[mid] - nums[mid + 1]),
                    Math.min(this.left.value, this.right.value));
            }

        }

        public int find(int[] nums, int l, int r) {
            System.out.println(l + "," + r);
            if (l == this.start && r == this.end) {
                return value;
            } else {
                int mid = (start + end) / 2;
                if (mid >= r && this.left != null) {
                    return this.left.find(nums, l, r);
                } else if (mid < l && this.right != null) {
                    return this.right.find(nums, l, r);
                } else if (this.left != null && this.right != null) {
                    int leftVal = this.left.find(nums, l, mid);
                    int rightVal = this.right.find(nums, mid, r);
                    return Math.min(Math.abs(nums[mid] - nums[mid + 1]),
                        Math.min(leftVal, rightVal));
                } else {
                    return Integer.MAX_VALUE;
                }
            }
        }

    }
}