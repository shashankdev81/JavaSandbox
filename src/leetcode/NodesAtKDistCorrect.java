package leetcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/submissions/
public class NodesAtKDistCorrect {

    private Set<TreeNode> nodes;

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        nodes = new HashSet<TreeNode>();
        findTarget(root, target, k);
        return nodes.stream().map(n -> n.val).collect(Collectors.toList());

    }


    private int findTarget(TreeNode root, TreeNode target, int k) {
        if (root == null) {
            return -1;
        }
        if (root.val == target.val) {
            if (k == 0) {
                nodes.add(root);
            }
            findNodesAtDistance(root.left, k - 1);
            findNodesAtDistance(root.right, k - 1);
            return 1;
        }
        int d1 = findTarget(root.left, target, k);
        if (d1 != -1) {
            if (k - d1 == 0) {
                nodes.add(root);
            }
            findNodesAtDistance(root.right, k - d1 - 1);
            return 1 + d1;
        }
        int d2 = findTarget(root.right, target, k);
        if (d2 != -1) {
            if (k - d2 == 0) {
                nodes.add(root);
            }
            findNodesAtDistance(root.left, k - d2 - 1);
            return 1 + d2;
        }
        return -1;
    }

    private void findNodesAtDistance(TreeNode root, int d) {
        if (root == null || d < 0) {
            return;
        }
        if (d == 0 && root != null) {
            nodes.add(root);
        }
        findNodesAtDistance(root.left, d - 1);
        findNodesAtDistance(root.right, d - 1);
    }

    public static void main(String[] args) {
        TreeNode two = new TreeNode(2, new TreeNode(7, null, null), new TreeNode(4, null, null));
        TreeNode five = new TreeNode(5, new TreeNode(6, null, null), two);
        TreeNode one = new TreeNode(1, new TreeNode(0, null, null), new TreeNode(8, null, null));
        TreeNode root = new TreeNode(3, five, one);
        NodesAtKDistCorrect nodesAtKDist = new NodesAtKDistCorrect();
        System.out.println(nodesAtKDist.distanceK(root, five, 2));

        TreeNode two2 = new TreeNode(2, null, null);
        one = new TreeNode(1, new TreeNode(3, null, null), two2);
        TreeNode root2 = new TreeNode(0, one, null);
        System.out.println(nodesAtKDist.distanceK(root2, two2, 1));

        TreeNode three = new TreeNode(3, null, new TreeNode(4, null, null));
        TreeNode two3 = new TreeNode(2, null, three);
        TreeNode root3 = new TreeNode(0, null, new TreeNode(1, null, two3));
        System.out.println(nodesAtKDist.distanceK(root3, two3, 2));

        TreeNode root4 = new TreeNode(0, new TreeNode(1, null, two3), null);
        System.out.println(nodesAtKDist.distanceK(root4, three, 0));

    }

}
