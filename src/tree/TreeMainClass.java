package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class TreeMainClass {

    private int removable;

    public static void main(String[] args) {

        //testForest();

        LinkedNodesBasedBST bst = new LinkedNodesBasedBST();

        for (int i = 1; i <= 10; i++) {
            bst.insert(i);
        }

        System.out.println("Size of tree=" + bst.getSize());
        System.out.println("Height of tree=" + bst.getHeight());
        System.out.println("Tree is balanced=" + bst.isBalanced());
        bst.balance();
        System.out.println("Tree is balanced=" + bst.isBalanced());
    }

    private static void testForest() {
        NAryNode three = new NAryNode(3);
        NAryNode six = new NAryNode(6);
        NAryNode seven = new NAryNode(7);
        seven.addEdge(39);
        NAryNode four = new NAryNode(4);

        NAryNode one = new NAryNode(1);

        NAryNode two = new NAryNode(2);
        two.addEdge(three);

        NAryNode five = new NAryNode(5);
        five.addEdge(six);
        five.addEdge(seven);
        four.addEdge(five);
        four.addEdge(21);

        NAryNode zero = new NAryNode(0);
        zero.addEdge(one);
        zero.addEdge(two);
        zero.addEdge(four);

        int maxEdgeRemovable = createForest(zero);
        System.out.println(maxEdgeRemovable);
    }

    private static int createForest(NAryNode root) {

        int removable = 0;
        List<NAryNode> removed = new ArrayList<NAryNode>();
        for (NAryNode edge : root.edges) {
            if ((root.size() - edge.size()) % 2 == 0 && edge.size() % 2 == 0) {
                removed.add(edge);
            }
        }
        root.edges.removeAll(removed);
        removable = removed.size();
        for (NAryNode NAryNode : removed) {
            removable += createForest(NAryNode);
        }

        return removable;
    }

    private static class NAryNode {

        int value;
        List<NAryNode> edges = new ArrayList<NAryNode>();

        public NAryNode(int value) {
            this.value = value;
        }

        public void addEdge(int value) {
            NAryNode NAryNode = new NAryNode(value);
            edges.add(NAryNode);
        }

        public void addEdge(NAryNode NAryNode) {
            edges.add(NAryNode);
        }

        public NAryNode removeEdge(int value) {
            int ind = edges.indexOf(new NAryNode(value));
            NAryNode removed = edges.get(ind);
            edges.remove(removed);
            return removed;
        }

        public int size() {
            int size = 1;
            if (!edges.isEmpty()) {
                for (NAryNode n : edges) {
                    size += n.size();
                }
            }
            return size;
        }
    }


}


