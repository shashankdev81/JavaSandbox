package object_orinted_design.splitwise;

import object_orinted_design.User;
import object_orinted_design.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class SplitwiseSettlement implements ISettlement {


    private Map<String, Group> groups;

    public SplitwiseSettlement() {
        this.groups = new HashMap<>();
    }

    public static void main(String[] args) {
        ISettlement splitwise = new SplitwiseSettlement();
        UserRepository.persist(new User("Shashank", "Shashank"));
        UserRepository.persist(new User("Divya", "Divya"));
        UserRepository.persist(new User("Shweta", "Shweta"));
        UserRepository.persist(new User("Gaurav", "Gaurav"));

        splitwise.createGroup("groupId1", "userId1", "Biere CLub");
        splitwise.addContributor("groupId1", "Shashank");
        splitwise.addContributor("groupId1", "Divya");
        splitwise.addContributor("groupId1", "Shweta");
        splitwise.addContributor("groupId1", "Gaurav");

    }

    @Override
    public void addContributor(String groupId, String userId) {
        groups.get(groupId).addMember(userId);
    }

    @Override
    public void removeContributor(String groupId, String userId) {

    }


    @Override
    public void createGroup(String id, String owner, String name) {
        GroupRepository.persist(new Group(id, owner, name));
    }

    @Override
    public void deleteGroup(String id) {
        groups.remove(id);
    }

    @Override
    public void settle(String groupId, String userId1, String userId2, int amount) {


    }

    @Override
    public void settle(String groupId, String userId, int amount) {
        groups.get(groupId).getLedger().receive(userId, amount);
    }

    @Override
    public void payBill(String groupId, int amount) {
        groups.get(groupId).captureBill(amount);
    }
}



