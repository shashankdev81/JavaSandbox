package object_orinted_design;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private static Map<String, User> users = new HashMap<>();

    public static void persist(User user) {
        users.putIfAbsent(user.getId(), user);
    }

    public static User get(String userId) {
        return users.get(userId);
    }
}
