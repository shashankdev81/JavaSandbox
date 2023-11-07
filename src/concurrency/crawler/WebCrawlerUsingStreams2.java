package concurrency.crawler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * // This is the HtmlParser's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface HtmlParser {
 * public List<String> getUrls(String url) {}
 * }
 */

class WebCrawlerUsingStreams2 {


    private String START_URL_HOST;

    private AtomicInteger depth = new AtomicInteger(0);

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        START_URL_HOST = startUrl.substring(7, startUrl.length()).split("/")[0];
        List<String> crawledUrls = crawlWithParallelStream(startUrl, htmlParser).collect(Collectors.toList());
        return crawledUrls;
    }


    private Stream<String> crawlWithParallelStream(String startUrl, HtmlParser htmlParser) {
        //crawledUrls.put(startUrl, Boolean.TRUE);
        Stream<String> stream = fetchUrls(startUrl, htmlParser)
                .parallelStream()
                .flatMap(url -> crawlWithParallelStream(url, htmlParser));
        return Stream.concat(Stream.of(startUrl), stream);

    }

    private List<String> fetchUrls(String startUrl, HtmlParser htmlParser) {
        try {
            System.out.println("Will fetch urls: " + Thread.currentThread().getName());
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return htmlParser.getUrls(startUrl);
    }


    private static class HtmlParser {

        private int ind = 1;
        private static List<String> sampleUrls = Arrays.asList(new String[]{"http://news.yahoo.com",
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
                "http://news.yahoo.com/news/topics/",
                "http://news.yahoo.com/us"});

        public List<String> getUrls(String startUrl) {
            List<String> res = ind > 10 ? new ArrayList<String>() : sampleUrls.subList(ind, ind + 3);
            ind += 3;
            return res;
        }

        public List<String> getAllUrls() {
            return sampleUrls;
        }

    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        WebCrawlerUsingStreams2 webCrawler = new WebCrawlerUsingStreams2();
        HtmlParser htmlParser = new HtmlParser();
        List<String> crawledUrls = webCrawler.crawl("http://news.yahoo.com", htmlParser);
        System.out.println("crawledUrls=" + crawledUrls);
        //System.out.println("crawledUrls size=" + crawledUrls.size() + ", sample urls=" + htmlParser.getAllUrls().size());
//        boolean isCrawledAll = htmlParser.getAllUrls().stream().allMatch(url -> crawledUrls.contains(url));
//        long end = System.currentTimeMillis();
//        System.out.println("isCrawledAlldddd=" + isCrawledAll + ", time taken=" + (end - start));
    }

}