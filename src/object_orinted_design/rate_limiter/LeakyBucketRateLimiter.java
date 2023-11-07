package object_orinted_design.rate_limiter;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.DelayQueue;

public class LeakyBucketRateLimiter {

    private Map<String, DelayQueue<Delay>> clientsToBucketMap = new HashMap<>();

    private Map<String, Integer> clientsToLimitMap = new HashMap<>();

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public LeakyBucketRateLimiter() {
        clientsToLimitMap.put("Ads", 100);
        clientsToLimitMap.put("Merch", 200);
        clientsToLimitMap.put("Search", 1200);
        clientsToBucketMap.put("Ads", getQueue("Ads"));
        clientsToBucketMap.put("Merch", getQueue("Merch"));
        clientsToBucketMap.put("Search", getQueue("Search"));
    }

    public boolean isAllowed(String client) {
        try {
            clientsToBucketMap.get(client).take();
            var timeObject = new Date();
            timeObject.setSeconds(timeObject.getSeconds() + 1);
            clientsToBucketMap.get(client).add(new Delay(timeObject.getTime()));
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    private DelayQueue<Delay> getQueue(String client) {
        var timeObject = new Date();
        DelayQueue<Delay> queue = new DelayQueue<Delay>();
        int rate = clientsToLimitMap.get(client) / 60;
        for (int i = 0; i < clientsToLimitMap.get(client); i++) {
            timeObject.setSeconds(timeObject.getSeconds() + rate);
            queue.add(new Delay(timeObject.getTime()));
        }
        return queue;
    }

    public static void main(String[] args) {
        LeakyBucketRateLimiter rateLimiter = new LeakyBucketRateLimiter();
        new Thread(new Client("Ads", rateLimiter)).start();
        new Thread(new Client("Merch", rateLimiter)).start();
        new Thread(new Client("Search", rateLimiter)).start();
    }

    public static class Client implements Runnable {

        private String client;

        private LeakyBucketRateLimiter limiter;

        public Client(String cl, LeakyBucketRateLimiter limiter) {
            this.client = cl;
            this.limiter = limiter;
        }

        @Override
        public void run() {
            try {
                for (int i = -0; i < 1000; i++) {
                    System.out.println("Client try to hit api=" + client + "," + limiter.isAllowed(client));
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
