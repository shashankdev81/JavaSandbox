package concurrency.problems.stack_overflow;

import java.util.*;
import java.util.stream.Collectors;

public class Answer implements Actionable, Commentable {

    private SocialInfo socialInfo;

    private Member author;

    private String id;

    private String content;

    private long timestamp;

    public Answer(Member author, String id, String content) {
        this.author = author;
        this.id = id;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
        socialInfo = new SocialInfo();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();


    }

    @Override
    public int getUpVotes() {
        return socialInfo.upVoters.size();
    }

    @Override
    public int getFlags() {
        return socialInfo.flaggers.size();
    }

    @Override
    public void upVote(Member voter) {
        socialInfo.upVoters.add(voter);
    }

    @Override
    public void flag(Member voter) {
        socialInfo.upVoters.add(voter);
    }

    @Override
    public void comment(Comment comment) {

    }

    public Member getAuthor() {
        return author;
    }

    public Set<Member> getUpVoters() {
        return socialInfo.upVoters;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(content, answer.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
