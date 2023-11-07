package object_orinted_design.splitwise;

public interface ISettlement {

    void addContributor(String groupId, String userId);

    void removeContributor(String groupId, String userId);

    void createGroup(String id, String owner, String name);

    void deleteGroup(String id);

    void settle(String groupId, String userId1, String userId2, int amount);

    void settle(String groupId, String userId, int amount);

    void payBill(String groupId, int amount);
}
