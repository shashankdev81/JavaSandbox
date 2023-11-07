package concurrency.crawler;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class WebCrawler {

    private static String ROOT = "http://news.yahoo.com/";

    private AsyncHtmlCient client = new AsyncHtmlCient();

    private static List<String> crawledUrls = new ArrayList<>();

    private static AtomicInteger count = new AtomicInteger();

    static Executor multiThreadedExecutor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        WebCrawler crawler = new WebCrawler();
        List<CompletableFuture<Void>> futures = crawler.crawl(WebCrawler.ROOT, 3);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println(crawledUrls);
    }

    public List<CompletableFuture<Void>> crawl(String url, int level) {
        List<CompletableFuture<Void>> crawled = new ArrayList<>();
        if (level != 0) {
            crawled.add(client.getUrls(url).thenCompose(fetchedUrls -> {
                crawledUrls.addAll(fetchedUrls);
                List<CompletableFuture<Void>> results = new ArrayList<>();
                for (String fetchedUrl : fetchedUrls) {
                    results.addAll(crawl(fetchedUrl, level - 1));
                }
                CompletableFuture<Void> future = CompletableFuture.allOf(results.toArray(new CompletableFuture[0]));
                future.join();
                return future;
            }));
        }
        CompletableFuture.allOf(crawled.toArray(new CompletableFuture[0])).join();
        return crawled;
    }


    public static class AsyncHtmlCient {

        private int ind = 0;
        private List<String> sampleUrls = Arrays.asList(new String[]{"http://news.yahoo.com",
                "http://news.yahoo.com/news",
                "http://news.yahoo.com/a",
                "http://news.yahoo.com/b",
                "http://news.yahoo.com/c",
                "http://news.yahoo.com/d",
                "http://news.yahoo.com/e",
                "http://news.yahoo.com/f",
                "http://news.yahoo.com/g",
                "http://news.yahoo.com/h",
                "http://news.yahoo.com/k",
                "http://news.yahoo.com/news/topics",
                "http://news.yahoo.com/us"});

        public CompletableFuture<List<String>> getUrls(String startUrl) {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep((long) (10 * Math.random()));
                    System.out.println("Crawl no:" + count.incrementAndGet() + ", Thread=" + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return sampleUrls.stream().map(u -> u + "/" + UUID.randomUUID()).collect(Collectors.toList());
            }, multiThreadedExecutor);
            return future;
        }
    }
}
