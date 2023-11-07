package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

class TopVotedCandidate {

    private TreeMap<Integer, Integer> votes = new TreeMap<>();

    private TreeMap<Integer, Integer> votesReverseIndex = new TreeMap<>();

    private TreeMap<Integer, Integer> timeToLeadingCandidate = new TreeMap<>();

    public static void main(String[] args) {
        TopVotedCandidate topVotedCandidate = new TopVotedCandidate(new int[]{0, 1, 1, 0, 0, 1, 0}, new int[]{0, 5, 10, 15, 20, 25, 30});
        System.out.println(topVotedCandidate.q(3));
        System.out.println(topVotedCandidate.q(12));
        System.out.println(topVotedCandidate.q(25));
        System.out.println(topVotedCandidate.q(15));
        System.out.println(topVotedCandidate.q(24));
        System.out.println(topVotedCandidate.q(8));

    }

    public TopVotedCandidate(int[] persons, int[] times) {
        for (int i = 0; i < persons.length; i++) {
            votes.putIfAbsent(persons[i], 0);

            Map.Entry<Integer, Integer> en = votes.lastEntry();
            votes.put(persons[i], votes.get(persons[i]) + 1);
            votesReverseIndex.put(votes.get(persons[i]), persons[i]);
            if (en.getValue() == votes.get(persons[i])) {
                timeToLeadingCandidate.put(times[i], persons[i]);
            } else {
                timeToLeadingCandidate.put(times[i], votesReverseIndex.lastEntry().getValue());
            }
        }

    }

    public int q(int t) {
        TreeMap<Integer, Map<String, Integer>> secondLevelAgg = new TreeMap<>();
        Optional<Integer> minSum = secondLevelAgg.subMap(10,20).values().stream().map(m -> m.get("tweetName")).reduce((i1, i2)->i1+i2);
        minSum.orElseGet(() -> 0);
        Integer res = timeToLeadingCandidate.get(t);
        if (res == null) {
            List<Integer> timeLine = timeToLeadingCandidate.keySet().stream().map(e -> e.intValue()).collect(Collectors.toList());
            int index = Collections.binarySearch(timeLine, t);
            if (index <= 0) {
                if (index == 0) {
                    res = null;
                } else {
                    res = timeToLeadingCandidate.values().stream().map(e -> e.intValue()).collect(Collectors.toList()).get(Math.abs(index) - 2);
                }
            }
        }
        return res == null ? 0 : res;
    }
}

/**
 * Your TopVotedCandidate object will be instantiated and called as such:
 * TopVotedCandidate obj = new TopVotedCandidate(persons, times);
 * int param_1 = obj.q(t);
 */