package object_orinted_design;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RouterServiceUsingTrie implements IRouter {


    private Set<Character> SPECIAL_TRAILING_CHARS = "?/&".chars().mapToObj(s -> (char) s).collect(
        Collectors.toSet());


    private TrieNode rootNode = new TrieNode("ROOT");

    public static void main(String[] args) {
        IRouter router = new RouterServiceUsingTrie();
        long then = System.currentTimeMillis();
        router.withRoute("/foo/qw/ert", "/bar/foo");
        router.withRoute("/bar/*/ert", "/foo/bar");
        router.withRoute("/bar/ext/*", "/qu/rty");
        System.out.println(router.route("/foo/qw/ert"));
        System.out.println(router.route("/bar/qw/ert"));
        System.out.println(router.route("/bar/ext/timepass"));
        long now1 = System.currentTimeMillis();
        router = new RouterServiceUsingRegexMatching();
        router.withRoute("/foo/qw/ert", "/bar/foo");
        router.withRoute("/bar/*/ert", "/foo/bar");
        router.withRoute("/bar/ext/*", "/qu/rty");
        System.out.println(router.route("/foo/qw/ert"));
        System.out.println(router.route("/bar/qw/ert"));
        System.out.println(router.route("/bar/ext/timepass"));
        long now2 = System.currentTimeMillis();
        System.out.println((now1 - then) + "," + (now2 - now1));

    }


    @Override
    public void withRoute(String path, String result) {
        path = normalise(path);
        String[] partArr = path.split("/");
        rootNode.addPart(0, partArr, result);

    }

    @Override
    public String route(String path) {
        path = normalise(path);
        String[] partArr = path.split("/");
        return rootNode.match(0, partArr);
    }

    private String normalise(String path) {
        if (path.charAt(0) == '/') {
            path = path.substring(1);
        }
        char endChar = path.charAt(path.length() - 1);
        if (SPECIAL_TRAILING_CHARS.contains(endChar)) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    public class TrieNode {

        private String part;

        private String result;

        private Map<String, TrieNode> nextNodes = new HashMap<>();

        public TrieNode(String part) {
            this.part = part;
        }

        public void addPart(int idx, String[] partArr, String result) {
            TrieNode node = null;
            if (nextNodes.containsKey(partArr[idx])) {
                node = nextNodes.get(partArr[idx]);
            } else {
                node = new TrieNode(partArr[idx]);
                nextNodes.put(partArr[idx], node);
            }
            if (idx == partArr.length - 1) {
                node.result = result;
                return;
            }
            node.addPart(idx + 1, partArr, result);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TrieNode trieNode = (TrieNode) o;
            return part.equals(trieNode.part);
        }

        @Override
        public int hashCode() {
            return Objects.hash(part);
        }

        public String match(int idx, String[] partArr) {
            String partOfInputUrl = partArr[idx];
            if ("ROOT".equals(part) || part.equals(partOfInputUrl)) {
                if (idx == partArr.length - 1) {
                    return result;
                }
                int nextIdx = "ROOT".equals(part) ? idx : idx + 1;
                return nextNodes.containsKey(partArr[nextIdx]) ? nextNodes.get(partArr[nextIdx])
                    .match(nextIdx, partArr) : nextNodes.get("*").match(nextIdx, partArr);
            } else if (part.equals("*")) {
                for (TrieNode nextNode : nextNodes.values()) {
                    String match = nextNode.match(idx + 1, partArr);
                    if (match != null) {
                        return match;
                    }
                }
                return result;
            } else {
                return null;
            }
        }
    }

}
