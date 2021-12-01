package leetcode;

import java.util.*;

public class AllFullBinaryTreesCorrect {

    Map<Integer, List<TreeNode>> memo = new HashMap();

    public List<TreeNode> allPossibleFBT(int N) {
        if (!memo.containsKey(N)) {
            List<TreeNode> ans = new LinkedList();
            if (N == 1) {
                ans.add(new TreeNode(0));
            } else if (N % 2 == 1) {
                for (int x = 0; x < N; ++x) {
                    int y = N - 1 - x;
                    for (TreeNode left : allPossibleFBT(x))
                        for (TreeNode right : allPossibleFBT(y)) {
                            TreeNode bns = new TreeNode(0);
                            bns.left = left;
                            bns.right = right;
                            ans.add(bns);
                        }
                }
            }
            memo.put(N, ans);
        }

        return memo.get(N);
    }

    public static void main(String[] args) {
        AllFullBinaryTreesCorrect binaryTrees = new AllFullBinaryTreesCorrect();
        List<TreeNode> allPossibleFBT = binaryTrees.allPossibleFBT(11);
        System.out.println(allPossibleFBT.size());
    }
}
