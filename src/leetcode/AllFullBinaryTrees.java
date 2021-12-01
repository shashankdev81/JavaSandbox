package leetcode;

import java.util.ArrayList;
import java.util.List;

public class AllFullBinaryTrees {

    public List<TreeNode> allPossibleFBT(int n) {
        return recurse(n);
    }

    private List<TreeNode> recurse(int n) {
        int nonRootNodes = n - 1;
        List<TreeNode> trees = new ArrayList<TreeNode>();
        if (nonRootNodes == 0) {
            trees.add(new TreeNode());
            return trees;
        }
        for (int i = 1; i <= nonRootNodes - 1; i += 2) {
            int partition1 = i;
            int partition2 = nonRootNodes - partition1;
            trees.addAll(cartesian(recurse(partition1), recurse(partition2)));
        }

        return trees;

    }

    private List<TreeNode> cartesian(List<TreeNode> leftSubtrees, List<TreeNode> rightSubtrees) {
        List<TreeNode> trees = new ArrayList<TreeNode>();
        for (TreeNode left : leftSubtrees) {
            for (TreeNode right : rightSubtrees) {
                trees.add(new TreeNode(0, left, right));
            }
        }
        return trees;
    }


    private int nodesInBtreeOfHeightX(int h) {
        return (int) Math.pow(2, h + 1) - 1;
    }


    public static void main(String[] args) {
        AllFullBinaryTrees binaryTrees = new AllFullBinaryTrees();
        List<TreeNode> allPossibleFBT = binaryTrees.allPossibleFBT(11);
        System.out.println(allPossibleFBT.size());
    }
}
