package concurrency.nonblockingds;

public interface Stack<T> {

    public void push(T val);

    public boolean tryPush(T val);

    public T pop();

    public T tryPop();

}
