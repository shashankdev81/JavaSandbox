package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraAlgorithmWithNode {

    private Map<Integer, Node> idToNodeMap;

    public DijkstraAlgorithmWithNode(int[][] edges) {
        idToNodeMap = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            Node n1 = new Node(edges[i][0]);
            Node n2 = new Node(edges[i][1]);
            idToNodeMap.putIfAbsent(edges[i][0], n1);
            idToNodeMap.putIfAbsent(edges[i][1], n2);
            idToNodeMap.get(edges[i][0]).addEdge(n2, edges[i][2]);
        }
    }

    public class Node {

        private int val;

        private Map<Node, Integer> edgeMap;


        public Node(int val) {
            this.val = val;
            this.edgeMap = new HashMap<>();
        }

        public void addEdge(Node edge, int weight) {
            this.edgeMap.put(edge, weight);

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return val == node.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

    public static void main(String[] args) {
        DijkstraAlgorithmWithNode dijkstraAlgorithmWithNode = new DijkstraAlgorithmWithNode(
            new int[][]{{1, 2, 2}, {2, 4, 7}, {4, 6, 1}, {1, 3, 4}, {3, 5, 3}, {5, 6, 5}, {2, 3, 1},
                {5, 4, 2}});
        Map<Integer, Integer> distMap = dijkstraAlgorithmWithNode.getShortestPath(1);
        System.out.println(distMap);

    }

    private Map<Integer, Integer> getShortestPath(int source) {
        Map<Integer, Integer> distMap = new HashMap<>();
        for (Node v : idToNodeMap.values()) {
            if (idToNodeMap.get(source).edgeMap.containsKey(v)) {
                distMap.putIfAbsent(v.val, idToNodeMap.get(source).edgeMap.get(v));
            } else {
                distMap.putIfAbsent(v.val, Integer.MAX_VALUE);
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>((n1, n2) -> {
            return distMap.get(n1.val) - distMap.get(n2.val);

        });
        Set<Integer> visited = new HashSet<>();
        queue.offer(idToNodeMap.get(source));
        while (!queue.isEmpty()) {
            Node u = queue.poll();
            visited.add(u.val);
            //d(u)+c(u,v) < d(v)
            for (Node v : u.edgeMap.keySet()) {
                if (visited.contains(v.val)) {
                    continue;
                }
                if (distMap.get(u.val) != Integer.MAX_VALUE
                    && distMap.get(u.val) + u.edgeMap.get(v) < distMap.get(v.val)) {
                    distMap.put(v.val, distMap.get(u.val) + u.edgeMap.get(v));
                }
                queue.offer(v);
            }
        }
        return distMap;
    }


}
