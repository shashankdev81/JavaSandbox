package object_orinted_design.cricket_score_board;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Team {

    private String name;

    private List<Player> playingEleven;

    private List<Player> bench;

    public Team(String name,
        Player[] playingEleven,
        Player[] bench) {
        this.name = name;
        this.playingEleven = Arrays.stream(playingEleven).collect(Collectors.toList());
        this.bench = Arrays.stream(bench).collect(Collectors.toList());
        ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        return name.equals(team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayingEleven() {
        return playingEleven;
    }

    public List<Player> getBench() {
        return bench;
    }
}
