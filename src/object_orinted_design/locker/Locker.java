package object_orinted_design.locker;

public class Locker {

    private String id;

    private long assignedTime;

    private long releaseTime;

    public String getId() {
        return id;
    }

    public long getAssignedTime() {
        return assignedTime;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public Locker(String id) {
        this.id = id;
    }

    public Locker(String id, long assignedTime, long releaseTime) {
        this.id = id;
        this.assignedTime = assignedTime;
        this.releaseTime = releaseTime;
    }

    public void open(IAuthorizationService.Challenge challenge) {
        IAuthorizationService service = new AuhorisationService();
        service.isAuthorised(challenge);

    }

    public void close() {

    }

    public void receivePackage(String userId, String pack) {
        IAuthorizationService service = new AuhorisationService();
        service.sendChallenge(userId);
    }
}
