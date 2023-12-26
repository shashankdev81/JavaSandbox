package object_orinted_design.cricket_score_board;

import java.util.ArrayList;
import java.util.List;

public class ScoreCard {

    private Innings first;

    private Innings second;

    public List<Team> getWinner() {
        return new ArrayList<>();
    }

    public int getFirstInningsRuns() {
        return first.getRuns();
    }

    public int getSecondInningsRuns() {
        return second.getRuns();
    }

}
