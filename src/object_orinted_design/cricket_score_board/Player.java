package object_orinted_design.cricket_score_board;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public List<String> getSkills() {
        return skills;
    }

    private String id;

    private String name;

    private int number;

    private List<String> skills;


    public Player(String id, String name, int number, String... skill) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.skills = Arrays.stream(skill).collect(Collectors.toList());
    }
}
