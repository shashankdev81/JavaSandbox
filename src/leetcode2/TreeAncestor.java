package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TreeAncestor {


    private Map<Integer, Node> nodeMap = new HashMap<>();

    private int kthAns;

    public TreeAncestor(int n, int[] parent) {
        for (int i = 0; i < n; i++) {
            nodeMap.put(i, new Node(i));
        }
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == -1) {
                continue;
            }
            nodeMap.get(parent[i]).addChild(nodeMap.get(i));
            nodeMap.get(nodeMap.get(i)).addParent(nodeMap.get(parent[i]));
        }
    }

    public int getKthAncestor(int node, int k) {
        Node curr = nodeMap.get(node);
        int count = k;
        while (count > 0 && curr != null) {
            curr = curr.parent;
            count--;
        }
        return curr == null ? -1 : curr.val;
    }

    public int getKthAncestor1(int node, int k) {
        kthAns = -1;
        nodeMap.get(0).searchAncestor(node, k, 0);
        return kthAns;
    }


    class Node {

        int val;
        Node parent;
        List<Node> children;

        public Node(int v) {
            val = v;
            children = new ArrayList<>();
        }

        void addParent(Node n) {
            parent = n;
        }

        void addChild(Node n) {
            children.add(n);
        }

        int searchAncestor(int node, int k, int depth) {
            if (node == val) {
                return depth;
            }
            for (Node child : children) {
                int depthFound = child.searchAncestor(node, k, depth + 1);
                if (depthFound != -1) {
                    if (depthFound - depth == k) {
                        kthAns = val;
                        return -1;
                    } else {
                        return depthFound;
                    }
                }
            }
            return -1;
        }

    }
}

/**
 * Your TreeAncestor object will be instantiated and called as such: TreeAncestor obj = new
 * TreeAncestor(n, parent); int param_1 = obj.getKthAncestor(node,k);
 */