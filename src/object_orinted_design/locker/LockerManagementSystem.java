package object_orinted_design.locker;

import object_orinted_design.User;
import object_orinted_design.UserRepository;

public class LockerManagementSystem {

    public static void main(String[] args) {
        UserRepository.persist(new User("Shashank", "Shashank"));
        UserRepository.persist(new User("Divya", "Divya"));
        UserRepository.persist(new User("Shweta", "Shweta"));
        UserRepository.persist(new User("Gaurav", "Gaurav"));

        LockerRepository.persist(new Locker("1"));
        LockerRepository.persist(new Locker("2"));
        LockerRepository.persist(new Locker("3"));

        ILockerSelector lockerManager = new SimpleLockerSelectorImpl();
        Locker locker = lockerManager.request(new ILockerSelector.Criteria());

    }

}
