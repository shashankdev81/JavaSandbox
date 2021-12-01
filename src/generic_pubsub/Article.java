package generic_pubsub;

import java.util.Date;
import java.util.Objects;

public class Article implements IMessage{

    private String publisherId;

    private String message;

    private Date creationTime;

    public String getPublisherId() {
        return publisherId;
    }

    public String getPayload() {
        return message;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getTopic() {
        return topic;
    }

    public long getTtl() {
        return ttl;
    }

    public Article() {
    }

    public Article withMessage(String publisherId, String message, String topic) {
        this.publisherId = publisherId;
        this.message = message;
        this.topic = topic;
        return this;
    }

    public Article withTimeAndTTL(Date creationTime, long ttl) {
        this.creationTime = creationTime;
        this.ttl = ttl;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article message1 = (Article) o;
        return ttl == message1.ttl && Objects.equals(publisherId, message1.publisherId) && Objects.equals(message, message1.message) && Objects.equals(creationTime, message1.creationTime) && Objects.equals(topic, message1.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publisherId, message, creationTime, topic, ttl);
    }

    private String topic;

    private long ttl;

    @Override
    public String toString() {
        return "Message{" +
                "publisherId='" + publisherId + '\'' +
                ", message='" + message + '\'' +
                ", creationTime=" + creationTime +
                ", topic='" + topic + '\'' +
                ", ttl=" + ttl +
                '}';
    }
}
