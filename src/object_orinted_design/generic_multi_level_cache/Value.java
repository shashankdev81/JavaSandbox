package object_orinted_design.generic_multi_level_cache;

public class Value<T> {
    private T value;

    public Double getTimeToAccess() {
        return Double.valueOf(timeToAccess / 1000);
    }

    public void setTimeToAccess(Long timeToAccess) {
        this.timeToAccess = timeToAccess;
    }

    private Long timeToAccess;

    public T get() {
        return value;
    }

    public Value(T value, Long timeToAccess) {
        this.value = value;
        this.timeToAccess = timeToAccess;
    }

    public Value(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" + value + '}';
    }
}
