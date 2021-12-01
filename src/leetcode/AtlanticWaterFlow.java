package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AtlanticWaterFlow {

    private enum Ocean {
        Pacific, Atlantic;
    }

    private class Node {
        private int value;
        private Node east;
        private Node west;
        private Node north;
        private Node south;
        private Ocean ocean;
        private Boolean isReachable;
        private int row;
        private int col;

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Ocean oc) {
            this.value = value;
            this.ocean = oc;
        }

        public List<Integer> getRowColAsList() {
            List<Integer> res = new ArrayList<Integer>();
            res.add(row);
            res.add(col);
            return res;
        }
    }

    private Node pacific = new Node(Integer.MAX_VALUE, Ocean.Pacific);

    private Node atlantic = new Node(Integer.MIN_VALUE, Ocean.Atlantic);


    private Node[][] graph;

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> resultsAsList = new ArrayList<List<Integer>>();
        buildGraph(heights);
        connectNodes(heights);
        connectWithOcean();
        for (int r = 0; r < heights.length; r++) {
            for (int c = 0; c < heights[0].length; c++) {
                if (isReachable(graph[r][c], Ocean.Pacific, new HashSet<Node>())
                        && isReachable(graph[r][c], Ocean.Atlantic, new HashSet<Node>())) {
                    graph[r][c].isReachable = true;
                    addReachableCell(resultsAsList, r, c);
                }
            }
        }
        return resultsAsList;
    }

    private void connectNodes(int[][] heights) {
        for (int r = 0; r < heights.length; r++) {
            for (int c = 0; c < heights[0].length; c++) {
                graph[r][c].east = connect(r, c, r, c + 1, heights); //east
                graph[r][c].west = connect(r, c, r, c - 1, heights);//west
                graph[r][c].north = connect(r, c, r - 1, c, heights);//north
                graph[r][c].south = connect(r, c, r + 1, c, heights);//south
            }
        }
    }

    private void buildGraph(int[][] heights) {
        graph = new Node[heights.length][heights[0].length];
        for (int r = 0; r < heights.length; r++) {
            for (int c = 0; c < heights[0].length; c++) {
                graph[r][c] = new Node(heights[r][c]);
            }
        }
    }

    private void addReachableCell(List<List<Integer>> resultsAsList, int r, int c) {
        List<Integer> cell = new ArrayList<Integer>();
        cell.add(r);
        cell.add(c);
        resultsAsList.add(cell);
    }

    private void connectWithOcean() {
        for (int r = 0; r < graph.length; r++) {
            graph[r][graph[0].length - 1].east = atlantic;
        }

        for (int r = 0; r < graph.length; r++) {
            graph[r][0].west = pacific;
        }

        for (int c = 0; c < graph[0].length; c++) {
            graph[0][c].north = pacific;
        }

        for (int c = 0; c < graph[0].length; c++) {
            graph[graph.length - 1][c].south = atlantic;
        }

    }

    private boolean isReachable(Node node, Ocean ocean, Set<Node> visited) {
        if (node == null) {
            return false;
        }
        if (ocean.equals(node.ocean) || (node.isReachable != null && node.isReachable.booleanValue())) {
            return true;
        }
        boolean isReachable = false;
        if (node.ocean == null) {
            visited.add(node);
            isReachable =
                    (!visited.contains(node.east) && isReachable(node.east, ocean, visited)) ||
                            (!visited.contains(node.west) && isReachable(node.west, ocean, visited)) ||
                            (!visited.contains(node.north) && isReachable(node.north, ocean, visited)) ||
                            (!visited.contains(node.south) && isReachable(node.south, ocean, visited));
            visited.remove(node);
        }
        return isReachable;
    }

    private Node connect(int r, int c, int nr, int nc, int[][] heights) {
        Node result = null;
        if (nr >= 0 && nr < heights.length && nc >= 0 && nc < heights[0].length) {
            if (heights[r][c] >= heights[nr][nc]) {
                result = graph[nr][nc];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        AtlanticWaterFlow flow = new AtlanticWaterFlow();
        int[][] heights = new int[][]{{1, 2, 2, 3, 5}, {3, 2, 3, 4, 4}, {2, 4, 5, 3, 1}, {6, 7, 1, 4, 5}, {5, 1, 1, 2, 4}};
        List<List<Integer>> results = flow.pacificAtlantic(heights);
        System.out.println(results);
    }
}
