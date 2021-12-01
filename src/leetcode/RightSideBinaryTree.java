package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RightSideBinaryTree {

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> rightSideNos = new LinkedList<Integer>();
        if (root == null) {
            return rightSideNos;
        }
        Queue<TreeNode> nodeInLine = new LinkedList<TreeNode>();
        nodeInLine.add(root);
        int count = 1;
        while (!nodeInLine.isEmpty() && count > 0) {
            TreeNode parent = null;
            while (count > 0) {
                parent = nodeInLine.poll();
                if (parent.left != null)
                    nodeInLine.add(parent.left);
                if (parent.right != null)
                    nodeInLine.add(parent.right);
                count--;
            }
            rightSideNos.add(parent.val);
            count = nodeInLine.size();
        }
        return rightSideNos;

    }

}

