package leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UnhappyFriends {

    public static void main(String[] args) {
        UnhappyFriends unhappyFriends = new UnhappyFriends();
        System.out.println(unhappyFriends.unhappyFriends(4,
            new int[][]{{1, 3, 2}, {2, 3, 0}, {1, 3, 0}, {0, 2, 1}}, new int[][]{{1, 3}, {0, 2}}));
    }

    public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
        Map<Integer, List<Integer>> friendsMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            friendsMap.put(i, new ArrayList<>());
        }
        for (int i = 0; i < preferences.length; i++) {
            friendsMap.get(i).addAll(
                Arrays.stream(preferences[i]).mapToObj(val -> Integer.valueOf(val))
                    .collect(Collectors.toList()));
        }
        Map<Integer, List<Integer>> pairsMap = new HashMap<>();
        for (int i = 0; i < pairs.length; i++) {
            pairsMap.putIfAbsent(pairs[i][0], new ArrayList<>());
            pairsMap.get(pairs[i][0]).add(pairs[i][1]);
            pairsMap.putIfAbsent(pairs[i][1], new ArrayList<>());
            pairsMap.get(pairs[i][1]).add(pairs[i][0]);
        }
        int unHappyFriends = 0;
        for (Map.Entry<Integer, List<Integer>> entry : friendsMap.entrySet()) {
            Integer x = entry.getKey();
            if (pairsMap.get(x) == null) {
                continue;
            }
            for (Integer y : pairsMap.get(x)) {
                boolean isUnhappy = false;
                int yPref = entry.getValue().indexOf(Integer.valueOf(y));
                if (yPref == 0) {
                    continue;
                }
                yPref = yPref == -1 ? entry.getValue().size() : yPref;
                for (int k = 0; k < yPref; k++) {
                    int u = entry.getValue().get(k);
                    if (!pairsMap.containsKey(u)) {
                        continue;
                    }
                    int uPrefOfX = friendsMap.get(u).indexOf(x);
                    if (uPrefOfX == -1) {
                        continue;
                    }
                    for (int pairedWithU : pairsMap.get(u)) {
                        if (friendsMap.get(u).indexOf(pairedWithU) > uPrefOfX) {
                            unHappyFriends++;
                            isUnhappy = true;
                            break;
                        }
                    }
                    if (isUnhappy) {
                        break;
                    }
                }

            }
        }
        return unHappyFriends;
    }
}
