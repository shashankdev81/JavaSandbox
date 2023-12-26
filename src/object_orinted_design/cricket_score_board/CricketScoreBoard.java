package object_orinted_design.cricket_score_board;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CricketScoreBoard {

    private Map<String, ScoreCard> matchScoreCard;

    private GenericRepository<Team> teamRepository = new GenericRepository<>();

    private GenericRepository<Player> playerRepository = new GenericRepository<>();

    private GenericRepository<Match> matchRepository = new GenericRepository<>();

    {
        playerRepository.create("1", new Player("1", "Kohli", 4, "batsman"));
        playerRepository.create("2", new Player("2", "KL Rahul", 10, "batsman"));
        playerRepository.create("3", new Player("3", "Shami", 6, "bowler"));
        playerRepository.create("4", new Player("4", "Atherton", 2, "batsman"));
        playerRepository.create("5", new Player("5", "Flintoff", 3, "bowler"));
        playerRepository.create("6", new Player("16", "Fraser", 5, "batsman"));
        teamRepository.create("India",
            new Team("India", new Player[]{playerRepository.get("1"), playerRepository.get("2")},
                new Player[]{playerRepository.get("3")}));
        teamRepository.create("England",
            new Team("England", new Player[]{playerRepository.get("4"), playerRepository.get("5")},
                new Player[]{playerRepository.get("6")}));

    }

    public void createPlayer(String id, String name, int number, String... skill) {
        playerRepository.create(id, new Player(id, name, number, skill));
    }

    public void createTeam(String name, String[] playingEleven, String[] bench) {
        teamRepository.create("name",
            new Team(name, Arrays.stream(playingEleven).map(p -> playerRepository.get(p))
                .toArray(Player[]::new),
                Arrays.stream(bench).map(p -> playerRepository.get(p)).toArray(Player[]::new)));
    }

    public void recordMatch(String team1, String team2, String date) {
        matchRepository.create("Match-" + UUID.randomUUID().toString(),
            new Match("12-12-2022", teamRepository.get(team1), teamRepository.get(team2)));
    }

}
