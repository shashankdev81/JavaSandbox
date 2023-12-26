package leetcode2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class HierarchicalLoanManagment {

    private static Map<String, Person> personMap = new HashMap<>();

    private static Person dhriti;

    public HierarchicalLoanManagment() {
        Person person1 = new Person("BS Baravani", 100);
        Person person2 = new Person("NB Baravani", 100);
        Person person3 = new Person("Shashank Baravani", 100);
        Person person4 = new Person("Divya Baravani", 100);
        Person person5 = new Person("Ramesh Bhat", 100);
        Person person6 = new Person("Geeta Bhat", 100);
        personMap.put(person1.name, person1);
        personMap.put(person2.name, person2);
        personMap.put(person3.name, person3);
        personMap.put(person4.name, person4);
        personMap.put(person5.name, person5);
        personMap.put(person6.name, person6);

        dhriti = new Person("Dhriti Baravani", 100);
        dhriti.parents.add(person3);
        dhriti.parents.add(person4);
        person3.parents.add(person1);
        person3.parents.add(person2);
        person4.parents.add(person5);
        person4.parents.add(person6);

    }

    public static void main(String[] args) {
        HierarchicalLoanManagment hierarchicalLoanManagment = new HierarchicalLoanManagment();
        List<Pair<String, Integer>> liabilities = dhriti.borrow(1000);
        print(liabilities);
        liabilities = dhriti.borrow(500);
        print(liabilities);
        liabilities = dhriti.borrow(100);
        print(liabilities);

    }

    private static void print(List<Pair<String, Integer>> liabilities) {
        System.out.println(liabilities.stream().map(l -> "[" + l.key + "-" + l.value + "]").collect(
            Collectors.toList()));
        System.out.println(
            personMap.values().stream().map(p -> "[" + p.name + "-" + p.balance + "]").collect(
                Collectors.toList()));
    }

    public class Person {

        String name;

        List<Person> children = new ArrayList<>();

        List<Person> parents = new ArrayList<>();

        int balance;

        int blocked;

        Lock lock = new ReentrantLock();

        public Person(String name, int money) {
            this.name = name;
            this.balance = money;
        }

        public List<Pair<String, Integer>> borrow(int amount) {
            List<Pair<String, Integer>> liabilities = new ArrayList<>();
            int shortFall = amount;

            if (this.balance > amount) {
                this.balance -= amount;
                return liabilities;
            } else {
                shortFall = amount - this.balance;
            }
            Queue<Person> queue = new ArrayDeque<>();
            queue.addAll(parents);
            while (!queue.isEmpty() && shortFall > 0) {
                int lenders = queue.size();
                boolean isConflict = false;
                while (lenders > 0) {
                    Person lender = queue.poll();
                    if (!lender.lock.tryLock()) {
                        isConflict = true;
                        break;
                    }
                    if (lender.balance > shortFall) {
                        lender.balance -= shortFall;
                        liabilities.add(new Pair<String, Integer>(lender.name, shortFall));
                    } else {
                        shortFall = shortFall - lender.balance;
                        liabilities.add(new Pair<String, Integer>(lender.name, lender.balance));
                        lender.balance = 0;
                    }
                    if (shortFall == 0) {
                        return liabilities;
                    }
                    queue.addAll(lender.parents);
                    lender.lock.unlock();
                    lenders--;
                }
                if (isConflict) {
                    break;
                }
            }
            for (Pair<String, Integer> liability : liabilities) {
                Person lender = personMap.get(liability.key);
                lender.balance += liability.value;
            }
            liabilities.clear();
            return liabilities;
        }

    }

    class Pair<K, V> {

        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


}
