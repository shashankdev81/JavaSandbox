package observer;

import java.util.ArrayList;
import java.util.List;

public class AbstractObservable<T> implements Observable<T> {

    public String getName() {
        return name;
    }

    private String name;

    private T state;

    private volatile boolean isChanged;

    private List<Observer<T>> subscribers = new ArrayList<Observer<T>>();

    public AbstractObservable(String name) {
        this.name = name;
    }

    @Override
    public void register(Observer<T> observer) {
        subscribers.add(observer);
    }

    @Override
    public void deregister(Observer<T> observer) {
        subscribers.remove(observer);
    }

    @Override
    public void informObservers() {
        if (isChanged) {
            subscribers.forEach(s -> s.update(this, state));
        }
        isChanged = false;
    }

    public void change(T newState) {
        this.state = newState;
        isChanged = true;

    }
}
