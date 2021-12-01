package observer;

public class ObserverTest {

    public static void main(String[] args) {

        AbstractObserver<News> ndtv = new AbstractObserver<News>("ndtv");
        AbstractObserver<News> republic = new AbstractObserver<News>("republic");
        AbstractObserver<News> cnn = new AbstractObserver<News>("cnn");

        AbstractObservable<News> reuters = new AbstractObservable<News>("reuters");
        AbstractObservable<News> ani = new AbstractObservable<News>("ani");

        reuters.register(ndtv);
        reuters.register(republic);
        ani.register(cnn);
        ani.register(republic);

        ani.change(new News("India beats Aus in cricket"));
        reuters.change(new News("Brazil wins football world cup"));
        ani.informObservers();
        reuters.informObservers();
    }
}
