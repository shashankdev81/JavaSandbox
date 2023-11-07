package object_orinted_design.generic_multi_level_cache;

public interface IStorage {
    public void save(Key key, Value value, int level);

    public Value retrieve(Key key, int level);

    public Value purge(Key key, int level);
}
