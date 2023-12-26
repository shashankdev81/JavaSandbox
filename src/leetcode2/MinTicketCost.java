package leetcode2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class MinTicketCost {

    public static void main(String[] args) {
        MinTicketCost minTicketCost = new MinTicketCost();
        System.out.println(
            minTicketCost.mincostTickets(new int[]{1, 4, 6, 7, 8, 20}, new int[]{2, 7, 15}));
    }

    public int mincostTickets(int[] days, int[] costs) {
        List<Integer> daysList = Arrays.stream(days).mapToObj(i -> Integer.valueOf(i))
            .collect(Collectors.toList());
        return cost(daysList, 0, costs);
    }

    private int cost(List<Integer> days, int idx, int[] costs) {
        if (idx >= days.size()) {
            return 0;
        }
        int cost1 = Integer.MAX_VALUE;
        int cost2 = Integer.MAX_VALUE;
        int cost3 = Integer.MAX_VALUE;
        cost1 = costs[0] + cost(days, idx + 1, costs);
        cost2 = costs[1] + cost(days, move(days, idx, 7), costs);
        cost3 = costs[2] + cost(days, move(days, idx, 30), costs);


        return Math.min(cost1, Math.min(cost2, cost3));
    }

    private int move(List<Integer> days, int idx, int length) {
        int weekOrMOnthIdx = Collections.binarySearch(days, days.get(idx) + length);
        weekOrMOnthIdx = weekOrMOnthIdx < 0 ? Math.abs(weekOrMOnthIdx) - 1 : weekOrMOnthIdx - 1;
        return weekOrMOnthIdx < days.size() ? weekOrMOnthIdx : days.size();
    }
}
