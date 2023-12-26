package object_orinted_design;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VoteAlgorithm {

    public static void main(String[] args) {
        VoteAlgorithm voteAlgorithm = new VoteAlgorithm();
        voteAlgorithm.vote(new String[]{"A, B, C"});
        voteAlgorithm.vote(new String[]{"A, C, D"});
        voteAlgorithm.vote(new String[]{"D, A, C"});
        System.out.println(voteAlgorithm.getCandidates());
    }


    private Map<String, Integer> candidateToVotes = new HashMap<>();

    public void vote(String[] votes) {
        for (int i = 0; i < votes.length; i++) {
            candidateToVotes.putIfAbsent(votes[i], 0);
            int points = candidateToVotes.get(votes[i]) + i < 3 ? 3 - i : 0;
            candidateToVotes.put(votes[i], points);
        }
    }

    public List<String> getCandidates() {
        List<String> candidates = candidateToVotes.entrySet().stream().sorted((e1, e2) -> {
            if (e2.getValue() == e1.getValue()) {
                return e1.getKey().compareTo(e2.getKey());
            } else {
                return e2.getValue() - e1.getValue();
            }
        }).map(e -> e.getKey()).collect(
            Collectors.toList());
        return candidates;
    }


}
