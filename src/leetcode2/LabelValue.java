//package leetcode2;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.PriorityQueue;
//
//public class LabelValue {
//
//    private int maxScore = Integer.MIN_VALUE;
//
//    public int largestValsFromLabels(int[] values, int[] labels, int numWanted, int useLimit) {
//        int maxScore;
//        PriorityQueue<int[]> queue = new PriorityQueue<>((i1, i2) -> i2[0] - i1[0]);
//        for (int i = 0; i < values.length; i++) {
//            queue.offer(new int[]{values[i], labels[i]});
//        }
//        Map<Integer, Integer> usedLabels = new HashMap<>();
//        int count = 0;
//        while (!queue.isEmpty() && count < numWanted) {
//            int[] item = queue.poll();
//            if (usedLabels.containsKey(item[1]) && usedLabels.get(item[1]) == useLimit) {
//                continue;
//            }
//            usedLabels.putIfAbsent(item[1], 0);
//            usedLabels.put(item[1], usedLabels.get(item[1]) + 1);
//            maxScore += item[0];
//            count++;
//        }
//        return maxScore;
//    }
//
//    public int largestValsFromLabels1(int[] values, int[] labels, int numWanted, int useLimit) {
//        Map<Integer, Integer> itemMap = new HashMap<>();
//        maxScore(values, labels, 0, numWanted, useLimit, itemMap, 0, 0);
//        return maxScore;
//    }
//
//    private void maxScore(int[] values, int[] labels, int idx, int numWanted, int useLimit,
//        Map<Integer, Integer> itemMap, int score, int count) {
//        if (idx >= values.length || count == numWanted) {
//            if (count == numWanted) {
//                maxScore = Math.max(maxScore, score);
//            }
//            return;
//        }
//        if (itemMap.containsKey(labels[idx]) && itemMap.get(labels[idx]) <= useLimit) {
//            itemMap.putIfAbsent(labels[idx], 0);
//            itemMap.put(labels[idx], itemMap.get(labels[idx]) + 1);
//            maxScore(values, labels, idx, numWanted, useLimit, itemMap, values[idx] + score,
//                count + 1);
//            maxScore(values, labels, idx + 1, numWanted, useLimit, itemMap, values[idx] + score,
//                count + 1);
//            itemMap.put(labels[idx], itemMap.get(labels[idx]) - 1);
//        }
//        maxScore(values, labels, idx + 1, numWanted, useLimit, itemMap, score, count);
//
//    }
//
//}
