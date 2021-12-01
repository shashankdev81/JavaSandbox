package tree;

import java.util.*;

public class EncodeNaryTree {


    private NAryNode tree;

    public EncodeNaryTree() {
        //test data
        initTestData();
    }

    private void initTestData() {
        NAryNode five = new NAryNode(5);
        NAryNode six = new NAryNode(6);
        NAryNode three = new NAryNode(3).addNode(five).addNode(six);
        NAryNode two = new NAryNode(2);
        NAryNode four = new NAryNode(4);
        NAryNode root = new NAryNode(1).addNode(three).addNode(two).addNode(four);
        this.tree = root;
    }

    private class NAryNode {

        private List<NAryNode> children;

        private int value;

        private NAryNode(int v) {
            this.value = v;
            this.children = new LinkedList<NAryNode>();
        }

        private NAryNode addNode(NAryNode node) {
            children.add(node);
            return this;
        }

    }

    private class BNode {

        private NAryNode node;

        private BNode left;

        private BNode right;

        public BNode(NAryNode node) {
            this.node = node;
        }

        public BNode(NAryNode node, BNode left, BNode right) {
            this.node = node;
            this.left = left;
            this.right = right;
        }
    }


    private BNode transformNaryToBinaryTree() {
        List<NAryNode> nodes = getLevelTraversedNodes();
        BNode root = createSubtree(nodes);
        return root;
    }

    private NAryNode transformToNAryTree(BNode root) {
        return root.node;
    }

    /*
    private List<BNode> getLevelTraversedNodes2(BNode root) {
        List<BNode> nodes = new LinkedList<>();
        Queue<BNode> curr = new LinkedList<>();
        curr.add(root);

        while (!curr.isEmpty()) {
            nodes.addAll(curr);
            int count = curr.size();
            while (count > 0) {
                BNode node = curr.poll();
                if (node.left != null)
                    curr.add(root.left);
                if (node.right != null)
                    curr.add(root.right);
                count--;
            }
        }
        return nodes;

    }
    */


    private BNode createSubtree(List<NAryNode> nodes) {
        BNode newRoot = new BNode(nodes.get(0));
        nodes.remove(0);
        if (nodes.isEmpty()) {
            return newRoot;
        }
        int left = nodes.size() / 2;
        List<NAryNode> leftSubtree = new ArrayList<>();
        leftSubtree.addAll(nodes.subList(0, left == 0 ? 1 : left));
        newRoot.left = createSubtree(leftSubtree);
        if (left > 0) {
            List<NAryNode> rightSubtree = new ArrayList<>();
            rightSubtree.addAll(nodes.subList(left, nodes.size()));
            newRoot.right = createSubtree(rightSubtree);
        }
        return newRoot;
    }

    private List<NAryNode> getLevelTraversedNodes() {
        List<NAryNode> nodes = new LinkedList<>();
        Queue<NAryNode> curr = new LinkedList<>();
        NAryNode tree = this.tree;
        nodes.add(tree);
        curr.addAll(tree.children);

        while (!curr.isEmpty()) {
            nodes.addAll(curr);
            int count = curr.size();
            while (count > 0) {
                NAryNode node = curr.poll();
                if (node.children != null) {
                    curr.addAll(node.children);
                }
                count--;
            }
        }
        return nodes;

    }


    private void addOrphan(NAryNode root, Queue<NAryNode> orphans) {
        if (!orphans.isEmpty() && root.children.size() < 2) {
            root.addNode(orphans.poll());
        }
    }

    public static void main(String[] args) {
        EncodeNaryTree tree = new EncodeNaryTree();
        BNode btree = tree.transformNaryToBinaryTree();
        tree.tree = btree.node;
        System.out.println("Hi");
    }
}
