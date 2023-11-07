package leetcode2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BTreeGame {
    Set<TreeNode> blueVisited = new HashSet<>();
    Set<TreeNode> redVisited = new HashSet<>();

    public static void main(String[] args) {
        BTreeGame bTreeGame = new BTreeGame();
        Map<String, Integer> videos = new TreeMap<>();
        Map<Integer,List<String>> videosReverseIndex = videos.entrySet().stream().collect(Collectors.groupingBy(e -> e.getValue(), Collectors.mapping(e -> e.getKey(), Collectors.toList())));
        Map<Integer,List<String>> videosSortedReverseIndex = new TreeMap<>(videosReverseIndex);
        videosSortedReverseIndex.entrySet().stream().flatMap(e -> e.getValue().stream()).sorted().collect(Collectors.toList());
        TreeNode root = getTreeNode();
        //System.out.println(bTreeGame.btreeGameWinningMove2(root, 3, 2));
        System.out.println(bTreeGame.btreeGameWinningMove2(root, 11, 3));
    }

    private static TreeNode getTreeNode() {
        TreeNode eight = new TreeNode(8);
        TreeNode nine = new TreeNode(9);
        TreeNode ten = new TreeNode(10);
        TreeNode eleven = new TreeNode(11);
        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        two.left = four;
        two.right = five;
        four.left = eight;
        four.right = nine;
        five.left = ten;
        five.right = eleven;
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);
        three.left = six;
        three.right = seven;
        TreeNode root = new TreeNode(1);
        root.left = two;
        root.right = three;
        return root;
    }

    private static TreeNode getTreeNode2() {
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode root = new TreeNode(1);
        root.left = two;
        root.right = three;
        return root;
    }

    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        boolean isPossible = false;
        for (int y = 1; y <= n; y++) {
            blueVisited = new HashSet<>();
            redVisited = new HashSet<>();
            if (y == x) {
                continue;
            }
            visit(root, x, redVisited);
            visit(root, y, blueVisited);
            boolean canProceed = true;
            while (canProceed) {
                canProceed = color(root, redVisited, blueVisited) || color(redVisited, blueVisited) || color(root, blueVisited, redVisited) || color(blueVisited, redVisited);
            }
            isPossible = blueVisited.size() > redVisited.size();
            if (isPossible) {
                return true;
            }
        }
        return isPossible;
    }

    private boolean visit(TreeNode root, int x, Set<TreeNode> visited) {
        if (root == null) return false;
        if (root.val == x) {
            visited.add(root);
            return true;
        }
        return visit(root.left, x, visited) || visit(root.right, x, visited);

    }

    private boolean color(TreeNode root, Set<TreeNode> visited, Set<TreeNode> other) {
        if (root == null) {
            return false;
        }
        if (!visited.contains(root) && !other.contains(root) && isChildColored(root, visited)) {
            visited.add(root);
            return true;
        }
        return color(root.left, visited, other) || color(root.right, visited, other);
    }

    private boolean color(Set<TreeNode> visited, Set<TreeNode> other) {
        for (TreeNode node : visited) {
            if (node.left != null && !visited.contains(node.left) && !other.contains(node.left)) {
                visited.add(node.left);
                return true;
            }
            if (node.right != null && !visited.contains(node.right) && !other.contains(node.right)) {
                visited.add(node.right);
                return true;
            }
        }
        return false;
    }

    private boolean isChildColored(TreeNode root, Set<TreeNode> visited) {
        return (root.left != null && visited.contains(root.left)) || (root.right != null && visited.contains(root.right));
    }

    private int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private TreeNode findX(TreeNode root, int x) {
        if (root == null || root.val == x) {
            return root;
        }
        TreeNode left = findX(root.left, x);
        if (left != null) {
            return left;
        }
        return findX(root.right, x);
    }

    public boolean btreeGameWinningMove2(TreeNode root, int n, int x) {
        TreeNode nodeX = findX(root, x);
        int leftCount = countNodes(nodeX.left);
        int rightCount = countNodes(nodeX.right);
        return (leftCount + rightCount < n / 2) || (leftCount > n - leftCount) || (rightCount > n - rightCount);
    }

    public static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TreeNode treeNode = (TreeNode) o;
            return val == treeNode.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

}

