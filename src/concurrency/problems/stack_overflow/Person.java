package concurrency.problems.stack_overflow;

import java.util.*;

public abstract class Person {

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;

    private String name;

    public Set<Permission> getPermissions() {
        return new HashSet<Permission>(Arrays.asList(new Permission[]{Permission.VIEW, Permission.SEARCH}));

    }
}
