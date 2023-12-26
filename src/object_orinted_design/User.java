package object_orinted_design;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();
        this.id = userId;
        this.name = name;
        UserRepository.persist(this);
    }

}
