package cache;

public interface IEvictionStrategy<K, V> {

    public void evict();
}
