package object_orinted_design.generic_multi_level_cache;

public interface ICache<T> {

    public Value<T> add(Key<T> key, Value<T> value);

    public Value<T> get(Key<T> key);

    public Value<T> remove(Key<T> key);
}
