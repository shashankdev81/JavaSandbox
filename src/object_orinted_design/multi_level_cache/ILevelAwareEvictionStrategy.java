package object_orinted_design.multi_level_cache;

public interface ILevelAwareEvictionStrategy {

    public void evict(int level);

    //TODO should eviction strategy expose these abstractions?
    public void notifyKeyAccess(Key key, int level);

    void notifyKeyRemoval(Key key, int level);
}
