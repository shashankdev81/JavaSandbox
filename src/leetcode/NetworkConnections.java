package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class NetworkConnections {

    private Map<Integer, Node> valToNodeMap = new HashMap<Integer, Node>();

    private class Node {
        private int val;
        private List<Node> adjcs;

        public Node(int val) {
            this.val = val;
            this.adjcs = new ArrayList<Node>();
        }
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> criticalConns = new ArrayList<List<Integer>>();
        for (List<Integer> conn : connections) {
            valToNodeMap.putIfAbsent(conn.get(0), new Node(conn.get(0)));
            valToNodeMap.putIfAbsent(conn.get(1), new Node(conn.get(1)));
            valToNodeMap.get(conn.get(0)).adjcs.add(valToNodeMap.get(conn.get(1)));
            valToNodeMap.get(conn.get(1)).adjcs.add(valToNodeMap.get(conn.get(0)));
        }
        for (List<Integer> conn : connections) {
            if (!isSafeEdge(valToNodeMap.get(conn.get(0)), valToNodeMap.get(conn.get(1)), valToNodeMap.size())) {
                criticalConns.add(conn);
            }
        }

        return criticalConns;
    }

    private boolean isSafeEdge(Node left, Node right, int nodes) {
        List<Node> adjs1 = left.adjcs.stream().filter(n -> n.val != right.val).collect(Collectors.toList());
        List<Node> adjs2 = right.adjcs.stream().filter(n -> n.val != left.val).collect(Collectors.toList());
        Set<Integer> leftVisited = new HashSet<Integer>();
        leftVisited.add(left.val);
        getAllNodesVisitable(adjs1, leftVisited);
        Set<Integer> rightVisited = new HashSet<Integer>();
        rightVisited.add(right.val);
        getAllNodesVisitable(adjs2, rightVisited);
        return leftVisited.size() == nodes && rightVisited.size() == nodes;
    }

    private void getAllNodesVisitable(List<Node> adjcs, Set<Integer> visited) {
        for (Node node : adjcs) {
            if (!visited.contains(node.val)) {
                visited.add(node.val);
                getAllNodesVisitable(node.adjcs, visited);
            }
        }
        return;
    }

    public static void main(String[] args) {
        NetworkConnections connections = new NetworkConnections();
        Integer[][] connArr = {{0, 1}, {1, 2}, {2, 0}, {1, 3}};
        List<List<Integer>> criticalConns = new ArrayList<List<Integer>>();

        for (Integer[] conn : connArr) {
            List<Integer> connList = new ArrayList<>();
            connList.add(conn[0]);
            connList.add(conn[1]);
            criticalConns.add(connList);
        }
        System.out.println(connections.criticalConnections(criticalConns.size(), criticalConns));
    }
}
