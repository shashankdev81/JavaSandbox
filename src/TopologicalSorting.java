import java.util.*;
import java.util.stream.Collectors;

public class TopologicalSorting {

    private List<Node> graph;

    private class Node {
        private int value;

        private List<Node> adjacencies;

        public Node(int value) {
            this.value = value;
            adjacencies = new ArrayList<Node>();
        }

        private Node addAdjacency(Node node) {
            adjacencies.add(node);
            return this;
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


    public TopologicalSorting(Map<Integer, List<Integer>> connections) {
        graph = new ArrayList<Node>();
        Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
        for (Map.Entry<Integer, List<Integer>> entry : connections.entrySet()) {
            if (!nodeMap.containsKey(entry.getKey())) {
                nodeMap.put(entry.getKey(), new Node(entry.getKey()));
            }
            Node curr = nodeMap.get(entry.getKey());
            graph.add(curr);
            for (Integer conn : entry.getValue()) {
                Node adj = new Node(conn);
                if (!nodeMap.containsKey(conn)) {
                    nodeMap.put(conn, adj);
                }
                curr.addAdjacency(nodeMap.get(conn));
            }
        }

    }

    public List<Integer> topologicalSort() {
        Set<Node> visited = new HashSet<Node>();
        Stack<Node> sorted = new Stack<Node>();
        for (Node node : graph) {
            sort(node, visited, sorted);
        }
        List<Integer> sortedNums = new ArrayList<Integer>();
        while (!sorted.isEmpty()) {
            sortedNums.add(sorted.pop().value);
        }
        System.out.println(sortedNums);
        return sortedNums;
    }

    private void sort(Node curr, Set<Node> visited, Stack<Node> sorted) {
        if (visited.contains(curr)) {
            return;
        }
        visited.add(curr);
        if (curr.adjacencies.isEmpty()) {
            sorted.push(curr);
            return;
        }
        for (Node adj : curr.adjacencies) {
            if (visited.contains(adj)) {
                continue;
            }
            sort(adj, visited, sorted);
        }
        sorted.push(curr);
    }

    public static void main(String[] args) {
        Map<Integer, List<Integer>> adjacencies = new HashMap<Integer, List<Integer>>();
        adjacencies.put(5, Arrays.stream((new int[]{2, 0})).boxed().collect(Collectors.toList()));
        adjacencies.put(4, Arrays.stream((new int[]{0, 1})).boxed().collect(Collectors.toList()));
        adjacencies.put(2, Arrays.stream((new int[]{3})).boxed().collect(Collectors.toList()));
        adjacencies.put(3, Arrays.stream((new int[]{1})).boxed().collect(Collectors.toList()));
        adjacencies.put(0, new ArrayList<Integer>());
        adjacencies.put(1, new ArrayList<Integer>());
        TopologicalSorting sorting = new TopologicalSorting(adjacencies);
        System.out.println(sorting.topologicalSort());
    }

}
