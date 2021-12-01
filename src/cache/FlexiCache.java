package cache;

import java.util.HashMap;
import java.util.Map;

public class FlexiCache<K, V> implements Cache<K, V> {

    private int size;

    private Map<K, Node<V>> cache;

    private Node<V> head;

    private Node<V> tail;

    private IEvictionStrategy evictionStrategy;

    public enum CacheType {
        LRU, MRU;
    }

    public FlexiCache(int size, CacheType type) {
        this.size = size;
        cache = new HashMap<K, Node<V>>(size);
        if (type.equals(CacheType.LRU)) {
            evictionStrategy = new LRUEvictionStrategy();
        } else {
            evictionStrategy = new MRUEvictionStrategy();
        }
    }

    public boolean put(K key, V value) {
        if (cache.size() == size) {
            evictionStrategy.evict();
        }

        if (cache.containsKey(key)) {
            remove(key);
        }
        Node<V> currNode = new Node<V>(key, value);
        cache.put(key, currNode);
        if (isOnlyNode()) {
            tail = currNode;
        } else {
            currNode.next = head;
            currNode.prev = null;
            head.prev = currNode;
        }
        head = currNode;
        return true;
    }


    public V get(K key) {
        V val = remove(key);
        put(key, val);
        return head.value;
    }

    private boolean isOnlyNode() {
        return cache.size() == 1;
    }

    /*
    removed node can be head, tail or in between node or only node
    * */
    public V remove(K key) {
        Node<V> currNode = cache.get(key);
        if (isOnlyNode()) {
            head = null;
            tail = null;
        } else if (currNode.isHead()) {
            removeHead();
        } else if (currNode.isTail()) {
            removeTail();
        } else {
            currNode.prev.next = currNode.next;
            currNode.next.prev = currNode.prev;
            currNode.next = null;
            currNode.prev = null;
        }
        cache.remove(key);
        return currNode.value;
    }

    private class Node<V> {

        private K key;

        private V value;

        private Node<V> next;

        private Node<V> prev;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public boolean isHead() {
            return next != null && prev == null;
        }

        public boolean isTail() {
            return prev != null && next == null;
        }

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node<V> start = head;
        while (start != null) {
            builder.append(start.value).append("-->");
            start = start.next;
        }
        builder.append("NULL");
        return builder.toString();
    }

    public static void main(String[] args) {
        FlexiCache<Integer, String> cache = new FlexiCache<Integer, String>(5, CacheType.LRU);

        for (int i = 1; i <= 5; i++) {
            cache.put(i, String.valueOf(i));
        }
        System.out.println("Cache=" + cache);
        cache.get(2);
        System.out.println("Cache=" + cache);
        cache.put(7, String.valueOf(7));
        System.out.println("Cache=" + cache);
    }

    private class LRUEvictionStrategy implements IEvictionStrategy {
        @Override
        public void evict() {
            removeTail();
        }
    }

    private void removeTail() {
        Node temp = tail;
        tail = tail.prev;
        tail.next = null;
        temp.prev = null;
        cache.remove(temp.key);
    }

    private class MRUEvictionStrategy implements IEvictionStrategy {
        @Override
        public void evict() {
            removeHead();
        }
    }

    private void removeHead() {
        Node temp = head;
        head = head.next;
        head.prev = null;
        temp.next = null;
        cache.remove(temp.key);
    }
}
