package concurrency.problems.diningphilosopher;

import java.util.Arrays;

public class MainClass {

    private static int SIZE = 5;

    public static void main(String[] args) throws InterruptedException {

        Chopstick[] chopsticks = new Chopstick[SIZE];
        for (int i = 0; i < SIZE; i++) {
            chopsticks[i] = new Chopstick(i);
        }
        Philosopher[] philosophers = new Philosopher[SIZE];
        for (int i = 0; i < SIZE; i++) {
            int ind = (i + 1) % SIZE;
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[ind]);
        }

        Arrays.stream(philosophers).forEach(p -> new Thread(p).start());
        Thread.sleep(10000);
        Arrays.stream(philosophers).forEach(p -> p.stop());
        System.out.println("Dinner done");
    }
}
