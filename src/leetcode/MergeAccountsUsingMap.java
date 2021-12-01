package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class MergeAccountsUsingMap {

    private class AccountDetails {

        private Set<String> names;

        private Set<String> emails;

        public AccountDetails() {
            this.names = new HashSet<String>();
            this.emails = new HashSet<String>();
            this.emails.addAll(emails);
        }

        public void merge(AccountDetails account) {
            this.names.addAll(account.names);
            this.emails.addAll(account.emails);

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AccountDetails details = (AccountDetails) o;
            return names.equals(details.names) && emails.equals(details.emails);
        }

        @Override
        public int hashCode() {
            return Objects.hash(names, emails);
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, AccountDetails> accountDetailsMap = new HashMap<String, AccountDetails>();
        for (List<String> emailIds : accounts) {
            AccountDetails account = new AccountDetails();
            account.names.add(emailIds.get(0));
            account.emails.addAll(emailIds.subList(1, emailIds.size()));
            List<String> existing = account.emails.stream().filter(e -> accountDetailsMap.containsKey(e))
                    .collect(Collectors.toList());
            if (existing.size() > 0) {
                existing.stream().forEach(e -> account.merge(accountDetailsMap.get(e)));
            }
            account.emails.stream().forEach(e -> accountDetailsMap.put(e, account));

        }


        List<List<String>> result = new ArrayList<List<String>>();
        Set<AccountDetails> uniqueAccounts = new HashSet<AccountDetails>();
        uniqueAccounts.addAll(accountDetailsMap.values());
        for (AccountDetails details : uniqueAccounts) {
            List<String> accountInfo = details.names.stream().sorted().collect(Collectors.toList());
            accountInfo.addAll(details.emails.stream().sorted().collect(Collectors.toList()));
            result.add(accountInfo);
        }
        return result;
    }


    public static void main(String[] args) {
        List<List<String>> emails = new ArrayList<List<String>>();
        emails.add(Arrays.asList(new String[]{"David", "David0@m.co", "David1@m.co"}));
        emails.add(Arrays.asList(new String[]{"David", "David3@m.co", "David4@m.co"}));
        emails.add(Arrays.asList(new String[]{"David", "David4@m.co", "David5@m.co"}));
        emails.add(Arrays.asList(new String[]{"David", "David2@m.co", "David3@m.co"}));
        emails.add(Arrays.asList(new String[]{"David", "David1@m.co", "David2@m.co"}));

        MergeAccountsUsingMap mergeAccounts2 = new MergeAccountsUsingMap();
        System.out.println(mergeAccounts2.accountsMerge(emails));
    }
}
