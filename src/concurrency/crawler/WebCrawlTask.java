package concurrency.crawler;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class WebCrawlTask extends RecursiveTask<List<String>> {

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

    List<String> urls;
    int k = 0;

    public WebCrawlTask(int k, List<String> crawled) {
        this.k = k;
        this.urls = crawled;
    }

    @Override
    protected List<String> compute() {
        if (k > 0) {
            return ForkJoinTask.invokeAll(getSubTasks(urls)).stream().map(t -> t.join()).flatMap(l -> l.stream()).collect(Collectors.toList());
        } else {
            return urls;
        }
    }

    private List<ForkJoinTask<List<String>>> getSubTasks(List<String> urls) {
        return urls.stream().map(url -> new WebCrawlTask(k - 1, new WebCrawler.AsyncHtmlCient().getUrls(url).join())).collect(Collectors.toList());
//        List<String> urlsList = sampleUrls.stream().map(u -> u + "/" + UUID.randomUUID()).collect(Collectors.toList());
//        return urls.stream().map(url -> new WebCrawlTask(k - 1, urlsList)).collect(Collectors.toList());

    }


}
