package object_orinted_design.cricket_score_board;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Innings {

    public Team getTeam() {
        return team;
    }

    private Team team;

    private Map<Integer, Over> overMap;

    public Map<Integer, Over> getOverMap() {
        return overMap;
    }

    public int getRuns() {
        return overMap.entrySet().stream().map(o -> o.getValue().getRunsScored())
            .mapToInt(i -> i.intValue()).sum();
    }

    private class Over {

        Map<Integer, Outcome> overMap = new HashMap<>();

        public void add(Integer ball, Outcome outcome) {
            overMap.put(ball, outcome);
        }

        public int getRunsScored() {
            return overMap.entrySet().stream().map(o -> o.getValue().runs)
                .mapToInt(i -> i.intValue()).sum();
        }

    }

    private class Outcome {

        int runs;
        Result result;

        public Outcome(int runs, Result result) {
            this.runs = runs;
            this.result = result;
        }
    }

    private enum Result {
        WICKET, NO_BALL, WIDE_BALL, FREE_HIT;

    }

}
