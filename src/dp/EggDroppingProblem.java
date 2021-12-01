package dp;

public class EggDroppingProblem {

    private int breaksFloorInd;

    public static void main(String[] args) {
        EggDroppingProblem problem = new EggDroppingProblem();
        System.out.println(minTrials(13, 4));
    }

    private int getMinExperiments(int m, int n) {
        int steps = Integer.MIN_VALUE;
        for (breaksFloorInd = 1; breaksFloorInd <= n; breaksFloorInd++) {
            steps = Math.max(steps, getMinExperiments(1, n, m, n));
            System.out.println(breaksFloorInd + "=" + steps);
        }
        return steps;
    }

    private int getMinExperiments(int start, int end, int m, int n) {

        if (m == 1) {
            return end - start + 1;
        }


        int median = (end + start) / 2;
        if (n % 2 != 0 && (median - start) > 1 && (median - start) == (end - median)) {
            median += -1;
        }
        boolean isBroken = isBroken(median);

        if (!isBroken) {
            if (start == end || (n - median == 1)) {
                return 1;
            }
            return 1 + getMinExperiments(median + 1, end, m, n);
        } else {
            if (median == start) {
                return 1;
            }
            return 1 + getMinExperiments(start, median - 1, m - 1, n);
        }


    }

    private boolean isBroken(int median) {
        return median >= breaksFloorInd;
    }

    static int binomialCoeff(int x, int n, int k)
    {
        int sum = 0, term = 1;
        for (int i = 1; i <= n && sum < k; ++i) {
            term *= x - i + 1;
            term /= i;
            sum += term;
        }
        return sum;
    }

    // Do binary search to find minimum
// number of trials in worst case.
    static int minTrials(int n, int k)
    {
        // Initialize low and high as 1st
        //and last floors
        int low = 1, high = k;

        // Do binary search, for every mid,
        // find sum of binomial coefficients and
        // check if the sum is greater than k or not.
        while (low < high) {
            int mid = (low + high) / 2;
            if (binomialCoeff(mid, n, k) < k)
                low = mid + 1;
            else
                high = mid;
        }

        return low;
    }

}
