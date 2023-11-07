package object_orinted_design.rate_limiter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucketRateLimiter {

    private Map<String, Map<String, AtomicInteger>> minuteToTokenMap = new HashMap<>();

    private Map<String, Integer> clientsToLimitMap = new HashMap<>();

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public TokenBucketRateLimiter() {
        clientsToLimitMap.put("Ads", 100);
        clientsToLimitMap.put("Merch", 200);
        clientsToLimitMap.put("Search", 1200);
    }

    public boolean isAllowed(String client) {
        String key = df.format(Calendar.getInstance().getTime());
        minuteToTokenMap.putIfAbsent(key, new HashMap<>());
        minuteToTokenMap.get(key).putIfAbsent(client, new AtomicInteger(clientsToLimitMap.get(client)));
        return minuteToTokenMap.get(key).get(client).decrementAndGet() > 0;
    }

    public static void main(String[] args) {
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter();
        new Thread(new Client("Ads", rateLimiter)).start();
        new Thread(new Client("Merch", rateLimiter)).start();
        new Thread(new Client("Search", rateLimiter)).start();

    }

    public static class Client implements Runnable {

        private String client;

        private TokenBucketRateLimiter limiter;

        public Client(String cl, TokenBucketRateLimiter limiter) {
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
