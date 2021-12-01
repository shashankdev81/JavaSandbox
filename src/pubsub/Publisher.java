package pubsub;

import java.util.List;
import java.util.UUID;

public class Publisher implements Registerable {

    private String id = "pub" + UUID.randomUUID().toString().substring(0, 9);

    private String name;

    private List<String> topics;

    public Publisher(String name, List<String> topics) {
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
}
