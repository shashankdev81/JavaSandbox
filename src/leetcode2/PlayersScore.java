package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayersScore {

    public List<List<Integer>> findWinners(int[][] matches) {
        Set<Integer> winners = new HashSet<>();
        Map<Integer, Integer> loosers = new HashMap<>();
        Set<Integer> noLossPlayers = new HashSet<>();
        Set<Integer> oneLossPlayers = new HashSet<>();
        for (int i = 0; i < matches.length; i++) {
            winners.add(matches[i][0]);
            loosers.putIfAbsent(matches[i][1], 0);
            loosers.put(matches[i][1], loosers.get(matches[i][1]) + 1);
            noLossPlayers.add(Integer.valueOf(matches[i][0]));
            noLossPlayers.remove(Integer.valueOf(matches[i][1]));
            if (loosers.get(matches[i][1]) > 1) {
                oneLossPlayers.remove(Integer.valueOf(matches[i][1]));
            } else {
                oneLossPlayers.add(Integer.valueOf(matches[i][1]));
            }
        }
        List<List<Integer>> results = new ArrayList<>();
        results.add(noLossPlayers.stream().collect(Collectors.toList()));
        results.add(oneLossPlayers.stream().collect(Collectors.toList()));
        return results;
    }
}
