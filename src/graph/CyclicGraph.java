package graph;

import java.util.*;

public class CyclicGraph {

    private Node[][] B = new Node[10][10];

    private Map<Node, Node> allNodes = new HashMap<Node, Node>();

    private Map<Node, Boolean> visited = new HashMap<Node, Boolean>();

    public void createTestGraph() {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);
        Node five = new Node(5);
        B[0][0] = one;B[0][1] = two;
        B[1][0] = one;B[1][1] = three;
        B[2][0] = four;B[2][1] = two;
        B[3][0] = two;B[3][1] = three;
        B[4][0] = three;B[4][1] = four;
        B[5][0] = two;B[5][1] = five;
        B[6][0] = four;B[6][1] = five;

        for (int i = 0; i <= 6; i++) {
            Node n = B[i][0];
            allNodes.putIfAbsent(n, n);
            allNodes.get(n).addEdge(B[i][1]);
        }

    }

    private class Node {

        public int getValue() {
            return value;
        }

        private int value;

        Set<Node> connections = new HashSet<Node>();

        public Node(int value) {
            this.value = value;
        }

        private void addEdge(Node node) {
            connections.add(node);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public boolean isCyclic() {
        boolean isCyclic = false;
        for (Node node : allNodes.keySet()) {
            visited.putIfAbsent(node, Boolean.TRUE);
            isCyclic = isCyclic || isCyclic(node, visited);
            if (isCyclic) {
                return isCyclic;
            }
            visited.put(node, Boolean.FALSE);
        }
        return isCyclic;
    }

    private boolean isCyclic(Node node, Map<Node, Boolean> visited) {
        boolean isCyclic = false;
        for (Node conn : node.connections) {
            visited.putIfAbsent(conn, Boolean.FALSE);
            if (visited.get(conn)) {
                return true;
            } else {
                visited.put(conn, Boolean.TRUE);
                isCyclic = isCyclic || isCyclic(conn, visited);
                if (isCyclic) {
                    return true;
                }
                visited.put(conn, Boolean.FALSE);
            }
        }
        return isCyclic;
    }

    public static void main(String[] args) {

        CyclicGraph graph = new CyclicGraph();
        graph.createTestGraph();
        System.out.println("IsCyclic=" + graph.isCyclic());

    }
}
