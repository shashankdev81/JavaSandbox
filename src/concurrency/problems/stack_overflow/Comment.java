package concurrency.problems.stack_overflow;

import java.util.Objects;
import java.util.Set;

public class Comment implements Comparable<Comment> {

    private Member author;

    private String content;

    private String id;

    private long timestamp;

    public Comment(Member author, String content, String id) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public int compareTo(Comment o) {
        return Long.compare(this.timestamp, o.timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
