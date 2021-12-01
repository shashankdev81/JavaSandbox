package tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class NodeBasedBST {

    private Node root = createTestTree();
    private Node target;

    private class Node {

        private int value;

        private Node left;

        private Node right;


        public Node(int v) {
            this.value = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    private Node createTestTree() {

        Node head = new Node(4);

        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node five = new Node(5);
        Node six = new Node(6);
        Node seven = new Node(7);
        Node eight = new Node(8);

        head.left = two;
        two.left = one;
        two.right = three;

        head.right = six;
        six.left = five;
        six.right = seven;
        seven.right = eight;

        return head;

    }


    public static void main(String[] args) {
        NodeBasedBST bst = new NodeBasedBST();
        bst.createTestTree();
        Node lca = bst.findLCA2(bst.root, 2, 11);
        Node lca2 = bst.findLCA(bst.root, 2, 11);
        System.out.println("LCA=" + (lca == null ? "Not Found" : lca.value));
        System.out.println("LCA=" + (lca2 == null ? "Not Found" : lca2.value));

    }


    public Node findLCA(Node head, int x, int y) {
        if (find(head.left, x) != null && find(head.left, y) != null) {
            return findLCA(root.left, x, y);
        } else if (find(head.right, x) != null && find(head.right, y) != null) {
            return findLCA(root.right, x, y);
        } else if (find(head, x) == null || find(head, y) == null) {
            return null;
        } else {
            return head;
        }
    }

    public Node findLCA2(Node head, int x, int y) {
        List<Node> pathX = findWithPath(root, x, new LinkedList<>());
        List<Node> pathY = findWithPath(root, y, new LinkedList<>());
        if (pathX.isEmpty() || pathY.isEmpty()) {
            return null;
        }
        Node[] pathXArr = pathX.toArray(new Node[0]);
        Node[] pathYArr = pathY.toArray(new Node[0]);
        int k = 0;
        while (k < pathXArr.length && k < pathYArr.length && pathXArr[k] == pathYArr[k]) {
            k++;
            continue;

        }
        return pathXArr[k];

    }

    private Node find(Node head, int val) {
        if (head == null) {
            return null;
        } else if (head.value == val) {
            return head;
        } else {
            if (head.value < val) {
                return find(head.right, val);
            } else {
                return find(head.left, val);
            }
        }
    }

    private List<Node> findWithPath(Node head, int val, List<Node> path) {
        if (head == null) {
            return new LinkedList<>();
        }
        path.add(head);
        if (head.value == val) {
            return path;
        } else {
            if (head.value < val) {
                return findWithPath(head.right, val, path);
            } else {
                return findWithPath(head.left, val, path);
            }
        }
    }
}
