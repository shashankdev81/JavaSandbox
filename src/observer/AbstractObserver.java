package observer;

public class AbstractObserver<T> implements Observer<T> {

    private String name;

    public AbstractObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable<T> subject, T state) {
        AbstractObservable<T> observable = (AbstractObservable<T>) subject;
        System.out.println(name + " has recevied update from " + observable.getName() + "," + state);
    }
}
