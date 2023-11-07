package object_orinted_design.splitwise;

import java.util.HashMap;
import java.util.Map;

public class GroupRepository {

    private static Map<String, Group> groups = new HashMap<>();

    public static void persist(Group group) {
        groups.putIfAbsent(group.getId(), group);
    }

    public static Group get(String id) {
        return groups.get(id);
    }
}
