package leetcode2;

import leetcode.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UniqueBSTs {


    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode() {}
     * TreeNode(int val) { this.val = val; }
     * TreeNode(int val, TreeNode left, TreeNode right) {
     * this.val = val;
     * this.left = left;
     * this.right = right;
     * }
     * }
     */

    public static void main(String[] args) {
        UniqueBSTs uniqueBSTs = new UniqueBSTs();
        List<TreeNode> results = uniqueBSTs.generateTrees(3);
    }

    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> results = new ArrayList<>();
        List<Integer> nums = new LinkedList<Integer>();
        nums.add(-1);
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }
        for (int i = 1; i <= n; i++) {
            results.addAll(buildTree(i, nums.subList(1, i), nums.subList(i + 1, nums.size())));
        }

        return results;
    }

    private List<TreeNode> buildTree(int v, List<Integer> left, List<Integer> right) {
        System.out.println(v + "," + left + "," + right);
        List<TreeNode> results = new ArrayList<>();
        if (left.isEmpty() && right.isEmpty()) {
            TreeNode root = new TreeNode(v);
            results.add(root);
            return results;
        }
        List<TreeNode> leftSubTrees = new ArrayList<>();
        if (!left.isEmpty()) {
            leftSubTrees = buildTree(left.get(0), new LinkedList<Integer>(), left.subList(1, left.size()));
        }
        List<TreeNode> rightSubTrees = new ArrayList<>();
        if (!right.isEmpty()) {
            rightSubTrees = buildTree(right.get(0), new LinkedList<Integer>(), right.subList(1, right.size()));
        }
        if (leftSubTrees.isEmpty() && !rightSubTrees.isEmpty()) {
            for (TreeNode rightSubtree : rightSubTrees) {
                build(v, results, null, clone(rightSubtree));
            }

        } else if (!leftSubTrees.isEmpty() && rightSubTrees.isEmpty()) {
            for (TreeNode leftSubtree : leftSubTrees) {
                build(v, results, clone(leftSubtree), null);
            }

        } else if (!leftSubTrees.isEmpty() && !rightSubTrees.isEmpty()) {
            for (TreeNode leftSubtree : leftSubTrees) {
                for (TreeNode rightSubtree : rightSubTrees) {
                    build(v, results, clone(leftSubtree), clone(rightSubtree));
                }
            }
        }


        return results;

    }

    private void build(int v, List<TreeNode> results, TreeNode l, TreeNode r) {
        TreeNode root = new TreeNode(v);
        root.left = l;
        root.right = r;
        results.add(root);
    }

    private TreeNode clone(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode clonedRoot = new TreeNode(root.val);
        clonedRoot.left = clone(root.left);
        clonedRoot.right = clone(root.right);
        return clonedRoot;
    }
}