package graph;


import java.util.*;

public class DijkstraAlgorithm {


    private Map<Integer, Map<Integer, Integer>> adjacencies = new HashMap<Integer, Map<Integer, Integer>>();


    public DijkstraAlgorithm withAdjcacency(int source, int target, int value) {
        adjacencies.putIfAbsent(source, new HashMap<Integer, Integer>());
        adjacencies.get(source).put(target, value);
        adjacencies.putIfAbsent(target, new HashMap<Integer, Integer>());
        adjacencies.get(target).put(source, value);
        return this;
    }


    private class Node implements Comparable<Node> {
        private Integer dist;
        private Integer value;

        public Node(Integer dist, Integer value) {
            this.dist = dist;
            this.value = value;
        }

        @Override
        public int compareTo(Node o) {
            return value.compareTo(o.dist);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public int getShortestPath(int source, int target, int distFromSource) {
        if (source == target) {
            return distFromSource;
        }
        int[] cost = new int[adjacencies.size()];
        Arrays.fill(cost, Integer.MAX_VALUE);
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> unVisited = new PriorityQueue<>();
        cost[source] = 0;
        unVisited.add(new Node(0, source));
        while (!unVisited.isEmpty()) {
            Node node = unVisited.poll();
            visited.add(node);
            for (Map.Entry<Integer, Integer> neighbourAndDist : adjacencies.get(node.value).entrySet()) {
                if (!visited.contains(new Node(0, neighbourAndDist.getKey()))) {
                    cost[neighbourAndDist.getKey()] = Math.min(cost[neighbourAndDist.getKey()], node.dist + neighbourAndDist.getValue());
                    unVisited.add(new Node(cost[neighbourAndDist.getKey()], neighbourAndDist.getKey()));
                }
            }
        }
        return cost[target];

    }

    public static void main(String[] args) {
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm().withAdjcacency(0, 1, 4)
                .withAdjcacency(0, 7, 8).withAdjcacency(1, 7, 11)
                .withAdjcacency(1, 2, 8).withAdjcacency(7, 8, 7)
                .withAdjcacency(2, 8, 2).withAdjcacency(7, 6, 1)
                .withAdjcacency(2, 5, 4).withAdjcacency(6, 5, 2)
                .withAdjcacency(3, 4, 9).withAdjcacency(3, 5, 14)
                .withAdjcacency(5, 4, 10).withAdjcacency(8, 6, 6)
                .withAdjcacency(2, 3, 7);

//        System.out.println("Shortest path=" + dijkstraAlgorithm.getShortestPath(0, 4, 0));
//        System.out.println("Shortest path=" + dijkstraAlgorithm.getShortestPath(0, 8, 0));

        dijkstraAlgorithm = new DijkstraAlgorithm()
                .withAdjcacency(0, 1, 4)
                .withAdjcacency(1, 3, 2)
                .withAdjcacency(3, 2, 1)
                .withAdjcacency(1, 2, 5);
        System.out.println("Shortest path=" + dijkstraAlgorithm.getShortestPath(0, 2, 0));

    }
}
