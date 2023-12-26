package concurrency.cigarette_smokers_problem;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CigaretteSmokersProblemUsingSemaphores {

    private Semaphore tobaccoQueue;
    private Semaphore paperQueue;
    private Semaphore matchesQueue;
    private Supply supply;

    public CigaretteSmokersProblemUsingSemaphores() {
        Smoker smoker1 = new Smoker(INGREDIENT.TOBACCO);
        Smoker smoker2 = new Smoker(INGREDIENT.PAPER);
        Smoker smoker3 = new Smoker(INGREDIENT.MATCHES);
        tobaccoQueue = new Semaphore(1);
        paperQueue = new Semaphore(1);
        matchesQueue = new Semaphore(1);
        supply = null;
        Agent agent = new Agent();
        new Thread(smoker1).start();
        new Thread(smoker2).start();
        new Thread(smoker3).start();
        new Thread(agent).start();

    }

    public static void main(String[] args) {
        CigaretteSmokersProblemUsingSemaphores problem = new CigaretteSmokersProblemUsingSemaphores();
    }


    private enum INGREDIENT {
        TOBACCO,
        PAPER,
        MATCHES,
        INVALID

    }

    private class Supply {
        INGREDIENT first;
        INGREDIENT second;


        public Supply(INGREDIENT f, INGREDIENT s) {
            this.first = f;
            this.second = s;
        }
    }

    private class Smoker implements Runnable {
        INGREDIENT ingredient;

        public Smoker(INGREDIENT ingredient) {
            this.ingredient = ingredient;
        }

        @Override
        public void run() {
            while (true) {
                fetchMissingIngredients(INGREDIENT.MATCHES, tobaccoQueue, paperQueue);
                fetchMissingIngredients(INGREDIENT.PAPER, tobaccoQueue, matchesQueue);
                fetchMissingIngredients(INGREDIENT.TOBACCO, matchesQueue, paperQueue);
                System.out.println("Smoker with " + ingredient + " able to smoke");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        private void fetchMissingIngredients(INGREDIENT exists, Semaphore ingredient1, Semaphore ingredient2) {
            if (ingredient.equals(exists)) {
                while (!(ingredient1.tryAcquire(1) && ingredient2.tryAcquire(1))) {
                    ingredient1.release(1);
                    ingredient2.release(1);
                }
                supply = null;
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
                    provide(INGREDIENT.PAPER, INGREDIENT.MATCHES);
                } else if (exclude == INGREDIENT.PAPER) {
                    provide(INGREDIENT.TOBACCO, INGREDIENT.MATCHES);
                } else {
                    provide(INGREDIENT.TOBACCO, INGREDIENT.PAPER);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void provide(INGREDIENT ingredient1, INGREDIENT ingredient2) {
            supply = new Supply(ingredient1, ingredient2);
            System.out.println("Agent provided ingredients:" + ingredient1 + "," + ingredient2);
        }
    }
}

