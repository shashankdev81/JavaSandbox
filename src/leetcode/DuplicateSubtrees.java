package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class DuplicateSubtrees {


    private Map<String, List<TreeNode>> subtreeToHashcode = new HashMap<String, List<TreeNode>>();


    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        print(root, 1);
        buildMerkleTree(root);
        System.out.println(subtreeToHashcode);
        List<TreeNode> duplicates = subtreeToHashcode.entrySet().stream().filter(e -> e.getValue().size() > 1)
                .map(e -> e.getValue().get(0)).collect(Collectors.toList());
        return duplicates;
    }

    private void print(TreeNode root, int level) {
        if (root == null) {
            return;
        }
        System.out.println(level + ":" + root.val);
        print(root.left, level + 1);
        print(root.right, level + 1);

    }

    private String buildMerkleTree(TreeNode root) {
        if (root == null) {
            return "Null";
        }
        String hash = buildMerkleTree(root.left) + buildMerkleTree(root.right) + String.valueOf(root.val).hashCode();
        subtreeToHashcode.putIfAbsent(hash, new ArrayList<TreeNode>());
        subtreeToHashcode.get(hash).add(root);
        return hash;
    }

    public static void main(String[] args) {
        List<Integer> nos = new ArrayList<Integer>();
        nos.add(0);
        nos.add(0);
        nos.add(0);
        System.out.println(nos.hashCode());
    }

}
