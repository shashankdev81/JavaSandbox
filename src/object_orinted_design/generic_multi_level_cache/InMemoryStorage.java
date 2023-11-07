package object_orinted_design.generic_multi_level_cache;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStorage implements IStorage {

    private Map<Key, String> keyLock;

    private Map<Integer, Map<Key, Value>> cache;

    private int LEVELS;

    public InMemoryStorage(int cacheLevels, int[] capacity) {
        cache = new ConcurrentHashMap<>();
        LEVELS = cacheLevels;
        for (int level = 0; level < LEVELS; level++) {
            cache.put(level, new ConcurrentHashMap<>(capacity[level]));
        }

    }

    @Override
    public void save(Key key, Value value, int level) {
        cache.get(level).put(key, value);
    }

    @Override
    public Value retrieve(Key key, int level) {
        return cache.get(level).get(key);
    }

    @Override
    public Value purge(Key key, int level) {
        return cache.get(level).remove(key);
    }
}
