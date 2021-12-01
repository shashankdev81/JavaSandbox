package leetcode;

public class KokoBanana {

    public int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = max(piles);

        while (low < high) {
            int mid = (low + high) / 2;
            if (!possible(piles, mid, h)) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    private int max(int[] piles) {
        int max = Integer.MIN_VALUE;
        for (int n : piles) {
            if (max < n) {
                max = n;
            }
        }
        return max;
    }

    public boolean possible(int[] piles, int K, int H) {
        int time = 0;
        for (int p : piles)
            time += (p - 1) / K + 1;
        return time <= H;
    }

    public static void main(String[] args) {
        int[] piles = new int[]{3, 6, 7, 11};
        KokoBanana banana = new KokoBanana();
        System.out.println(banana.minEatingSpeed(piles, 8));
    }
}
