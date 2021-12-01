package generic_pubsub;

import pubsub.Registerable;

import java.util.List;
import java.util.UUID;

public class GenericSubcriber<T> implements Registerable {

    private String id = "sub" + UUID.randomUUID().toString().substring(0, 9);

    private String name;

    private List<String> topics;


    public GenericSubcriber(String name, List<String> topics) {
        this.name = name;
        this.topics = topics;
    }

    @Override
    public String getId() {
        return id;
    }

    public boolean receive(T message) {
        System.out.println("Subcriber received message: subscriber=" + id + ", message=" + message);
        return true;
    }

    public List<String> getTopics() {
        return topics;
    }
}
