package observer;

public interface Observer<T> {

    public void update(Observable<T> subject, T state);

}
