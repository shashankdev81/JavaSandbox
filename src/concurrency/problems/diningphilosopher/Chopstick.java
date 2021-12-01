package concurrency.problems.diningphilosopher;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

    private int id;

    private Lock lock = new ReentrantLock();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chopstick chopstick = (Chopstick) o;
        return id == chopstick.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Chopstick(int i) {
        this.id = i;
    }

    public boolean tryToPick() {
        return lock.tryLock();
    }

    public void putDown() {
        lock.unlock();
    }

}
