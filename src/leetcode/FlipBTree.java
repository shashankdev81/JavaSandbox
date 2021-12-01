package leetcode;

import concurrency.nonblockingds.Stack;

import java.util.LinkedList;

public class FlipBTree {

    private Node btree1 = init();

    private Node btree2 = init2();

    private class Node {
        public Node(Node left, Node right, int val) {
            this.left = left;
            this.right = right;
            this.val = val;
        }

        private Node left;

        private Node right;

        private int val;

    }

    private boolean isFlipEquiv(Node left, Node right) {
        //no point in proceeding further is nodes are not equal
        if (!isEqualNodes(left, right)) {
            return false;
        }
        //subtree is equal and ends
        if ((isLeaf(left) && isLeaf(right)) || (left == null && right == null)) {
            return true;
        }
        if (isUnEqualChildren(left, right)) {
            Node temp = left.left;
            left.left = left.right;
            left.right = temp;
        }
        return isFlipEquiv(left.left, right.left) && isFlipEquiv(left.right, right.right);

    }

    private boolean isUnEqualChildren(Node left, Node right) {
        return !isEqualNodes(left.left, right.left) || !isEqualNodes(left.right, right.right);
    }

    private boolean isEqualNodes(Node left, Node right) {
        return (left == null && right == null) || (left != null && right != null && left.val == right.val);
    }

    private boolean isLeaf(Node node) {
        return node != null && (node.left == null && node.right == null);
    }

    private Node init() {
        Node seven = new Node(null, null, 7);
        Node eight = new Node(null, null, 8);
        Node five = new Node(seven, eight, 5);
        Node four = new Node(null, null, 4);
        Node two = new Node(four, five, 2);
        Node six = new Node(null, null, 6);
        Node three = new Node(six, null, 3);
        Node one = new Node(two, three, 1);
        return one;


    }

    private Node init2() {
        Node seven = new Node(null, null, 7);
        Node eight = new Node(null, null, 8);
        Node five = new Node(eight, seven, 5);
        Node four = new Node(null, null, 4);
        Node two = new Node(four, five, 2);
        Node six = new Node(null, null, 6);
        Node three = new Node(null, six, 3);
        Node one = new Node(three, two, 1);
        return one;


    }

    public static void main(String[] args) {
        FlipBTree flip = new FlipBTree();
        System.out.println(flip.isFlipEquiv(flip.btree1, flip.btree2));
    }

}
