package cache;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentCache<K, V> implements Cache<K, V> {


    private int size;

    private Map<K, Node<V>> cache;

    private AtomicReference<Node<V>> head;

    private AtomicReference<Node<V>> tail;

    private IEvictionStrategy evictionStrategy;
    private Node<V> currHead;

    public enum CacheType {
        LRU, MRU;
    }

    public ConcurrentCache(int size, CacheType type) {
        this.size = size;
        cache = new ConcurrentHashMap<K, Node<V>>(size);
        if (type.equals(CacheType.LRU)) {
            evictionStrategy = new LRUEvictionStrategy();
        } else {
            evictionStrategy = new MRUEvictionStrategy();
        }
        head = new AtomicReference<Node<V>>();
        tail = new AtomicReference<Node<V>>();

    }

    public boolean put(K key, V value) {
        if (cache.size() == size) {
            evictionStrategy.evict();
        }

        if (cache.containsKey(key)) {
            while (true) {
                try {
                    remove(key);
                    break;
                } catch (ConcurrentModificationException c) {
                    //try again
                }
            }
        }
        Node<V> oldHead = head.get();
        Node<V> currNode = new Node<V>(key, value);
        cache.put(key, currNode);
        if (isOnlyNode()) {
            tail.compareAndSet(null, currNode);
        } else {
            currNode.next = head.get();
            currNode.prev = null;
        }
        while (true) {
            if (head.compareAndSet(oldHead, currNode)) {
                if (oldHead != null) {
                    oldHead.prev = currNode;
                }
                break;
            } else {
                oldHead = head.get();
            }
        }
        return true;
    }


    public V get(K key) {
        V val = remove(key);
        put(key, val);
        return head.get().value;
    }

    private boolean isOnlyNode() {
        return cache.size() == 1;
    }

    /*
    removed node can be head, tail or in between node or only node
    * */
    public V remove(K key) {
        Node<V> currNode = cache.get(key);
        if (currNode == null) {
            return null;
        }
        while (!tryLockNodeParentAndChild(currNode)) {
            //currNode = cache.get(key);
        }

        if (currNode.isOrphan || (currNode.prev != null && currNode.prev.isOrphan) ||
                (currNode.next != null && currNode.next.isOrphan)) {
            throw new ConcurrentModificationException("Node already removed");
        }
        if (isOnlyNode()) {
            head = null;
            tail = null;
            cache.remove(key);
        } else if (currNode.isHead()) {
            removeHead();
        } else if (currNode.isTail()) {
            removeTail();
        } else {
            removeMiddleNode(currNode);
            unLockNodeParentAndChild(currNode);
            currNode.next = null;
            currNode.prev = null;
        }
        cache.remove(key);
        return currNode.value;
    }

    private boolean tryLockNodeParentAndChild(Node<V> currNode) {
        boolean isCurrLocked = currNode.lock.tryLock();
        boolean isPrevLocked = currNode.prev != null && currNode.prev.lock.tryLock();
        boolean isNextLocked = currNode.next != null && currNode.next.lock.tryLock();
        boolean isAcquired = isCurrLocked && (currNode.prev == null || isPrevLocked) && (currNode.next == null || isNextLocked);
        if (!isAcquired) {
            if (isCurrLocked) currNode.lock.unlock();
            if (isNextLocked) currNode.next.lock.unlock();
            if (isPrevLocked) currNode.prev.lock.unlock();
        }
        return isAcquired;
    }

    private void unLockNodeParentAndChild(Node<V> currNode) {
        currNode.lock.unlock();
        if (currNode.prev != null) {
            currNode.prev.lock.unlock();
        }
        if (currNode.next != null) {
            currNode.next.lock.unlock();
        }
    }

    private void removeMiddleNode(Node<V> currNode) {
        currNode.isOrphan = true;
        currNode.prev.next = currNode.next;
        currNode.next.prev = currNode.prev;
    }

    private class Node<V> {

        private K key;

        private V value;

        private Node<V> next;

        private Node<V> prev;

        private Lock lock;

        private volatile boolean isOrphan = false;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            lock = new ReentrantLock();
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
        Node<V> start = head.get();
        while (start != null) {
            builder.append(start.value).append("-->");
            start = start.next;
        }
        builder.append("NULL");
        return builder.toString();
    }

    public static void main(String[] args) {
        ConcurrentCache<Integer, String> cache = new ConcurrentCache<Integer, String>(5, CacheType.LRU);

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
        while (true) {
            Node temp = tail.get();
            Node prev = tail.get().prev;
            if (tail.compareAndSet(temp, prev)) {
                temp.isOrphan = true;
                prev.next = null;
                temp.prev = null;
                cache.remove(temp.key);
                break;
            }
        }
    }

    private class MRUEvictionStrategy implements IEvictionStrategy {
        @Override
        public void evict() {
            removeHead();
        }
    }

    private void removeHead() {
        while (true) {
            Node temp = head.get();
            Node next = head.get().next;
            if (head.compareAndSet(temp, next)) {
                temp.isOrphan = true;
                next.prev = null;
                temp.next = null;
                cache.remove(temp.key);
                break;
            }
        }
    }
}
