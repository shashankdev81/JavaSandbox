package leetcode;

//https://leetcode.com/problems/recover-binary-search-tree/
public class RecoverBinarySearchTree {

    private TreeNode prev;

    private TreeNode next;

    public void recoverTree(TreeNode root) {
        TreeNode first = null;
        TreeNode second = null;
        TreeNode min = null;
        while (true) {
            if (next == null) {
                next = getMin(root);
                min = next;
            } else {
                if (next != min) {
                    prev = next;
                }
                Pair currAndNext = getAfter(root, next);
                next = currAndNext != null ? currAndNext.next : null;
                if (prev.val > next.val && first == null) {
                    first = prev;
                } else if (prev.val > next.val && first != null) {
                    second = next;
                }
            }
            if (next == null || first != null || second != null) {
                break;
            }
        }
        swap(first, second);

    }

    private void swap(TreeNode first, TreeNode second) {
        if (first == null || second == null) {
            return;
        }
        int val = first.val;
        first.val = second.val;
        second.val = val;
    }

    private class Pair {
        private TreeNode curr;
        private TreeNode next;

        public Pair(TreeNode curr, TreeNode next) {
            this.curr = curr;
            this.next = next;
        }
    }

    private Pair getAfter(TreeNode root, TreeNode curr) {
        if (root == null) {
            return new Pair(null, null);
        } else if (root.val == curr.val) {
            return root.right != null ? new Pair(root, getMin(root.right)) : new Pair(root, null);
        } else if (root.left != null) {
            Pair found = getAfter(root.left, curr);
            return root.right == null ? found : new Pair(root, getMin(root.right));
        } else if (curr.right != null) {
            Pair found = getAfter(root.right, curr);
            return found.next != null ? found : new Pair(found.curr, root);
        } else {
            return getAfter(root.right, curr);
        }
    }

    private TreeNode getMin(TreeNode root) {
        if (root.left != null) {
            return getMin(root.left);
        } else {
            return root;
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1, null, null);
        treeNode.left = new TreeNode(3, null, new TreeNode(2, null, null));
        RecoverBinarySearchTree recoverBinarySearchTree = new RecoverBinarySearchTree();
        recoverBinarySearchTree.recoverTree(treeNode);
    }
}
