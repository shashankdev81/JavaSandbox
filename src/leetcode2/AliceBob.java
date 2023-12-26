package leetcode2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class AliceBob {

    public static void main(String[] args) {

        AliceBob aliceBob = new AliceBob();
        System.out.println(aliceBob.mostProfitablePath(
            new int[][]{{0, 1}, {1, 2}, {1, 3}, {3, 4}}, 3, new int[]{-2, 4, 2, -4, 6}));

    }


    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        Map<Integer, List<Integer>> edgeMap = new HashMap<>();
        Map<Integer, Integer> valueMap = new HashMap<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());

        List<Integer> maxStarSumList = edgeMap.entrySet().stream()
            .map(e -> e.getValue().stream().map(n -> valueMap.get(n))
                .filter(v -> v>0).sorted((v1,v2) -> v1-v2).limit(10).mapToInt(en -> en.intValue()).sum())
            .mapToInt(sum -> sum.intValue()).boxed().collect(Collectors.toList());
        for (int i = 0; i < edges.length; i++) {
            edgeMap.putIfAbsent(edges[i][0], new ArrayList<>());
            edgeMap.putIfAbsent(edges[i][1], new ArrayList<>());
            edgeMap.get(edges[i][0]).add(edges[i][1]);
            edgeMap.get(edges[i][1]).add(edges[i][0]);
        }
        edgeMap.putIfAbsent(-1, new ArrayList<>());
        Queue<int[]> aliceQueue = new ArrayDeque<>();
        Queue<int[]> bobQueue = new ArrayDeque<>();
        Set<Integer> bobVisited = new HashSet<>();
        Set<Integer> aliceVisited = new HashSet<>();
        aliceQueue.offer(new int[]{0, 0});
        bobQueue.offer(new int[]{bob, 0});
        int aliceMoney = 0;
        int bobMoney = 0;
        int maxAliceIncome = Integer.MIN_VALUE;
        Set<Integer> gateOpen = new HashSet<>();
        while (!aliceQueue.isEmpty()) {
            int count = aliceQueue.size();
            while (count > 0 && !aliceQueue.isEmpty()) {
                int[] aliceNext = aliceQueue.poll();
                int[] bobNext = bobQueue.isEmpty() ? new int[]{-1, -1} : bobQueue.poll();
                float k =
                    gateOpen.contains(aliceNext[0]) ? 0 : (aliceNext[0] == bobNext[0] ? 0.5f : 1);
                aliceMoney = (int) (aliceNext[1] + amount[aliceNext[0]] * k);
                aliceVisited.add(aliceNext[0]);
                bobVisited.add(bobNext[0]);
                gateOpen.add(aliceNext[0]);
                gateOpen.add(bobNext[0]);
                List<Integer> visitableList = edgeMap.get(aliceNext[0]).stream()
                    .filter(n -> !aliceVisited.contains(n))
                    .collect(Collectors.toList());
                if (visitableList.isEmpty()) {
                    maxAliceIncome = Math.max(maxAliceIncome, aliceMoney);
                }
                for (Integer node : visitableList) {
                    aliceQueue.offer(new int[]{node, aliceMoney});
                }
                visitableList = edgeMap.get(bobNext[0]).stream()
                    .filter(n -> !bobVisited.contains(n))
                    .filter(n -> {
                        Set<Integer> visited = new HashSet<>();
                        visited.add(bobNext[0]);
                        return isTowardsRoot(edgeMap, n, visited);
                    })
                    .collect(Collectors.toList());
                for (Integer node : visitableList) {
                    bobQueue.offer(new int[]{node, bobMoney});
                }
                count--;
            }
        }
        return maxAliceIncome;
    }

    private boolean isTowardsRoot(Map<Integer, List<Integer>> edgeMap, int current,
        Set<Integer> visited) {
        if (current == 0) {
            return true;
        }
        int penaltyAfterHours = "YYNN".chars().map(s -> ((char)s=='Y')?0:1).sum();

        boolean isRightMove = false;
        visited.add(current);
        for (Integer node : edgeMap.get(current)) {
            if (!visited.contains(node)) {
                isRightMove = isRightMove || isTowardsRoot(edgeMap, node, visited);
            }
        }
        return isRightMove;
    }
}