package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NodeBasedBSTImproved {

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
        NodeBasedBSTImproved bst = new NodeBasedBSTImproved();
        bst.createTestTree();
        Node lca = bst.findLCA(1, 11);
        System.out.println("LCA=" + (lca == null ? "Not Found" : lca.value));
    }

    public Node findLCA(int x, int y) {

        Node[] pathForX = new Node[getHeight(root)];
        Node[] pathForY = new Node[getHeight(root)];
        find(root, x, pathForX, 0);
        find(root, y, pathForY, 0);
        int xInd = 0;
        int yInd = 0;
        Node lca = root;

        while (xInd < pathForX.length && yInd < pathForY.length) {
            if (!pathForX[xInd].equals(pathForY[yInd])) {
                lca = pathForX[--xInd];
                break;
            }
            xInd++;
            yInd++;
        }
        return lca;

    }


    private void find(Node head, int val, Node[] path, int index) {
        if (head == null) {
            return;
        }
        path[index] = head;
        if (head.value == val) {
            return;
        } else {
            if (head.value < val) {
                find(head.right, val, path, index + 1);
            } else {
                find(head.left, val, path, index + 1);
            }
        }
    }


    private int getHeight(Node root) {
        if (root == null) {
            return 0;
        } else if (root.right == null && root.left == null) {
            return 1;
        } else {
            return (1 + maxOf(getHeight(root.left), getHeight(root.right)));
        }
    }

    private int maxOf(int height1, int height2) {
        if (height1 > height2) return height1;
        else return height2;
    }
}
