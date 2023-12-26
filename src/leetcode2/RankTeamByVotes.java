package leetcode2;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class RankTeamByVotes {

//    Map<Integer, Map<String, Integer>> positionToTeamMap;
//
//    //Map<Integer, Map<String, Integer>> teamToVotesMap;
//
//    Map<Integer, TreeMap<Integer, List<String>>> positionToVotesMap;

    static int MAX_POS = 0;

    public static void main(String[] args) {
        RankTeamByVotes rankTeamByVotes = new RankTeamByVotes();
        System.out.println(rankTeamByVotes.rankTeams(new String[]{"WXYZ", "XYZW"}));
        System.out.println(
            rankTeamByVotes.rankTeams(new String[]{"ABC", "ACB", "ABC", "ACB", "ACB"}));

    }

    public String rankTeams(String[] votes) {
        int teams = votes[0].length();
        Map<String, Votes> votesMatrix = new TreeMap<>();
        int posOfA = (int) 'A';
        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                votesMatrix.putIfAbsent("" + vote.charAt(i),
                    new Votes("" + vote.charAt(i), new int[teams]));
                int[] votsArr = votesMatrix.get("" + vote.charAt(i)).votes;
                votsArr[i] = votsArr[i] + 1;
            }
        }
        String result = votesMatrix.entrySet().stream()
            .sorted((t1, t2) -> {
                for (int i = 0; i < t1.getValue().votes.length; i++) {
                    if (t1.getValue().votes[i] != t2.getValue().votes[i]) {
                        return t1.getValue().votes[i] - t1.getValue().votes[i];
                    }
                }
                return 0;
            }).map(v -> v.getKey()).reduce((acc, s) -> acc + s).orElseGet(() -> "");
        return result;
    }

    public String rankTeams2(String[] votes) {
        int teams = votes[0].length();
        Votes[] votesMatrix = new Votes[26];
        int posOfA = (int) 'A';
        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                int teamIdx = (int) vote.charAt(i) - posOfA;
                if (votesMatrix[teamIdx] == null) {
                    votesMatrix[teamIdx] = new Votes("" + vote.charAt(i), new int[teams]);
                }
                votesMatrix[teamIdx].votes[i] = votesMatrix[teamIdx].votes[i] + 1;
            }
        }
        String result = Arrays.stream(votesMatrix).filter(a -> a != null)
            .sorted((a1, a2) -> {
                for (int i = 0; i < a1.votes.length; i++) {
                    if (a1.votes[i] != a2.votes[i]) {
                        return a2.votes[i] - a1.votes[i];
                    }
                }
                return 0;
            }).map(v -> v.team).reduce((acc, s) -> acc + s).orElseGet(() -> "");
        return result;
    }

    private class Votes {

        String team;
        int[] votes;

        public Votes(String team, int[] votes) {
            this.team = team;
            this.votes = votes;
        }

        public void incrVote(int pos) {
            votes[pos] = votes[pos] + 1;
        }
    }

//    public String rankTeams1(String[] votes) {
//        int positions = 0;
//        positionToTeamMap = new TreeMap<>();
//        positionToVotesMap = new TreeMap<>();
//        for (String vote : votes) {
//            positions = vote.length();
//            MAX_POS = positions;
//            for (int i = 1; i <= vote.length(); i++) {
//                positionToTeamMap.putIfAbsent(i, new TreeMap<>());
//                String team = "" + vote.charAt(i - 1);
//                positionToTeamMap.get(i).putIfAbsent(team, 0);
//                positionToTeamMap.get(i).put(team, positionToTeamMap.get(i).get(team) + 1);
//                //teamToVotesMap.putIfAbsent(team, new TreeMap<>());
//                //teamToVotesMap.get(team).putIfAbsent(i, 0);
//                //teamToVotesMap.get(team).put(i, teamToVotesMap.get(i)+1);
//            }
//        }
//
//        for (Map.Entry<Integer, Map<String, Integer>> entry : positionToTeamMap.entrySet()) {
//            positionToVotesMap.putIfAbsent(entry.getKey(), new TreeMap<>());
//            Map<Integer, List<String>> votesToTeamMap = positionToVotesMap.get(entry.getKey());
//            for (Map.Entry<String, Integer> innerEntry : entry.getValue().entrySet()) {
//                int v = innerEntry.getValue();
//                String team = innerEntry.getKey();
//                votesToTeamMap.putIfAbsent(v, new LinkedList<>());
//                votesToTeamMap.get(v).add(team);
//            }
//        }
//
//        String winnerStr = "";
//        System.out.println("positionToVotesMap=" + positionToVotesMap);
//        for (int i = 1; i <= positions; i++) {
//            winnerStr = winnerStr + winner(positionToVotesMap, i);
//            System.out.println("winnerStr=" + winnerStr);
//
//        }
//
//        return winnerStr;
//    }
//
//    public String winner(Map<Integer, TreeMap<Integer, List<String>>> positionToVotesMap,
//        int pos) {
//        TreeMap<Integer, List<String>> votesToTeamMap = positionToVotesMap.get(pos);
//        if (votesToTeamMap == null || votesToTeamMap.isEmpty()) {
//            return "";
//        }
//        List<String> teams = votesToTeamMap.lastEntry().getValue();
//        if (teams.size() == 1) {
//            return teams.get(0);
//        } else {
//            for (int j = pos + 1; j < MAX_POS; j++) {
//                votesToTeamMap = positionToVotesMap.get(j);
//                List<String> teamsAtNextPos = votesToTeamMap.lastEntry().getValue();
//                if (teamsAtNextPos.stream().filter(t -> teams.contains(t))
//                    .collect(Collectors.toList()).size() == 1) {
//                    return teamsAtNextPos.get(0);
//                }
//            }
//            return winner(positionToVotesMap, pos + 1);
//        }
//    }
}
//W 1-1, 4-1
//X 1-1, 2-1
