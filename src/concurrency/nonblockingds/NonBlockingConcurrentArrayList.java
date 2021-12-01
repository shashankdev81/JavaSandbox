package concurrency.nonblockingds;

import java.lang.reflect.Array;

public class NonBlockingConcurrentArrayList<T> {

    private T[] array;

    public NonBlockingConcurrentArrayList(T[] array) {
        this.array = array;
    }

    public NonBlockingConcurrentArrayList<T> add(T obj) {
        T[] newArray = (T[]) Array.newInstance(obj.getClass(), array.length);
        int i = 0;
        for (; i < array.length; i++) {
            newArray[i] = array[i];
        }
        newArray[i] = obj;
        return new NonBlockingConcurrentArrayList<T>(newArray);
    }

    public NonBlockingConcurrentArrayList<T> remove(T obj) {
        T[] newArray = (T[]) Array.newInstance(obj.getClass(), array.length);
        int i = 0;
        int k = 0;
        for (; i < array.length; i++, k++) {
            if (!array[i].equals(obj))
                newArray[k] = array[i];
        }
        return new NonBlockingConcurrentArrayList(newArray);
    }
}
