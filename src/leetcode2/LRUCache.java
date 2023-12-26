package leetcode2;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LRUCache {


    private Map<Integer, Node> cacheMap = new HashMap<>();

    private Node head = new Node(-1, -1, null, null);

    private Node tail = null;

    private int size = 0;

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(2, 1);
        lruCache.put(1, 1);
        lruCache.put(2, 3);
        lruCache.put(4, 1);
        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(2));
    }

    public LRUCache(int capacity) {
        this.size = capacity;

    }

    public int get(int key) {
        Node node = cacheMap.get(key);
        if (node == null) {
            return -1;
        }
        int res = node.val;
        diconnect(node);
        tail.next = node;
        node.prev = tail;
        tail = node;
        return res;
    }

    private void diconnect(Node node) {
        node.prev.next = node.next;
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node != null) {
            node.next = null;
            node.prev = null;
        }

    }

    public void put(int key, int value) {
        Node node = new Node(key, value, null, null);
        if (cacheMap.size() == size && !cacheMap.containsKey(key)) {
            Node removed = head.next;
            head = head.next;
            cacheMap.remove(removed.key);
        } else if (cacheMap.containsKey(key)) {
            Node removed = cacheMap.remove(key);
            diconnect(removed);
        }
        if (head.next == null) {
            head.next = node;
        }
        if (tail == null) {
            tail = node;
            node.prev = head;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = tail.next;
        }
        cacheMap.put(key, node);

    }

    class Node {

        int key;
        int val;
        Node next;
        Node prev;

        public Node(int key, int val, Node next, Node prev) {
            this.key = key;
            this.val = val;
            this.next = next;
            this.prev = prev;
        }
    }

}
