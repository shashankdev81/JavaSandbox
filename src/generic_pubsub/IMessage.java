package generic_pubsub;

import java.util.Date;

public interface IMessage {

    public String getPublisherId();

    public String getPayload();

    public Date getCreationTime();

    public String getTopic();

    public long getTtl();
}
