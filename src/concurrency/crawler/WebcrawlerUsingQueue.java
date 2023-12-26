package concurrency.crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class WebcrawlerUsingQueue {


    private Set<String> visitedUrls;
    private ExecutorService executor;
    private BlockingQueue<String> urlQueue;
    private volatile boolean isStop = false;

    public WebcrawlerUsingQueue() {
        visitedUrls = new HashSet<>();
        executor = Executors.newFixedThreadPool(5); // You can adjust the thread pool size as needed
        urlQueue = new LinkedBlockingQueue<>();
    }

    public void scrape(String[] inputUrls) {
        for (String url : inputUrls) {
            urlQueue.offer(url);
        }

        List<Future<Void>> futures = new ArrayList<>();

        // Create multiple consumers (threads) to process URLs concurrently
        for (int i = 0; i < 5; i++) {
            Future<Void> future = executor.submit(new Scraper());
            futures.add(future);
        }

        // Wait for all tasks to complete
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shut down the executor service
        executor.shutdown();
    }

    public void stop() {
        isStop = true;
    }

    private void scrapeUrl(String url) {
        if (!visitedUrls.contains(url)) {
            visitedUrls.add(url);

            // Fetch HTML content from the specified URL
            String htmlContent = fetchHtmlContent(url);

            // Extract image links from the HTML content
            Set<String> imageLinks = extractImageLinks(htmlContent);

            // Save image links (You can store them in a database, file, or any other storage mechanism)
            saveImageLinks(url, imageLinks);

            // Extract other links from the HTML content
            Set<String> otherLinks = extractOtherLinks(htmlContent);

            // Add other links to the queue for further processing
            for (String otherLink : otherLinks) {
                urlQueue.offer(otherLink);
            }
        }
    }

    private String fetchHtmlContent(String url) {
        // Implement logic to fetch HTML content from the specified URL
        // (e.g., using a library like HttpClient in Java)
        return ""; // Placeholder for actual implementation
    }

    private Set<String> extractImageLinks(String htmlContent) {
        // Implement logic to extract image links from HTML content
        // (e.g., using a library like Jsoup in Java)
        return new HashSet<>(); // Placeholder for actual implementation
    }

    private Set<String> extractOtherLinks(String htmlContent) {
        // Implement logic to extract other links from HTML content
        // (e.g., using a library like Jsoup in Java)
        return new HashSet<>(); // Placeholder for actual implementation
    }

    private void saveImageLinks(String url, Set<String> imageLinks) {
        // Implement logic to save image links
        // (e.g., store them in a database, file, or any other storage mechanism)
    }

    public class Scraper implements Callable {


        @Override
        public Object call() throws Exception {
            while (!isStop) {
                String url = urlQueue.poll();
                if (url == null) {
                    break; // No more URLs to process
                }
                scrapeUrl(url);
            }
            return null;
        }
    }

    public static void main(String[] args) {
        WebcrawlerUsingQueue webScraper = new WebcrawlerUsingQueue();

        // Example input URLs
        String[] inputUrls = {"https://example.com"};

        // Start the scraping process
        webScraper.scrape(inputUrls);
    }
}
