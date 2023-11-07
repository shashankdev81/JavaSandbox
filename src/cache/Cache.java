package cache;

import java.util.ConcurrentModificationException;

public interface Cache<K, V> {

    /* put=add or get, remove and add*/
    public boolean put(K key, V value);

    /*get = get, remove and put*/
    public V get(K key);

    public V remove(K key) throws Exception;

}
