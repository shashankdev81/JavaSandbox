package object_orinted_design;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteingServiceusingTrie2 {

    static class TrieNode {

        private Map<Character, TrieNode> children;
        private boolean isEnd;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEnd = false;
        }

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }
    }

    static class Router {

        private TrieNode root;

        public Router() {
            this.root = new TrieNode();
        }

        public void addRoute(String path) {
            TrieNode node = root;

            for (char c : path.toCharArray()) {
                node.getChildren().computeIfAbsent(c, k -> new TrieNode());
                node = node.getChildren().get(c);
            }

            node.setEnd(true);
        }

        public boolean route(String path) {
            return matchPath(root, path, 0);
        }

        private boolean matchPath(TrieNode node, String path, int index) {
            List<Integer> playersList;
            if (node == null) {
                return false;
            }

            if (index == path.length()) {
                return node.isEnd();
            }

            char currentChar = path.charAt(index);

            if (currentChar == '*') {
                // Wildcard matching
                for (TrieNode child : node.getChildren().values()) {
                    if (matchPath(child, path, index + 1)) {
                        return true;
                    }
                }
            } else {
                // Exact matching
                return matchPath(node.getChildren().get(currentChar), path, index + 1);
            }

            return false;
        }
    }

    public static void main(String[] args) {
        Router router = new Router();

        // Adding routes
        router.addRoute("/bar/qw/ert");
        router.addRoute("/bar/*/ert");

        // Testing routes
        System.out.println(router.route("/bar/qw/ert"));   // Should match
        System.out.println(router.route("/bar/xyz/ert"));  // Should match due to wildcard
        System.out.println(router.route("/bar/abc/xyz"));  // Should not match
    }
}
