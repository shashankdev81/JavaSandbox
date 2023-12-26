package object_orinted_design.cricket_score_board;

import java.util.Objects;

public class Match {

    private String date;

    private Team team1;

    private Team team2;

    public Match(String date, Team team1, Team team2) {
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Match match = (Match) o;
        return Objects.equals(date, match.date) && Objects.equals(team1,
            match.team1) && Objects.equals(team2, match.team2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, team1, team2);
    }

    public String getDate() {
        return date;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
}
