package object_orinted_design.locker;

import java.util.HashMap;
import java.util.Map;

public class AuhorisationService implements IAuthorizationService {

    private Map<String, Challenge> userToChallengeMap = new HashMap<>();

    @Override
    public Challenge sendChallenge(String userId) {
        Challenge chal = new Challenge(userId, "pass");
        userToChallengeMap.put(userId, chal);
        return chal;
    }

    @Override
    public boolean isAuthorised(Challenge chal) {
        return userToChallengeMap.get(chal.getUserId()).getValue().equals(chal.getValue());
    }
}
