package observer;

public interface Observable<T> {

    public void register(Observer<T> observer);

    public void deregister(Observer<T> observer);

    public void informObservers();


}
