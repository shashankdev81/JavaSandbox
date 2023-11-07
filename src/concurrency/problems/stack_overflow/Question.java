package concurrency.problems.stack_overflow;

import java.util.*;

public class Question implements Actionable, Commentable {

    private SocialInfo socialInfo;

    private Member author;

    private boolean isDeleted;

    private boolean isClosed;

    private String content;

    private String id;

    private Set<Comment> comments;

    private long timestamp;

    private Set<Answer> answers;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Question(Member author, String content, String id) {
        this.author = author;
        this.content = content;
        this.id = id;
        socialInfo = new SocialInfo();
        comments = new TreeSet<>();
        this.timestamp = System.currentTimeMillis();
    }

    public Question(String content, String id) {
        this.content = content;
        this.id = id;
    }

    public Member getAuthor() {
        return author;
    }

    public Set<Member> getUpVoters() {
        return socialInfo.upVoters;
    }

    public Set<Member> getDeleteVoters() {
        return socialInfo.deleteVoters;
    }

    public Set<Member> getCloseVoters() {
        return socialInfo.closeVoters;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    @Override
    public int getUpVotes() {
        return socialInfo.upVoters.size();
    }

    @Override
    public int getFlags() {
        return socialInfo.flaggers.size();
    }

    public int getDeleteVotes() {
        return socialInfo.deleteVoters.size();
    }

    public void upVote(Member voter) {
        socialInfo.upVoters.add(voter);
    }

    @Override
    public void flag(Member voter) {
        socialInfo.flaggers.add(voter);
    }

    public void voteForDelete(Member member) {
        socialInfo.deleteVoters.add(member);
    }

    public void voteForClose(Member member) {
        socialInfo.closeVoters.add(member);
    }

    public int getCloseVotes() {

        return socialInfo.closeVoters.size();
    }

    @Override
    public void comment(Comment comment) {
        comments.add(comment);
    }

    public void answer(String answer, Member member) {
        answers.add(new Answer(member, UUID.randomUUID().toString(), answer));
    }

    public Answer getAnswer(String id) {
        Optional<Answer> answer = answers.stream().filter(a -> a.getId().equals(id)).findFirst();
        return answer.isPresent() ? answer.get() : null;

    }
}
