package concurrency.problems.stack_overflow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Member extends Person {

    public int getBadges() {
        return badges;
    }

    private int badges;

    public Member(String id, String name) {
        super(id, name);
    }

    public Set<Permission> getPermissions() {
        Set<Permission> permissions = super.getPermissions();
        permissions.addAll(Arrays.asList(new Permission[]{Permission.POST_QUESTION, Permission.UP_VOTE_ANSWER, Permission.COMMENT}));
        return permissions;

    }

    public void earn(int badge) {
        badges += badge;
    }
}
