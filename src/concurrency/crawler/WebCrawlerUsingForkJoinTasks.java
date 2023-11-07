package concurrency.crawler;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class WebCrawlerUsingForkJoinTasks {


    public static void main(String[] args) {
        WebCrawlTask tasks = new WebCrawlTask(3, Arrays.asList(new String[]{"https://example.com"}));
        System.out.println(tasks.compute());

    }

}
