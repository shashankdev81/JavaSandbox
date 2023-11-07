package leetcode2;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MaximiseProfit {

    public static void main(String[] args) {
        MaximiseProfit maximiseProfit = new MaximiseProfit();
        System.out.println(maximiseProfit.maxProfitAssignment(new int[]{85, 47, 57}, new int[]{24, 66, 99}, new int[]{40, 25, 25}));
        System.out.println(maximiseProfit.maxProfitAssignment(new int[]{2, 4, 6, 8, 10}, new int[]{10, 20, 30, 40, 50}, new int[]{4, 5, 6, 7}));
    }

    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        TreeMap<ProfDiff, Integer> diffMap = new TreeMap<>(new DiffComparator());
        TreeMap<ProfDiff, Integer> profitMap = new TreeMap<>(new ProfComparator());
        //diff.addAll(difficulty.stream().boxed().collect(Collectors.toList()));
        for (int i = 0; i < difficulty.length; i++) {
            ProfDiff profDiff = new ProfDiff(difficulty[i], profit[i]);
            diffMap.put(profDiff, i);
            profitMap.put(profDiff, i);

        }
        System.out.println(diffMap);
        int maxProfit = 0;
        for (int j = 0; j < worker.length; j++) {
            ProfDiff profDiff = new ProfDiff(worker[j], Integer.MAX_VALUE);
            System.out.println("profDiff=" + profDiff);
            java.util.NavigableMap<ProfDiff, Integer> maxDiffView = diffMap.headMap(profDiff, true);
            TreeMap<ProfDiff, Integer> tempMap = new TreeMap<>(new ProfComparator());
            tempMap.putAll(maxDiffView);
            int workerProfit = tempMap.isEmpty() ? 0 : tempMap.lastEntry().getKey().profit;
            maxProfit += workerProfit;
            System.out.println(j + "=" + maxDiffView + "," + workerProfit);

        }
        return maxProfit;
    }

    public class ProfDiff {
        int profit;
        int diff;

        ProfDiff(int d, int p) {
            profit = p;
            diff = d;
        }

        public String toString() {
            return "ProfDiff{" +
                    "profit=" + profit +
                    ", diff=" + diff +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProfDiff profDiff = (ProfDiff) o;
            return profit == profDiff.profit && diff == profDiff.diff;
        }

        @Override
        public int hashCode() {
            return Objects.hash(profit, diff);
        }

    }

    public class ProfComparator implements Comparator<ProfDiff> {


        @Override
        public int compare(ProfDiff o1, ProfDiff o2) {
            if (o1.profit == o2.profit) {
                return Integer.compare(o1.diff, o2.diff);
            } else {
                return Integer.compare(o1.profit, o2.profit);
            }
        }
    }

    public class DiffComparator implements Comparator<ProfDiff> {


        @Override
        public int compare(ProfDiff o1, ProfDiff o2) {
            if (o1.diff == o2.diff) {
                return Integer.compare(o1.profit, o2.profit);
            } else {
                return Integer.compare(o1.diff, o2.diff);
            }
        }
    }
}
