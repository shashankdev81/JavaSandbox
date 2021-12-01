package concurrency.problems.diningphilosopher;

public class Philosopher implements Runnable {

    private int id;

    private Chopstick first;

    private Chopstick second;

    private volatile boolean isStop = false;

    public Philosopher(int id, Chopstick first, Chopstick second) {
        this.id = id;
        this.first = first;
        this.second = second;
    }

    private void eat() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("Philosopher " + id + " had some food");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void think() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println("Philosopher " + id + " did some thinking");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isStop = true;
    }

    @Override
    public void run() {
        while (!isStop) {
            boolean firstPicked = first.tryToPick();
            boolean secondPicked = second.tryToPick();
            if (firstPicked && secondPicked) {
                eat();
                first.putDown();
                second.putDown();
                think();
            } else {
                if (firstPicked) {
                    first.putDown();
                }
                if (secondPicked) {
                    second.putDown();
                }
            }
        }
    }
}
