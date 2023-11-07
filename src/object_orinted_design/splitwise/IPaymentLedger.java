package object_orinted_design.splitwise;

import java.util.List;

public interface IPaymentLedger {
    public void pay(String userId1, String userId2, int amount);

    public void receive(String userId, int amount);

    public void reconcile() throws Exception;

    public List<ContributionLedger.Payment> getPaymentHistory(String userId);
}
