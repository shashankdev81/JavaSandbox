package concurrency.problems.stack_overflow;

import java.util.HashSet;
import java.util.Set;

public class SocialInfo {

    Set<Member> upVoters;
    Set<Member> deleteVoters;
    Set<Member> closeVoters;
    Set<Member> flaggers;

    public SocialInfo() {
        upVoters = new HashSet<>();
        deleteVoters = new HashSet<>();
        closeVoters = new HashSet<>();
        flaggers = new HashSet<>();

    }


}