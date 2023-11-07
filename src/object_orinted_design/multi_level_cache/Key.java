package object_orinted_design.multi_level_cache;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Key {

    private String key;
    private AtomicInteger hits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key1 = (Key) o;
        return Objects.equals(key, key1.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public Key(String key) {
        this.key = key;
        this.hits = new AtomicInteger();
    }

    public String get() {
        return key;
    }

    public int hits() {
        return hits.get();
    }

    public int recordHit() {
        return hits.incrementAndGet();
    }


}
