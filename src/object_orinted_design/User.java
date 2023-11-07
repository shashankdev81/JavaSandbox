package object_orinted_design;

public class User {

    public String getName() {
        return name;
    }

    private String name;

    public String getId() {
        return id;
    }

    private String id;


    public User(String userId, String name) {
        this.id = userId;
        this.name = name;
        UserRepository.persist(this);
    }

}
