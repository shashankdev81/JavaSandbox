package leetcode2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class VideoViews {

    public static void main(String[] args) {
        VideoViews videoViews = new VideoViews();
        System.out.println(
            videoViews.mostPopularCreator(new String[]{"alice", "bob", "alice", "chris"},
                new String[]{"one", "two", "three", "four"}, new int[]{5, 10, 5, 4}));
    }

    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        //map <int[]{creater, views}>
        Map<String, Map<String, Integer>> createrToViewsMap = new HashMap<>();

        for (int i = 0; i < views.length; i++) {
            createrToViewsMap.putIfAbsent(creators[i], new HashMap<>());
            createrToViewsMap.get(creators[i]).putIfAbsent(ids[i], 0);
            int viewsVal = createrToViewsMap.get(creators[i]).get(ids[i]);
            createrToViewsMap.get(creators[i]).put(ids[i], viewsVal + views[i]);
        }
        //System.out.println(createrToViewsMap);
        List<List<String>> topViewsList = createrToViewsMap.entrySet().stream()
            .sorted((e1, e2) -> {
                int views1 = e1.getValue().entrySet().stream().map(v1 -> v1.getValue())
                    .mapToInt(v1 -> v1.intValue()).sum();
                int views2 = e2.getValue().entrySet().stream().map(v2 -> v2.getValue())
                    .mapToInt(v2 -> v2.intValue()).sum();
                return views2 - views1;
            })
            .map(e -> {
                String videoId = e.getValue().entrySet().stream().sorted((v1, v2) -> {
                    if (v2.getValue() == v1.getValue()) {
                        return v1.getKey().compareTo(v2.getKey());
                    } else {
                        return v2.getValue() - v1.getValue();
                    }
                }).map(en -> en.getKey()).collect(Collectors.toList()).get(0);
                Integer views2 = e.getValue().entrySet().stream().map(v -> v.getValue())
                    .mapToInt(v -> v.intValue()).sum();
                String createrId = e.getKey();
                return Arrays.stream(new String[]{createrId, views2.toString(), videoId})
                    .collect(Collectors.toList());
            })
            .collect(Collectors.toList());
        System.out.println(topViewsList);
        int maxViews = Integer.valueOf(topViewsList.get(0).get(1));
        List<List<String>> topViewsListFiltered = topViewsList
            .stream()
            .filter(c -> Integer.valueOf(c.get(1)) == maxViews)
            .sorted((c1, c2) -> c1.get(0).compareTo(c2.get(0)))
            .map(c -> Arrays.stream(new String[]{c.get(0), c.get(2)}).collect(Collectors.toList())
                .stream().collect(
                    Collectors.toList()))
            .collect(Collectors.toList());
        System.out.println(topViewsListFiltered);

        return topViewsListFiltered;
    }
}