package generic_pubsub;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class GenericBroker<T extends IMessage> {


    private Map<String, List<GenericPublisher<T>>> publishers;

    private Map<String, List<GenericSubcriber<T>>> subcribers;

    private BlockingQueue<T> buffer;

    private List<Dispatcher> dispatchers;

    private int parallelism;

    private volatile boolean isBrokerStop = false;

    public GenericBroker(int parallelism) {
        this.publishers = new ConcurrentHashMap<String, List<GenericPublisher<T>>>();
        this.subcribers = new ConcurrentHashMap<String, List<GenericSubcriber<T>>>();
        this.buffer = new LinkedBlockingDeque<T>();
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

        isBrokerStop = true;
        for (Dispatcher dispatcher : dispatchers) {
            dispatcher.setStop(true);
        }
        return true;
    }

    public boolean publish(T message) {
        System.out.println("Published message:" + message);
        try {
            buffer.put(message);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    public GenericBroker registerPublisher(GenericPublisher<T> publisher, List<String> topics) {
        for (String topic : topics) {
            publishers.putIfAbsent(topic, new CopyOnWriteArrayList<GenericPublisher<T>>());
            publishers.get(topic).add(publisher);
        }
        return this;
    }

    public boolean registerSubscriber(GenericSubcriber<T> subcriber, List<String> topics) {
        for (String topic : topics) {
            subcribers.putIfAbsent(topic, new CopyOnWriteArrayList<GenericSubcriber<T>>());
            subcribers.get(topic).add(subcriber);
        }
        return true;

    }

    private class Dispatcher implements Runnable {

        public void setStop(boolean stop) {
            isStop = stop;
            System.out.println("isStop=" + isStop);
        }

        public Boolean getStop() {
            return isStop;
        }

        private volatile boolean isStop = false;

        private String id = "dis" + UUID.randomUUID().toString().substring(0, 9);

        @Override
        public void run() {
            System.out.println("Begin dispatcher");

            while (!isBrokerStop) {
                System.out.println("inside isStop=" + isBrokerStop);
                T message = null;
                try {
                    if (!subcribers.isEmpty()) {
                        message = buffer.take();
                        if (subcribers.get(message.getTopic().toLowerCase(Locale.ROOT)) != null) {
                            for (GenericSubcriber<T> subcriber : subcribers.get(message.getTopic().toLowerCase(Locale.ROOT))) {
                                subcriber.receive(message);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Message not dispatched:" + (message == null ? "" : "message=" + message.getPayload()) + ",error=" + e.getMessage());
                }
            }

            System.out.println("Stop dispatcher");

        }

    }

}
