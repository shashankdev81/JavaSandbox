package object_orinted_design.multi_level_cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MultiLevelCache implements ICache {

    private Map<String, String> keyLock;

    private Map<Integer, Map<Key, String>> cache;

    private ILevelAwareEvictionStrategy evictionStrategy;


    private int LEVELS;

    public MultiLevelCache(int cacheLevels, int[] capacity, int[] writeTimes, int[] readTimes) {
        keyLock = new ConcurrentHashMap<>();
        cache = new ConcurrentHashMap<>();
        LEVELS = cacheLevels;
        for (int level = 0; level < LEVELS; level++) {
            cache.put(level, new ConcurrentHashMap<>(capacity[level]));
        }
        evictionStrategy = new LowestHitsEvictionStrategy();
    }

    @Override
    public Result add(Key key, String value) {
        return add(key, value, LEVELS);
    }

    public Result add(Key key, String value, int levelsAbove) {
        long start = System.currentTimeMillis();
        String lockVal = Thread.currentThread().getName() + "-" + start;
        boolean isLocked = false;
        String keyStr = key.get();
        do {
            isLocked = keyLock.putIfAbsent(keyStr, lockVal) == lockVal;
        } while (!isLocked);
        for (int level = levelsAbove - 1; level >= 0; level--) {
            cache.get(level).put(key, value);
        }
        keyLock.remove(keyStr);
        evictionStrategy.notifyKeyAccess(key, -1);
        long end = System.currentTimeMillis();
        long time = end - start;
        return new Result(value, time);
    }

    @Override
    public Result get(Key key) {
        long start = System.currentTimeMillis();
        String value = null;
        int levelFound = LEVELS - 1;
        for (int level = 0; level < LEVELS; level++) {
            value = cache.get(level).get(key);
            if (value != null) {
                levelFound = level;
                break;
            }
        }
        add(key, value, levelFound);
        evictionStrategy.notifyKeyAccess(key, levelFound);
        long end = System.currentTimeMillis();
        long time = end - start;
        return new Result(value, time);
    }

    @Override
    public Result remove(Key key) {
        return purge(key, -1);
    }

    private Result purge(Key key, int purgeLevel) {
        long start = System.currentTimeMillis();
        String lockVal = Thread.currentThread().getName() + "-" + start;
        boolean isLocked = false;
        String result = null;
        String keyStr = key.get();
        do {
            isLocked = keyLock.putIfAbsent(keyStr, lockVal) == lockVal;
        } while (!isLocked);
        if (purgeLevel == -1) {
            for (int level = 0; level < LEVELS; level++) {
                String v = cache.get(level).remove(key);
                result = v == null ? result : v;
            }
        } else {
            String v = cache.get(purgeLevel).remove(key);
            result = v == null ? result : v;
        }
        keyLock.remove(keyStr);
        evictionStrategy.notifyKeyRemoval(key, purgeLevel);
        long end = System.currentTimeMillis();
        long time = end - start;
        return new Result(result, time);
    }

    private class LowestHitsEvictionStrategy implements ILevelAwareEvictionStrategy {

        //TODO bucketise eviction
        Map<Integer, TreeMap<Integer, List<String>>> hitsMap = new HashMap<>();

        public LowestHitsEvictionStrategy() {
            for (int i = 0; i < LEVELS; i++) {
                this.hitsMap.put(i, new TreeMap<>());
            }
        }

        @Override
        public void evict(int level) {
            String evictKey = hitsMap.get(level).firstEntry().getValue().get(0);
            purge(new Key(evictKey), level);
        }

        @Override
        public void notifyKeyAccess(Key key, int level) {
            hitsMap.get(key.hits()).remove(key.get());
            int hits = key.recordHit();
            if (level == -1) {
                hitsMap.get(level).putIfAbsent(hits, new ArrayList<>());
                hitsMap.get(level).get(hits).add(key.get());
            } else {
                for (int i = 0; i < LEVELS; i++) {
                    hitsMap.get(i).putIfAbsent(hits, new ArrayList<>());
                    hitsMap.get(i).get(hits).add(key.get());
                }
            }

        }

        @Override
        public void notifyKeyRemoval(Key key, int level) {
            hitsMap.get(level).get(key.hits()).remove(key.get());
        }
    }
}
