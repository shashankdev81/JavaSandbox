package object_orinted_design.multi_level_cache;

public interface ICache {

    public Result add(Key key, String value);

    public Result get(Key key);

    public Result remove(Key key);
}
