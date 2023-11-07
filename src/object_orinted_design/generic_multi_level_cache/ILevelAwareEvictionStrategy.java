package object_orinted_design.generic_multi_level_cache;

public interface ILevelAwareEvictionStrategy<T> {

    public void evict(int level);

    //TODO should eviction strategy expose these abstractions?
    public void notifyKeyAccess(Key<T> key, int level);

    void notifyKeyRemoval(Key<T> key, int level);
}
