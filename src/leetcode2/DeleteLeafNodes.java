package leetcode2;

public class DeleteLeafNodes {


    public static void main(String[] args) {
        DeleteLeafNodes deleteLeafNodes = new DeleteLeafNodes();
        BTreeGame.TreeNode two1 = new BTreeGame.TreeNode(2);
        BTreeGame.TreeNode two2 = new BTreeGame.TreeNode(2);
        BTreeGame.TreeNode four = new BTreeGame.TreeNode(4);
        BTreeGame.TreeNode two = new BTreeGame.TreeNode(2, two1, null);
        BTreeGame.TreeNode three = new BTreeGame.TreeNode(3, two2, four);
        BTreeGame.TreeNode one = new BTreeGame.TreeNode(1, two, three);
        one = deleteLeafNodes.removeLeafNodes(one, 2);
        System.out.println(one);

    }

    public BTreeGame.TreeNode removeLeafNodes(BTreeGame.TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        root = delete(root, target);
        return root;
    }

    public BTreeGame.TreeNode delete(BTreeGame.TreeNode root, int target) {
        if (root == null || isLeafNodeTarget(root, target)) {
            return null;
        }
        if (isLeafNode(root, target)) {
            return root;
        }
        do {
            root.left = delete(root.left, target);
            root.right = delete(root.right, target);
            System.out.println("Iterations");
        } while (isTarget(root.left, target) || isTarget(root.right, target));
        return root;
    }

    private boolean isTarget(BTreeGame.TreeNode root, int target) {
        return root != null && root.val == target;
    }

    private boolean isLeafNodeTarget(BTreeGame.TreeNode root, int target) {
        return isLeafNode(root, target) && root.val == target;
    }

    private boolean isLeafNode(BTreeGame.TreeNode root, int target) {
        return root != null && root.left == null && root.right == null;
    }

}
