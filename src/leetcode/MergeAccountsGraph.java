package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class MergeAccountsGraph {

    private class Node {

        private String name;

        private String email;

        private boolean isName = false;

        private Set<Node> children;

        private Set<Node> parents;

        public Node(String name, boolean isName) {
            this.name = name;
            this.isName = isName;
            children = new HashSet<Node>();
        }

        public Node(String email, boolean isName, Node parent) {
            this.email = email;
            this.isName = isName;
            parents = new HashSet<Node>();
            parents.add(parent);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return isName == node.isName && Objects.equals(name, node.name) && Objects.equals(email, node.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, email, isName);
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Set<Node> emailNodes = new HashSet<Node>();
        Set<Node> nameNodes = new HashSet<Node>();
        for (List<String> emails : accounts) {
            Node parent = new Node(emails.get(0), true);
            nameNodes.add(parent);
            emails.subList(1, emails.size()).stream().forEach(e -> {
                Node child = new Node(e, false, parent);
                parent.children.add(child);
                emailNodes.add(child);
            });
        }

        List<List<String>> result = new ArrayList<List<String>>();
        for (Node emailNode : emailNodes) {
            Set<Node> emailsOfSameOwner = new HashSet<Node>();
            traverse(emailNode, emailsOfSameOwner);
            List<String> emails = new ArrayList<String>();
            emails.addAll(emailNode.parents.stream().map(e -> e.name).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList()));
            emails.addAll(emailsOfSameOwner.stream().map(e -> e.email).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList()));
            result.add(emails);
        }
        return result;
    }


    private void traverse(Node child, Set<Node> emails) {
        emails.add(child);
        for (Node parent : child.parents) {
            parent.children.stream().filter(e -> !emails.contains(e)).forEach(e -> {
                traverse(e, emails);
            });
        }
    }

    public static void main(String[] args) {
        List<List<String>> emails = new ArrayList<List<String>>();
        emails.add(Arrays.asList(new String[]{"David", "David0@m.co", "David1@m.co"}));
        emails.add(Arrays.asList(new String[]{"David", "David3@m.co", "David4@m.co"}));
        emails.add(Arrays.asList(new String[]{"David", "David4@m.co", "David5@m.co"}));
        emails.add(Arrays.asList(new String[]{"David", "David2@m.co", "David3@m.co"}));
        emails.add(Arrays.asList(new String[]{"David", "David1@m.co", "David2@m.co"}));

        MergeAccountsGraph mergeAccounts2 = new MergeAccountsGraph();
        System.out.println(mergeAccounts2.accountsMerge(emails));
    }
}
