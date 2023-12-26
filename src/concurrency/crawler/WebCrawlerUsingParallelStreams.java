package concurrency.crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class WebCrawlerUsingParallelStreams {


    private Set<String> visitedUrls;
    private ExecutorService executor;

    public WebCrawlerUsingParallelStreams() {
        visitedUrls = new HashSet<>();
        executor = Executors.newFixedThreadPool(5); // You can adjust the thread pool size as needed
    }

    public void scrape(String[] inputUrls) {
        List<Future<Void>> futures = new ArrayList<>();

        for (String url : inputUrls) {
            Future<Void> future = executor.submit(() -> {
                scrapeUrl(url);
                return null;
            });

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

            // Use parallelStream to process other links concurrently
            otherLinks.parallelStream().forEach(this::scrapeUrl);
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

    public static void main(String[] args) {
        WebCrawlerUsingParallelStreams webScraper = new WebCrawlerUsingParallelStreams();

        // Example input URLs
        String[] inputUrls = {"https://example.com"};

        // Start the scraping process
        webScraper.scrape(inputUrls);
    }
}
