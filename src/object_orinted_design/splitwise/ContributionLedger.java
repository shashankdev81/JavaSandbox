package object_orinted_design.splitwise;

import object_orinted_design.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContributionLedger implements IPaymentLedger {

    private int totalAmount;

    private Map<String, Map<String, Integer>> ledger;

    private Map<String, Integer> equalContributers;

    private Map<String, Integer> majorContributers;

    private Map<String, Integer> partialContributers;

    public ContributionLedger(int billAmount) {
        this.totalAmount = billAmount;
        equalContributers = new HashMap<>();
        majorContributers = new HashMap<>();
        partialContributers = new HashMap<>();
        ledger = new HashMap<>();

    }

    @Override
    public void pay(String userId1, String userId2, int amount) {
        ledger.putIfAbsent(userId1, new HashMap<>());
        ledger.get(userId1).put(userId2, ledger.get(userId1).get(userId2) + amount);

    }

    @Override
    public void receive(String userId, int amount) {
        if (amount > totalAmount / contributers()) {
            majorContributers.putIfAbsent(userId, amount);
            majorContributers.put(userId, majorContributers.get(userId) + amount);
        }

    }

    private int contributers() {
        return equalContributers.size() + majorContributers.size() + partialContributers.size();
    }

    @Override
    public void reconcile() throws Exception {
        int sum = contribution(majorContributers) + contribution(equalContributers) + contribution(partialContributers);
        if (sum != totalAmount) {
            throw new Exception("Cannot reconcile");
        }
        for (Map.Entry<String, Integer> lender : majorContributers.entrySet()) {
            int part = lender.getValue() / partialContributers.size();
            for (Map.Entry<String, Integer> lendee : partialContributers.entrySet()) {
                ledger.putIfAbsent(lendee.getKey(), new HashMap<>());
                ledger.get(lendee.getKey()).put(lender.getKey(), part);
            }
        }

    }

    @Override
    public List<Payment> getPaymentHistory(String userId) {
        if (ledger.get(userId) == null) {
            return new ArrayList<>();
        }
        return ledger.get(userId).entrySet().stream().map(e ->
                new Payment(UserRepository.get(e.getKey()).getName(), e.getValue())).collect(Collectors.toList());
    }

    private int contribution(Map<String, Integer> contributers) {
        return contributers.values().stream().mapToInt(e -> e.intValue()).sum();
    }

    protected class Payment {


        public Payment(String name, int amount) {
        }

        @Override
        public String toString() {
            return "Payment{}";
        }
    }

}
