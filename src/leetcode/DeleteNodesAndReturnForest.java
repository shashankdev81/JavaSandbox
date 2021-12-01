package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeleteNodesAndReturnForest {


    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> forest = new CopyOnWriteArrayList<TreeNode>();
        List<TreeNode> result = null;
        forest.add(root);
        for (int d : to_delete) {
            for (TreeNode start : forest) {
                result = findAndDelete(start, d);
                if (start.val == d) {
                    forest.remove(start);
                }
                if (!result.isEmpty()) {
                    forest.addAll(result);
                    break;
                }
            }
        }
        return forest;
    }

    private List<TreeNode> findAndDelete(TreeNode root, int target) {
        List<TreeNode> forest = new ArrayList<TreeNode>();
        if (root == null) {
            return forest;
        }
        if (root.val == target) {
            {
                if (root.left != null) {
                    forest.add(root.left);
                }
                if (root.right != null) {
                    forest.add(root.right);
                }
            }
        } else {
            forest = findAndDelete(root.left, target);
            if (root.left != null && root.left.val == target) {
                root.left = null;
            }
            if (!forest.isEmpty()) {
                return forest;
            }
            forest = findAndDelete(root.right, target);
            if (root.right != null && root.right.val == target) {
                root.right = null;
            }
        }


        return forest;
    }

    public static void main(String[] args) {
        TreeNode two = new TreeNode(2, new TreeNode(4, null, null), new TreeNode(5, null, null));
        TreeNode three = new TreeNode(3, new TreeNode(6, null, null), new TreeNode(7, null, null));
        TreeNode root = new TreeNode(1, two, three);
        DeleteNodesAndReturnForest forest = new DeleteNodesAndReturnForest();
        forest.delNodes(root, new int[]{3, 5});

        TreeNode root2 = new TreeNode(1, new TreeNode(2, new TreeNode(4, null, null), new TreeNode(3, null, null)), null);
        forest.delNodes(root2, new int[]{2, 3});

    }
}
