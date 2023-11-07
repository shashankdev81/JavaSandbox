package concurrency.problems.stack_overflow;

public interface Actionable {

    public int getUpVotes();

    public int getFlags();

    public void upVote(Member voter);

    public void flag(Member voter);

}
