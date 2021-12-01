package pubsub;

import java.util.*;

public class MainClass {

    public static void main(String[] args) {
        Broker broker = new Broker(2);
        broker.start();

        Publisher news = new Publisher("News", Arrays.asList(new String[]{"Politics", "Sports", "Economy"}));
        broker.registerPublisher(news, news.getTopics());
        Publisher music = new Publisher("Music", Arrays.asList(new String[]{"Pop", "Rock", "Metal"}));
        broker.registerPublisher(music, music.getTopics());
        Publisher money = new Publisher("Money", Arrays.asList(new String[]{"finance", "stocks", "market"}));
        broker.registerPublisher(money, money.getTopics());

        Subcriber myApp = new Subcriber("MyApp", Arrays.asList(new String[]{"Politics", "Pop", "finance"}));
        broker.registerSubscriber(myApp, myApp.getTopics());
        Subcriber myWebsite = new Subcriber("MyWebsite", Arrays.asList(new String[]{"Sports", "Rock", "stocks"}));
        broker.registerSubscriber(myWebsite, myWebsite.getTopics());
        Subcriber myFeed = new Subcriber("MyFeed", Arrays.asList(new String[]{"Economy", "market"}));
        broker.registerSubscriber(myFeed, myFeed.getTopics());

        new Thread(new Runnable() {
            private long TTL = 100000;

            @Override
            public void run() {
                while (true) {
                    broker.publish(new Message().withMessage(news.getId(), UUID.randomUUID().toString(),
                            "Politics").withTimeAndTTL(new Date(), TTL));
                    broker.publish(new Message().withMessage(music.getId(), UUID.randomUUID().toString(),
                            "Rock").withTimeAndTTL(new Date(), TTL));
                    broker.publish(new Message().withMessage(money.getId(), UUID.randomUUID().toString(),
                            "market").withTimeAndTTL(new Date(), TTL));
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }
}
