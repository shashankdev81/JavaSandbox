package thread_pool_nio_demo;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class ScalableNIO {

    int dbCallCount;

    public static void main(String[] args) throws InterruptedException {
        var scalableIO = new ScalableNIO();
        long startTime = System.currentTimeMillis();
        //ExecutorService executor = Executors.newFixedThreadPool(100);
        List<CompletableFuture<Response>> results = new ArrayList<CompletableFuture<Response>>();
        for (int i = 0; i < 100; i++) {
            results.add(genericIoCall(1));
            results.add(genericIoCall(2));
            results.add(genericIoCall(3));
        }

//        executor.shutdown();
//        executor.awaitTermination(2, TimeUnit.MINUTES);
        int total = 0;
        for (CompletableFuture<Response> r : results) {
            try {
                r.get();
                total++;
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("completed IO calls in %d ms\n", System.currentTimeMillis() - startTime);
    }

    private static CompletableFuture<Response> genericIoCall(int seconds) {
        CompletableFuture<Response> f = null;
        try {
            AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
            f = asyncHttpClient
                    .prepareGet("https://www.google.com/")
                    .execute()
                    .toCompletableFuture();
            //System.out.println(text);

            //System.out.printf("Completed db call #%d\n", ++dbCallCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

}