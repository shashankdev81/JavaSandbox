package object_orinted_design;

import java.util.HashMap;
import java.util.Map;

public class SkipList {

    private static final int LEVELS = 5;

    private Node sentinel;

    private class Node {

        private Integer val;

        private Map<Integer, Node> levelMap;

        public Node(Integer val) {
            this.val = val;
            this.levelMap = new HashMap<>();
        }

    }

    public void init(int[] arr) {
        sentinel = new Node(null);
        Node curr = sentinel;
        for (int i = 0; i < arr.length; i++) {
            curr.levelMap.put(0, new Node(arr[i]));
            curr = curr.levelMap.get(0);
        }
        curr = sentinel;
        Node next = curr;
        for (int i = 1; i < LEVELS; i++) {
            while (curr != null) {
                curr = next;
                int count = 2 * i;
                while (next != null && count > 0) {
                    next = next.levelMap.get(0);
                    count--;
                }
                curr.levelMap.put(i, next);
            }
        }
    }
}
