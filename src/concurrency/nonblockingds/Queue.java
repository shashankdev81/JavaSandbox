package concurrency.nonblockingds;

public interface Queue<T> {

    public void put(T val);

    public boolean tryPut(T val);

    public T dequeue();

    public T tryDequeue();

}
