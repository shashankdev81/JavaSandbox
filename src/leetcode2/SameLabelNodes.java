package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SameLabelNodes {

    public static void main(String[] args) {
        SameLabelNodes sameLabelNodes = new SameLabelNodes();
        sameLabelNodes.countSubTrees(7, new int[][]{{0, 1}, {0, 2}, {1, 4}, {1, 5}, {2, 3}, {2, 6}},
            "abaedcd");
    }

    public int[] countSubTrees(int n, int[][] edges, String labels) {
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            nodeMap.put(i, new Node(i, labels.charAt(i)));
        }
        for (int i = 0; i < edges.length; i++) {
            nodeMap.get(edges[i][0]).children.add(nodeMap.get(edges[i][1]));
            nodeMap.get(edges[i][1]).children.add(nodeMap.get(edges[i][0]));
        }
        int[] sameLabelsCountArr = new int[n];
        int k = 0;
        for (Node root : nodeMap.values()) {
            sameLabelsCountArr[k++] = root.nodesWithSameLabel(root.label, new HashSet<>());
        }
        return sameLabelsCountArr;

    }

    class Node {

        int val;
        char label;
        List<Node> children = new ArrayList<>();

        Node(int v, char l) {
            val = v;
            label = l;
        }

        int nodesWithSameLabel(char l, Set<Integer> visited) {
            visited.add(val);
            int sameLabels = label == l ? 1 : 0;
            for (Node node : children) {
                if (!visited.contains(node.val)) {
                    sameLabels += node.nodesWithSameLabel(l, visited);
                }
            }
            return sameLabels;
        }
    }
}
