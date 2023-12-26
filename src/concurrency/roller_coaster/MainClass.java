package concurrency.roller_coaster;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClass {

    private static boolean isStop = true;

    RollerCoaster rollerCoaster = new RollerCoaster(3);

    public MainClass() throws InterruptedException {
        for (int i = 0; i < 6; i++) {
            new Thread(new Passenger(i, rollerCoaster)).start();
        }
        new Thread(new Car(rollerCoaster)).start();
    }

    public static void main(String[] args) throws InterruptedException {
        MainClass mainClass = new MainClass();
        isStop = false;
        Thread.sleep(10000);
        isStop = true;
    }

    private class Passenger implements Runnable {

        private RollerCoaster rollerCoaster;

        private int paxNo;

        public Passenger(int i, RollerCoaster rollerCoaster) {
            this.rollerCoaster = rollerCoaster;
            this.paxNo = i;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                rollerCoaster.board();
                Thread.sleep(1000);
                rollerCoaster.unboard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Car implements Runnable {

        private RollerCoaster rollerCoaster;

        public Car(RollerCoaster rollerCoaster) {
            this.rollerCoaster = rollerCoaster;
        }

        @Override
        public void run() {
            try {
                while (!isStop) {
                    rollerCoaster.load();
                    rollerCoaster.run();
                    rollerCoaster.unload();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
