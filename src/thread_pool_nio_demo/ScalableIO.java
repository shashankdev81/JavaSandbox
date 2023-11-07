package thread_pool_nio_demo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


//https://medium.com/swlh/scaling-up-io-tasks-795df1e29d7e
public class ScalableIO {

    int dbCallCount;

    public static void main(String[] args) throws InterruptedException {
        var scalableIO = new ScalableIO();
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(300);
        for (int i = 0; i < 100; i++) {
            CompletableFuture.runAsync(scalableIO::dbCall1, executor);
            CompletableFuture.runAsync(scalableIO::dbCall2, executor);
            CompletableFuture.runAsync(scalableIO::dbCall3, executor);
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.MINUTES);
        System.out.printf("completed IO calls in %d ms\n", System.currentTimeMillis() - startTime);
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

            URL url = new URL("https://www.rediff.com/");

// Open a connection(?) on the URL(??) and cast the response(???)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

// Now it's "open", we can set the request method, headers etc.
            connection.setRequestProperty("accept", "application/json");

// This line makes the request
            InputStream responseStream = connection.getInputStream();
            String text = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
            //System.out.println(text);

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