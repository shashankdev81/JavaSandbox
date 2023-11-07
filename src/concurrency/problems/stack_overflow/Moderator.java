package concurrency.problems.stack_overflow;

import java.util.Arrays;
import java.util.Set;

public class Moderator extends Member {


    public Moderator(String id, String name) {
        super(id, name);
    }

    public Set<Permission> getPermissions() {
        Set<Permission> permissions = super.getPermissions();
        permissions.addAll(Arrays.asList(new Permission[]{Permission.CLOSE_QUESTION, Permission.UNDELETE_QUESTION}));
        return permissions;

    }
}
