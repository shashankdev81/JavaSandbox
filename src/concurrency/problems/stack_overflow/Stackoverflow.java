package concurrency.problems.stack_overflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/*
 * Person
 * Guest
 * Member
 * Moderator
 * Question - (Deleted, Closed)
 * Activity
 * ^
 * |
 * Upvote
 * Answer
 * Comment
 * Flag
 *
 * Vote
 *
 *Badge
 *
 * * * * *
 * * */
public class Stackoverflow {

    private Map<String, Question> questions;

    public Stackoverflow() {
        questions = new HashMap<>();
    }

    private List<Question> search(String text) {
        return null;
    }

    private String view(String qid) {
        return questions.containsKey(qid) ? null : questions.get(qid).getContent();
    }

    public void postQuestion(String question, Member member) {
        String qid = UUID.randomUUID().toString();
        questions.put(qid, new Question(member, question, qid));
    }

    public void addAnswer(Question question, String answer, Member member) {
        String aid = UUID.randomUUID().toString();
        questions.get(question.getId()).answer(answer, member);
    }

    public void addComment(Question question, String comment, Member member) {
        String cid = UUID.randomUUID().toString();
        questions.get(question.getId()).comment(new Comment(member, comment, cid));
    }

    public void addComment(Question question, Answer answer, String comment, Member member) {
        String cid = UUID.randomUUID().toString();
        questions.get(question.getId()).getAnswer(answer.getId()).comment(new Comment(member, comment, cid));
    }

    public void upvote(Question question, Member member) {
        question.upVote(member);
    }

    public void upvote(Question question, Answer answer, Member member) {
        questions.get(question.getId()).getAnswer(answer.getId()).upVote(member);
    }

    public void upvote(Comment comment, Member member) {
    }
}
