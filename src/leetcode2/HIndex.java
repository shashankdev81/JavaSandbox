package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

class HIndex {

    private Map<Integer, Set<Integer>> citationsIndex;

    private Map<Integer, Set<Integer>> citationsToPapersMap;

    public static void main(String[] args) {
        HIndex hIndex = new HIndex();
        int h = hIndex.hIndex(new int[]{1, 3, 1});
        System.out.println(h);

    }

    public int hIndex(int[] citations) {
        citationsIndex = new HashMap<>();
        citationsToPapersMap = new HashMap<>();
        for (int i = 0; i < citations.length; i++) {
            citationsIndex.putIfAbsent(citations[i], new HashSet<>());
            citationsIndex.get(citations[i]).add(i + 1);
            citationsToPapersMap.putIfAbsent(citations[i], new HashSet<>());
            citationsToPapersMap.get(citations[i]).add(i + 1);
        }
        for (Map.Entry<Integer, Set<Integer>> entry : citationsToPapersMap.entrySet()) {
            for (int i = entry.getKey() - 1; i > 0; i--) {
                if (citationsToPapersMap.get(i) == null) {
                    continue;
                }
                citationsToPapersMap.get(i).addAll(citationsIndex.get(entry.getKey()));
            }
        }
        int h = 0;
        int max = 0;
        List<Map.Entry<Integer, Set<Integer>>> collect = citationsToPapersMap.entrySet().stream().collect(Collectors.toList());
        if (collect != null && !collect.isEmpty()) {
            for (Map.Entry<Integer, Set<Integer>> entry : collect) {
                if (entry.getValue().size() > max) {
                    max = entry.getValue().size();
                    h = entry.getKey();
                }
            }
        }
        return h;
    }
}