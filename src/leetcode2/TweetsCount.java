package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

public class TweetsCount {

    private TreeMap<Integer, Map<String, Integer>> secondLevelAgg;

    // private Map<Integer, Map<String, Integer>> hourLevelAgg;

    // private Map<Integer, Map<String, Integer>> dayLevelAgg;

    public TweetsCount() {
        secondLevelAgg = new TreeMap<>();
        // hourLevelAgg = new TreeMap<>();
        // dayLevelAgg = new TreeMap<>();

    }

    public void recordTweet(String tweetName, int time) {
        secondLevelAgg.putIfAbsent(time, new HashMap<>());
        secondLevelAgg.get(time).putIfAbsent(tweetName, 0);
        secondLevelAgg.get(time).put(tweetName, secondLevelAgg.get(time).get(tweetName) + 1);
    }

    public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
        List<Integer> aggList = new ArrayList<>();
        if (freq.equals("minute")) {
            for (int minStart = startTime; minStart <= endTime; minStart += 60) {
                int minEnd = minStart + 60 <= secondLevelAgg.size() ? minStart + 60 : secondLevelAgg.lastKey();
                int minSum = 0;
                if (minStart != minEnd) {
                    List<Integer> list = secondLevelAgg.subMap(minStart, minEnd).values().stream().map(m -> m.get(tweetName)).collect(Collectors.toList());
                    System.out.println("list=" + list);
                    minSum = list.stream().reduce((i1, i2) -> i1 + i2).orElseGet(() -> 0);
                } else {
                    minSum = secondLevelAgg.get(minStart).get(tweetName);
                }
                aggList.add(minSum);
            }

        }
        if (freq.equals("hour")) {
            if (startTime < 3660) {
                aggList.add(secondLevelAgg.subMap(startTime, endTime).values().stream().map(m -> m.get(tweetName)).reduce((i1, i2) -> i1 + i2).orElseGet(() -> 0));
            } else {
                aggregate(tweetName, startTime, endTime, aggList, 3660);
            }

        }

        if (freq.equals("day")) {
            if (startTime < 100000) {
                aggList.add(secondLevelAgg.subMap(startTime, endTime).values().stream().map(m -> m.get(tweetName)).reduce((i1, i2) -> i1 + i2).orElseGet(() -> 0));
            } else {
                aggregate(tweetName, startTime, endTime, aggList, 100000);
            }

        }

        return aggList;
    }

    private void aggregate(String tweetName, int startTime, int endTime, List<Integer> aggList, int window) {
        for (int start = startTime; start <= endTime; start += window) {
            int hourEnd = start + window <= secondLevelAgg.size() ? start + window : secondLevelAgg.lastKey();
            System.out.println(start + "," + hourEnd);
            int hourSum = start != hourEnd ? secondLevelAgg.subMap(start, hourEnd).values().stream().map(m -> m.get(tweetName)).reduce((i1, i2) -> i1 + i2).orElseGet(() -> 0) : secondLevelAgg.get(start).get(tweetName);
            aggList.add(hourSum);
        }
    }
}

/**
 * Your TweetCounts object will be instantiated and called as such:
 * TweetCounts obj = new TweetCounts();
 * obj.recordTweet(tweetName,time);
 * List<Integer> param_2 = obj.getTweetCountsPerFrequency(freq,tweetName,startTime,endTime);
 */
