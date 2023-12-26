package object_orinted_design.cricket_score_board;

import java.util.HashMap;
import java.util.Map;

class Repository {

    private static Map<String, Player> playerRepo = new HashMap<>();

    private static Map<String, Team> teamRepo = new HashMap<>();

    public static Player getPlayer(String pid) {
        return playerRepo.get(pid);
    }

    public static void create(Player player) {
        playerRepo.put(player.getId(), player);
    }

    public static Team getTeam(String tid, Class<Team> className) {
        return teamRepo.get(tid);
    }

    public static void create(Team team) {
        teamRepo.put(team.getName(), team);
    }

}
