package pubsub;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class Broker {


    private Map<String, List<Publisher>> publishers;

    private Map<String, List<Subcriber>> subcribers;

    private BlockingQueue<Message> buffer;

    private List<Dispatcher> dispatchers;

    private int parallelism;

    public Broker(int parallelism) {
        this.publishers = new ConcurrentHashMap<String, List<Publisher>>();
        this.subcribers = new ConcurrentHashMap<String, List<Subcriber>>();
        this.buffer = new LinkedBlockingDeque<Message>();
        this.parallelism = parallelism;
    }

    public boolean start() {
        dispatchers = new ArrayList<Dispatcher>(parallelism);
        for (int i = 0; i < parallelism; i++) {
            Dispatcher dispatcher = new Dispatcher();
            dispatchers.add(dispatcher);
            new Thread(new Dispatcher()).start();
        }
        return true;
    }

    public boolean stop() {
        for (Dispatcher dispatcher : dispatchers) {
            dispatcher.isStop = true;
        }
        return true;
    }

    public boolean publish(Message message) {
        System.out.println("Published message:" + message);
        try {
            buffer.put(message);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    public Broker registerPublisher(Publisher publisher, List<String> topics) {
        for (String topic : topics) {
            publishers.putIfAbsent(topic, new CopyOnWriteArrayList<Publisher>());
            publishers.get(topic).add(publisher);
        }
        return this;
    }

    public boolean registerSubscriber(Subcriber subcriber, List<String> topics) {
        for (String topic : topics) {
            subcribers.putIfAbsent(topic, new CopyOnWriteArrayList<Subcriber>());
            subcribers.get(topic).add(subcriber);
        }
        return true;

    }

    private class Dispatcher implements Runnable {

        private volatile boolean isStop = false;

        private String id = "dis" + UUID.randomUUID().toString().substring(0, 9);

        @Override
        public void run() {
            System.out.println("Begin dispatcher");
            while (!isStop) {
                Message message = null;
                try {
                    if (!subcribers.isEmpty()) {
                        message = buffer.take();
                        if (subcribers.get(message.getTopic().toLowerCase(Locale.ROOT)) != null) {
                            for (Subcriber subcriber : subcribers.get(message.getTopic().toLowerCase(Locale.ROOT))) {
                                subcriber.receive(message);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Message not dispatched:" + (message == null ? "" : "message=" + message.getMessage()) + ",error=" + e.getMessage());
                }
            }

            System.out.println("Stop dispatcher");

        }

    }

}
