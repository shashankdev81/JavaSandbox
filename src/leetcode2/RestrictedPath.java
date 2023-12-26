package leetcode2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestrictedPath {

    public static void main(String[] args) {
        RestrictedPath restrictedPath = new RestrictedPath();
        System.out.println(restrictedPath.countRestrictedPaths(5, new int[][]{{1, 2, 3}, {1, 3, 3}, {2, 3, 1}, {1, 4, 2}, {5, 2, 2}, {3, 5, 1}, {5, 4, 10}}));
    }

    public int countRestrictedPaths(int n, int[][] edges) {
        Map<Integer, Node> nodes = new HashMap<>();
        int restrictedPaths = 0;
        for (int[] edgePair : edges) {
            nodes.putIfAbsent(edgePair[0], new Node(edgePair[0]));
            nodes.putIfAbsent(edgePair[1], new Node(edgePair[1]));
            Node node1 = nodes.get(edgePair[0]);
            Node node2 = nodes.get(edgePair[1]);
            node1.addEdge(node2, edgePair[2]);
            node2.addEdge(node1, edgePair[2]);
        }
        for (Node node : nodes.values()) {
            node.isVisited = true;
            restrictedPaths += node.getRestrictedPaths(Integer.MAX_VALUE);
            node.isVisited = false;
        }
        return restrictedPaths;
    }

    public class Node implements Comparable<Node> {
        int val;
        Map<Node, Integer> edges;
        boolean isVisited = false;

        public Node(int v) {
            this.val = v;
            this.edges = new HashMap<>();
        }

        public void addEdge(Node edge, int weight) {
            edges.putIfAbsent(edge, weight);

        }

        public boolean equals(Object obj) {
            if (obj == null || !obj.getClass().equals(this.getClass())) {
                return false;
            }
            Node ext = (Node) obj;
            return this.val == ext.val;
        }

        public int hashCode() {
            return Objects.hash(this.val);
        }

        public int compareTo(Node n) {
            return this.val = n.val;
        }

        public int getRestrictedPaths(int inWeight) {
            System.out.println("Edge path=" + this.val);
            int restrictedPaths = 0;
            for (Map.Entry<Node, Integer> edgeEntry : edges.entrySet()) {
                Node edge = edgeEntry.getKey();
                int weight = edgeEntry.getValue();
                if (edge.isVisited || edge.equals(this)) {
                    System.out.println("Edge path already visited=" + edge.val);
                    continue;
                }
                edge.isVisited = true;
                if (weight <= inWeight && inWeight != Integer.MAX_VALUE) {
                    restrictedPaths++;
                }
                restrictedPaths += edge.getRestrictedPaths(weight);
                edge.isVisited = false;
            }
            return restrictedPaths;
        }

    }
}