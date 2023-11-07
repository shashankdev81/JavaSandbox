package object_orinted_design.locker;

public interface IAuthorizationService {

    public Challenge sendChallenge(String userId);

    public boolean isAuthorised(Challenge chal);

    class Challenge {

        public String getUserId() {
            return userId;
        }

        private String userId;

        private String value;

        public Challenge(String userId, String value) {
            this.userId = userId;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
