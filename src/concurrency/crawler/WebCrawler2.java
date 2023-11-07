package concurrency.crawler;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler2 {
    private static final String ROOT_URL = "https://example.com"; // Replace with the starting URL
    private static final int MAX_DEPTH = 3; // Maximum depth of crawling
    private static final int MAX_CONCURRENT_REQUESTS = 10; // Maximum concurrent requests

    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
    private final Executor executor = Executors.newFixedThreadPool(MAX_CONCURRENT_REQUESTS);
    private final AtomicInteger depthCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        crawler.crawl(ROOT_URL, 0);
    }

    public void crawl(String url, int depth) {
        if (depth > MAX_DEPTH || !visitedUrls.add(url)) {
            return;
        }

        CompletableFuture<Void> pageFuture = CompletableFuture.supplyAsync(() -> {
            try {
                String content = fetchContent(url);
                processPageContent(content, depth);

                Set<String> newUrls = extractLinks(content);

                CompletableFuture<Void>[] subCrawlFutures = newUrls.stream()
                        .map(newUrl -> CompletableFuture.runAsync(() -> crawl(newUrl, depth + 1), executor))
                        .toArray(CompletableFuture[]::new);

                CompletableFuture<Void> allSubCrawls = CompletableFuture.allOf(subCrawlFutures);
                allSubCrawls.join();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }, executor);

        pageFuture.join(); // Wait for the page to be processed
    }

    private String fetchContent(String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        }
    }

    private Set<String> extractLinks(String content) {
        // Implement your own logic to extract links from HTML content
        // You can use regular expressions, HTML parsing libraries, or other methods
        // This is just a simplified example
        // Be careful, as HTML parsing can be complex and error-prone
        Set<String> newUrls = new HashSet<>(); // Extract new URLs
        return newUrls;
    }

    private void processPageContent(String content, int depth) {
        // Implement your logic to process the content of the page
        System.out.println("Depth " + depth + ": Content length " + content.length());
    }
}
