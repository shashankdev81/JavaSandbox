package object_orinted_design.splitwise;

import object_orinted_design.User;
import object_orinted_design.UserRepository;

import java.util.*;

public class Group {

    private String id;

    private String name;

    private String owner;

    private Map<String, User> members;

    public ContributionLedger getLedger() {
        return ledger;
    }

    private ContributionLedger ledger;

    //private Set<PaymentLedger> ledgers = new HashSet<>();

    public Group(String id, String ownerId, String name) {
        this.id = id;
        this.owner = ownerId;
        this.name = name;
        this.members = new HashMap<>();
        this.members.put(ownerId, UserRepository.get(ownerId));
    }

    public void addMember(String userId) {
        User user = UserRepository.get(userId);
        Collection<User> friends = members.values();
        members.put(userId, user);

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void captureBill(int amount) {
        ledger = new ContributionLedger(amount);

    }
}
