package generic_pubsub;

import java.util.*;

public class MainClass2 {

    public static void main(String[] args) throws InterruptedException {
        GenericBroker<Headlines> broker = new GenericBroker(2);
        broker.start();

        GenericPublisher<Headlines> news = new GenericPublisher<Headlines>("News", Arrays.asList(new String[]{"Politics", "Sports", "Economy"}));
        GenericPublisher<Headlines> music = new GenericPublisher<Headlines>("Music", Arrays.asList(new String[]{"Pop", "Rock", "Metal"}));
        GenericPublisher<Headlines> money = new GenericPublisher<Headlines>("Money", Arrays.asList(new String[]{"finance", "stocks", "market"}));

        broker.registerPublisher(news, news.getTopics());
        broker.registerPublisher(music, music.getTopics());
        broker.registerPublisher(money, money.getTopics());

        GenericSubcriber<Headlines> myApp = new GenericSubcriber<Headlines>("MyApp", Arrays.asList(new String[]{"Politics", "Pop", "finance"}));
        GenericSubcriber<Headlines> myWebsite = new GenericSubcriber<Headlines>("MyWebsite", Arrays.asList(new String[]{"Sports", "Rock", "stocks"}));
        GenericSubcriber<Headlines> myFeed = new GenericSubcriber<Headlines>("MyFeed", Arrays.asList(new String[]{"Economy", "market"}));

        broker.registerSubscriber(myApp, myApp.getTopics());
        broker.registerSubscriber(myWebsite, myWebsite.getTopics());
        broker.registerSubscriber(myFeed, myFeed.getTopics());

        GenericBroker<Article> bigBroker = new GenericBroker<Article>(2);
        GenericPublisher<Article> articles = new GenericPublisher<Article>("Money", Arrays.asList(new String[]{"finance", "stocks", "market"}));
        GenericSubcriber<Article> feed = new GenericSubcriber<Article>("Money", Arrays.asList(new String[]{"finance", "stocks", "market"}));


        Thread t = new Thread(new Runnable() {
            private long TTL = 100000;

            @Override
            public void run() {
                while (true) {
                    broker.publish(new Headlines().withMessage(news.getId(), UUID.randomUUID().toString(),
                            "Politics").withTimeAndTTL(new Date(), TTL));
                    broker.publish(new Headlines().withMessage(music.getId(), UUID.randomUUID().toString(),
                            "Rock").withTimeAndTTL(new Date(), TTL));
                    broker.publish(new Headlines().withMessage(money.getId(), UUID.randomUUID().toString(),
                            "market").withTimeAndTTL(new Date(), TTL));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }

            }
        });
        t.start();
        Thread.sleep(2000);
        broker.stop();
        Thread.sleep(10000);
        t.interrupt();
        Thread.sleep(2000);
        System.out.println("Stopped broker");
    }
}
