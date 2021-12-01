package generic_pubsub;

import pubsub.Registerable;

import java.util.List;
import java.util.UUID;

public class GenericPublisher<T extends IMessage> implements Registerable {

    private String id = "pub" + UUID.randomUUID().toString().substring(0, 9);

    private String name;

    private List<String> topics;

    private GenericBroker<IMessage> broker;

    public GenericPublisher(String name, List<String> topics) {
        this.name = name;
        this.topics = topics;
    }


    @Override
    public String getId() {
        return id;
    }

    public List<String> getTopics() {
        return topics;
    }

    public boolean create(T message) {
        broker.publish(message);
        return true;
    }
}
