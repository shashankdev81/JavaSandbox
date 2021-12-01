public class GasStationProblem {

    private boolean isPossible;

    public static void main(String[] args) {
//        int[] gas = {1, 2, 3, 4, 5};
//        int[] cost = {3, 4, 5, 1, 2};

        int[] gas = {5, 1, 2, 3, 4};
        int[] cost = {2, 3, 4, 5, 1};

        int ind = 0;
        while (cost[ind] > gas[ind]) {
            //do nothing
            ind++;
        }

        int basePos = ind;
        boolean isPossible = false;
        do {

            //with start as starting point check if you can cover all trip
            if (!isTripPossible(gas, cost, basePos)) {
                basePos++;
                if (basePos == gas.length) {
                    basePos = 0;
                }
            } else {
                isPossible = true;
                break;
            }

        } while (basePos != ind);
        System.out.println("Is one whole trip possible=" + isPossible + ", start=" + basePos);
    }

    private static boolean isTripPossible(int[] gas, int[] cost, int basePos) {

        int start = basePos;
        int totalGas = 0;

        do {
            totalGas += gas[start] - cost[start];
            if (totalGas < 0) {
                return false;
            }
            start++;
            if (start == gas.length) {
                start = 0;
            }
        } while (start != basePos);

        return totalGas >= 0;
    }
}
