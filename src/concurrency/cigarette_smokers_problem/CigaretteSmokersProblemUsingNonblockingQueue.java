package concurrency.cigarette_smokers_problem;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CigaretteSmokersProblemUsingNonblockingQueue {

    private ConcurrentLinkedQueue<INGREDIENT> tobaccoQueue;
    private ConcurrentLinkedQueue<INGREDIENT> paperQueue;
    private ConcurrentLinkedQueue<INGREDIENT> matchesQueue;

    public CigaretteSmokersProblemUsingNonblockingQueue() {
        Smoker smoker1 = new Smoker(INGREDIENT.TOBACCO);
        Smoker smoker2 = new Smoker(INGREDIENT.PAPER);
        Smoker smoker3 = new Smoker(INGREDIENT.MATCHES);
        tobaccoQueue = new ConcurrentLinkedQueue<>();
        paperQueue = new ConcurrentLinkedQueue<>();
        matchesQueue = new ConcurrentLinkedQueue<>();
        Agent agent = new Agent();
        new Thread(smoker1).start();
        new Thread(smoker2).start();
        new Thread(smoker3).start();
        new Thread(agent).start();

    }

    public static void main(String[] args) {
        CigaretteSmokersProblemUsingNonblockingQueue problem = new CigaretteSmokersProblemUsingNonblockingQueue();
    }


    private enum INGREDIENT {
        TOBACCO,
        PAPER,
        MATCHES,
        INVALID

    }

    private class Smoker implements Runnable {
        INGREDIENT ingredient;

        public Smoker(INGREDIENT ingredient) {
            this.ingredient = ingredient;
        }

        @Override
        public void run() {
            while (true) {
                fetchMissingIngredients(INGREDIENT.PAPER, tobaccoQueue, matchesQueue);
                fetchMissingIngredients(INGREDIENT.MATCHES, tobaccoQueue, paperQueue);
                fetchMissingIngredients(INGREDIENT.TOBACCO, matchesQueue, paperQueue);
                System.out.println("Smoker with " + ingredient + " able to smoke");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private void fetchMissingIngredients(INGREDIENT paper2, ConcurrentLinkedQueue<INGREDIENT> tobaccoQueue, ConcurrentLinkedQueue<INGREDIENT> matchesQueue) {
            if (ingredient.equals(paper2)) {
                INGREDIENT tobacco = tobaccoQueue.poll();
                INGREDIENT matches = matchesQueue.poll();
                if (tobacco == null && matches != null) {
                    matchesQueue.offer(matches);
                } else if (tobacco != null && matches == null) {
                    tobaccoQueue.offer(tobacco);
                } else {
                    System.out.println("Ingredients not available");
                }
            }
        }
    }

    private class Agent implements Runnable {


        @Override
        public void run() {
            while (true) {
                Random random = new Random();
                int ing = random.nextInt(3);
                INGREDIENT exclude = ing == 1 ? INGREDIENT.TOBACCO : (ing == 2 ? INGREDIENT.PAPER : INGREDIENT.MATCHES);
                System.out.println("Agent ingrdient excluded:" + exclude);
                if (exclude == INGREDIENT.TOBACCO) {
                    provide(paperQueue, INGREDIENT.PAPER, matchesQueue, INGREDIENT.MATCHES);
                } else if (exclude == INGREDIENT.PAPER) {
                    provide(tobaccoQueue, INGREDIENT.TOBACCO, matchesQueue, INGREDIENT.MATCHES);
                } else {
                    provide(tobaccoQueue, INGREDIENT.TOBACCO, paperQueue, INGREDIENT.PAPER);
                }
                System.out.println("Agent provided ingredients");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void provide(ConcurrentLinkedQueue<INGREDIENT> paperQueue, INGREDIENT paper, ConcurrentLinkedQueue<INGREDIENT> matchesQueue, INGREDIENT matches) {
            paperQueue.offer(paper);
            matchesQueue.offer(matches);
        }
    }
}

