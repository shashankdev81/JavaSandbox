package object_orinted_design.health_check;


import java.util.*;
import java.util.concurrent.*;

public class HealthCheckUrlsProblem {

    private static final int MAX_ATTEMPTS = 3;

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    private Queue<URL> delayQueue = new DelayQueue<URL>();

    private static List<String> successfullUrls = new ArrayList<>();

    private static List<String> failedUrls = new ArrayList<>();

    public static void main(String[] args) {
        HealthCheckUrlsProblem problem = new HealthCheckUrlsProblem();
        problem.performHealthCheck();
        Stack<String> st =new Stack<>();
    }

    private void performHealthCheck() {
        List<String> urls = fetchUrls();
        Queue<URL> delayQueue = new DelayQueue<URL>();
        for (String url : urls) {
            delayQueue.offer(new URL(url, 0));
        }
        while (!delayQueue.isEmpty()) {
            executor.submit(new InvokeUrl(delayQueue.poll()));
        }
        System.out.println("Successful urls=" + successfullUrls);
        System.out.println("Failed urls=" + failedUrls);
    }

    private List<String> fetchUrls() {
        return Arrays.asList(new String[]{"http://www.bbc.com", "http://www.rediff.com"});
    }

    private class InvokeUrl implements Runnable {

        private URL url;

        public InvokeUrl(URL url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                System.out.println("Will invoke " + url.url);
                if (url.url.contains("bbc")) {
                    throw new Exception("");
                } else {
                    Thread.sleep(1000);
                    successfullUrls.add(url.url);
                }
            } catch (Exception e) {
                if (url.attempts < MAX_ATTEMPTS) {
                    url.delayInMs = getDelayInMs();
                    url.attempts++;
                    delayQueue.offer(url);
                    System.out.println("Will retry " + url.url);
                } else {
                    failedUrls.add(url.url);
                }
            }
        }

        private long getDelayInMs() {
            return url.delayInMs + 1000 + System.currentTimeMillis();
        }
    }

    public static class URL implements Delayed {
        String url;
        long delayInMs;
        int attempts;

        public long getDelayInMs() {
            return delayInMs;
        }


        public URL(String url, long delay) {
            this.url = url;
            this.delayInMs = System.currentTimeMillis() + delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = delayInMs - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.delayInMs > ((URL) o).delayInMs) {
                return -1;
            }
            if (this.delayInMs < ((URL) o).delayInMs) {
                return 1;
            }
            return 0;
        }
    }
}



