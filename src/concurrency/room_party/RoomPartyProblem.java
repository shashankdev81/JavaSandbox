package concurrency.room_party;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RoomPartyProblem {

    private Lock lock = new ReentrantLock();

    private Condition roomEmptyOrFullCondition = lock.newCondition();

    private Condition deanAway = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        RoomPartyProblem roomPartyProblem = new RoomPartyProblem();
        roomPartyProblem.doSomething();

    }

    public void doSomething() throws InterruptedException {
        Room room = new Room();
        Dean dean = new Dean(1);
        room.enter(dean);
        Thread.sleep((long) (3000 * Math.random()));
        room.leave(dean);
        for (int i = 0; i < 10; i++) {
            new Thread(new Student(i + 1, room)).start();
        }
        room.enter(dean);
        for (int i = 10; i < 19; i++) {
            new Thread(new Student(i + 1, room)).start();
        }
        Thread.sleep((long) (3000 * Math.random()));
        room.leave(dean);
    }

    private class Dean implements Runnable {

        private int id;

        public Dean(int i) {
            id = i;
        }

        @Override
        public void run() {

        }
    }

    private class Student implements Runnable {

        private int id;

        private Room room;

        public Student(int i, Room room) {
            this.id = i;
            this.room = room;
        }

        @Override
        public void run() {
            room.enter(this);
            try {
                Thread.sleep((long) (3000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            room.leave(this);
        }
    }

    private class Room {

        private Set<Student> students = new HashSet<>();

        private Dean dean;


        void enter(Student student) {
            lock.lock();
            while (true) {
                if (dean == null) {
                    students.add(student);
                    System.out.println("Student entered:" + student.id);
                    break;
                } else {
                    try {
                        deanAway.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            lock.unlock();
        }

        void enter(Dean dean) {
            lock.lock();
            while (true) {
                if (students.isEmpty()) {
                    this.dean = dean;
                    System.out.println("Dean entered:" + dean.id);
                    break;
                } else if (students.size() >= 50) {
                    this.dean = dean;
                    break;
                } else {
                    try {
                        roomEmptyOrFullCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            lock.unlock();
        }

        void leave(Student student) {
            lock.lock();
            students.remove(student);
            System.out.println("Student left:" + student.id);
            if (students.isEmpty()) {
                roomEmptyOrFullCondition.signal();
            }
            lock.unlock();
        }

        void leave(Dean dean) {
            lock.lock();
            while (true) {
                if (!students.isEmpty()) {
                    try {
                        roomEmptyOrFullCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    this.dean = null;
                    deanAway.signalAll();
                    System.out.println("Dean left:" + dean.id);
                    break;
                }
            }
            lock.unlock();
        }

    }
}
