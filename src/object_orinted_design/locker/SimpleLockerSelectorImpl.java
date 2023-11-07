package object_orinted_design.locker;

import object_orinted_design.User;
import object_orinted_design.UserRepository;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class SimpleLockerSelectorImpl implements ILockerSelector {

    private Queue<Locker> lockerPool = new LinkedBlockingDeque<>();

    private Map<User, Locker> userToLockerMap = new ConcurrentHashMap<>();

    public SimpleLockerSelectorImpl() {
        for (Locker locker : LockerRepository.getAll()) {
            lockerPool.offer(locker);
        }
    }

    @Override
    public Locker request(Criteria criteria) {
        Locker locker = lockerPool.poll();
        return locker;
    }

    @Override
    public boolean assign(String userid, String lockerId) {
        userToLockerMap.putIfAbsent(UserRepository.get(userid), LockerRepository.get(lockerId));
        return true;
    }


    @Override
    public void release(String userId, String lockerId) {
        userToLockerMap.remove(UserRepository.get(userId));
        Locker locker = LockerRepository.get(lockerId);
        lockerPool.offer(locker);
        locker.close();
    }
}
