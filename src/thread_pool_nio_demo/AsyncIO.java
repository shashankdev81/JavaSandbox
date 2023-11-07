package thread_pool_nio_demo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

public class AsyncIO {

    int dbCallCount;

    public static void main(String[] args) throws InterruptedException {
        var asyncIO = new AsyncIO();
        long startTime = currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            CompletableFuture.runAsync(asyncIO::dbCall1);
            CompletableFuture.runAsync(asyncIO::dbCall2);
            CompletableFuture.runAsync(asyncIO::dbCall3);
        }
        ForkJoinPool.commonPool().awaitTermination(2, TimeUnit.MINUTES);
        System.out.println("Paralellism="+ForkJoinPool.getCommonPoolParallelism());
        System.out.printf("completed IO calls in %d ms\n", currentTimeMillis() - startTime);
    }

    private void genericDbCall(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
            System.out.printf("Completed db call #%d\n", ++dbCallCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void genericIoCall(int seconds) {
        try {
            //Thread.sleep(seconds * 1000);
            // Create a neat value object to hold the URL

            URL url = new URL("https://www.google.com/");

// Open a connection(?) on the URL(??) and cast the response(???)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

// Now it's "open", we can set the request method, headers etc.
            connection.setRequestProperty("accept", "application/json");

// This line makes the request
            InputStream responseStream = connection.getInputStream();
            String text = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
           // System.out.println(text);

            //System.out.printf("Completed db call #%d\n", ++dbCallCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dbCall1() {
        genericIoCall(1);
    }

    public void dbCall2() {
        genericIoCall(2);
    }

    public void dbCall3() {
        genericIoCall(3);
    }
}