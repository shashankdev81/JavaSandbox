package object_orinted_design.locker;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockerRepository {

    private static Map<String, Locker> lockers = new ConcurrentHashMap<>();

    public static void persist(Locker locker) {
        lockers.putIfAbsent(locker.getId(), locker);
    }

    public static Locker get(String lockerId) {
        return lockers.get(lockerId);
    }

    public static Collection<Locker> getAll() {
        return lockers.values();
    }
}
