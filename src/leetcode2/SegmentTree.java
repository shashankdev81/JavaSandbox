package leetcode2;

import java.util.Arrays;

public class SegmentTree {

    private Segment root = null;

    public static void main(String[] args) {
        SegmentTree segmentTree = new SegmentTree(new int[]{1, 2, 3, 4, 5, 6});
        System.out.println(segmentTree.getRangeSum(2, 4));

    }

    public SegmentTree(int[] arr) {
        root = constructSegmentTree(arr, 0, arr.length - 1);
    }

    public int getRangeSum(int low, int high) {
        return root.getRangeSum(low, high);
    }

    private Segment constructSegmentTree(int[] arr, int start, int end) {
        if (start > end) {
            return null;
        }
        Segment root = new Segment(start, end, Arrays.stream(Arrays.copyOfRange(arr, start, end + 1)).sum(), null, null);
        if (start == end) {
            return root;
        }
        int mid = (start + end) / 2;
        root.left = constructSegmentTree(arr, start, mid);
        root.right = constructSegmentTree(arr, mid + 1, end);
        return root;
    }

    public class Segment {
        int start;
        int end;
        int sum;
        Segment left;
        Segment right;

        public Segment(int start, int end, int sum, Segment left, Segment right) {
            this.start = start;
            this.end = end;
            this.sum = sum;
            this.left = left;
            this.right = right;
        }

        public int getRangeSum(int low, int high) {
            if (low == start && high == end) {
                return sum;
            } else if (inBetween(low, high, left.start, left.end)) {
                return left.getRangeSum(low, high);
            } else if (inBetween(low, high, right.start, right.end)) {
                return right.getRangeSum(low, high);
            } else {
                return left.getRangeSum(low, left.end) + right.getRangeSum(right.start, high);
            }
        }

        private boolean inBetween(int low, int high, int start, int end) {
            return low >= start && low <= end && high >= start && high <= end;
        }

    }
}
