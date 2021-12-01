package tree;

import java.util.Objects;

/* structural modification methods are oustide the Node class
LinkedNodesBasedBST understand what is the underlying data structure and how to perform
structural modifications and how to traverse
*/
public class LinkedNodesBasedBST implements BST {

    private Node root = null;


    //shouldnt be able to access parent class
    private class Node {

        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node inLeft, Node inRight) {
            this.value = value;
            this.left = inLeft;
            this.right = inRight;
        }

        public int getSize() {
            if (left == null && right == null) {
                return 1;
            } else {
                return (left == null ? 0 : left.getSize()) + 1 + (right == null ? 0 : right.getSize());
            }
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int getHeight() {
            int height = 1;

            if (!isLeaf()) {
                int leftHeight = left == null ? 0 : left.getHeight();
                int rightHeight = right == null ? 0 : right.getHeight();
                height = height + (leftHeight >= rightHeight ? leftHeight : rightHeight);
            }
            return height;
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

    public void insert(int value) {
        root = add(root, value);
    }

    public void balance() {
        try {
            root = balanceSubtree(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Node balanceSubtree(Node root) throws Exception {
        if (root == null) {
            return root;
        }
        while (!isBalanced(root)) {
            int leftHeight = root.left == null ? 0 : root.left.getHeight();
            int rightHeight = root.right == null ? 0 : root.right.getHeight();
            int oldRootVal = root.value;

            if (leftHeight - rightHeight > 1) {
                Node leftMax = findMax(root.left);
                root = new Node(leftMax.value, delete(root.left, leftMax.value), root.right);

            } else if (rightHeight - leftHeight > 1) {
                Node rightMin = findMin(root.right);
                root = new Node(rightMin.value, root.left, delete(root.right, rightMin.value));
            }
            root = add(root, oldRootVal);
        }
        root.left = balanceSubtree(root.left);
        root.right = balanceSubtree(root.right);
        return root;
    }

    @Override
    public int getSize() {
        return root == null ? 0 : root.getSize();
    }

    @Override
    public int getHeight() {
        return root.getHeight();
    }

    @Override
    public int getMin() {
        return findMin(root).value;
    }

    @Override
    public int getMax() {
        return findMax(root).value;
    }

    @Override
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node head) {
        return Math.abs((head.left == null ? 0 : head.left.getHeight()) - (head.right == null ? 0 : head.right.getHeight())) < 2;
    }

    public void remove(int value) throws Exception {
        delete(root, value);
    }

    /*
     *
     * if new node return after creating the node
     * else if new_value > root value root = add(rightSubtree, new_value)
     * else root = add(leftSubtree, new_value)
     * */
    private Node add(Node head, int value) {

        if (head == null) {
            return new Node(value);
        }
        if (head.value <= value) {
            head.right = add(head.right, value);
        } else if (head.value > value) {
            head.left = add(head.left, value);
        }
        return head;
    }

    private Node delete(Node head, int value) throws Exception {

        // this should never happen if element exists
        if (head == null) {
            throw new Exception("Element not found");
        }

        if (head.value < value) {
            head.right = delete(head.right, value);
        } else if (head.value > value) {
            head.left = delete(head.left, value);
        } else {
            /*Scenarios
             * 1. This node has only right child
             * 2. This node has only left child
             * 3. This node has both children
             * */

            if (head.left == null) {
                return head.right;
            } else if (head.right == null) {
                return head.left;
            } else {
                /*
                 * 1. find min of right sub tree
                 * 2. swap current node with the min node
                 * 3. delete min node which is a left most leaf node
                 * */
                Node next = findMin(head.right);
                swap(head, next);
                head.right = delete(head.right, next.value);
            }
        }
        return head;
    }

    private void swap(Node head, Node next) {
        int temp = head.value;
        head.value = next.value;
        next.value = temp;
    }


    private Node findMin(Node head) {

        if (head.left != null) {
            return findMin(head.left);
        } else {
            return head;
        }
    }

    private Node findMax(Node head) {

        if (head.right != null) {
            return findMax(head.right);
        } else {
            return head;
        }
    }
}
