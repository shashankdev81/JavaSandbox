package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentCacheSimplified<K, V> implements Cache<K, V> {


    private int size;

    private Map<K, Node<V>> cache;

    private AtomicReference<Node<V>> head;

    private AtomicReference<Node<V>> tail;

    private IEvictionStrategy evictionStrategy;
    private Node<V> currHead;

    public enum CacheType {
        LRU, MRU;
    }

    public ConcurrentCacheSimplified(int size, CacheType type) {
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
        //evict is no place
        if (cache.size() == size) {
            evictionStrategy.evict();
        }

        Node<V> currNode = cache.get(key);
        if (currNode != null) {
            currNode.value = value;
            get(key);
            return true;
        }

        //just add to cache
        currNode = new Node<V>(key, value, null, head.get());
        cache.put(key, currNode);
        insertAtHead(head.get(), currNode);
        //in case there is only node this is also the tail
        tail.compareAndSet(null, currNode);

        return true;
    }

    private void insertAtHead(Node<V> oldHead, Node<V> currNode) {
        //in loop CAS curr node as head
        while (!head.compareAndSet(oldHead, currNode)) {
            oldHead = head.get();
            currNode.next = oldHead;
        }
        if (oldHead != null) {
            oldHead.prev = currNode;
        }
    }


    public V get(K key) {
        Node<V> node = cache.get(key);
        try {
            remove(key);
            put(key, node.value);
        } catch (Exception e) {
            //ignore
            e.printStackTrace();
        }
        return node.value;
    }

    private boolean isOnlyNode() {
        return cache.size() == 1;
    }

    /*
    removed node can be head, tail or in between node or only node
    * */
    public V remove(K key) throws Exception {
        Node<V> currNode = cache.get(key);
        if (currNode == null) {
            return null;
        }
        while (!tryLockNodeParentAndChild(currNode)) {
            //currNode = cache.get(key);
        }

        if (currNode.isOrphan || (currNode.prev != null && currNode.prev.isOrphan) ||
                (currNode.next != null && currNode.next.isOrphan)) {
            throw new Exception("Node already removed");
        }
        currNode.isOrphan = true;
        if (currNode.prev == null) {
            head.compareAndSet(head.get(), currNode.next);
        } else {
            currNode.prev.next = currNode.next;
        }
        if (currNode.next == null) {
            tail.compareAndSet(tail.get(), currNode.prev);
        } else {
            currNode.next.prev = currNode.prev;
        }

        cache.remove(key);
        unLockNodeParentAndChild(currNode);
        return currNode.value;
    }

    private boolean tryLockNodeParentAndChild(Node<V> currNode) {
        boolean isCurrLocked = currNode.lock.tryLock();
        boolean isPrevLocked = (currNode.prev == null && head.get().lock.tryLock()) || (currNode.prev != null && currNode.prev.lock.tryLock());
        boolean isNextLocked = (currNode.next == null && tail.get().lock.tryLock()) || (currNode.next != null && currNode.next.lock.tryLock());
        boolean isAcquired = isCurrLocked && isPrevLocked && isNextLocked;
        if (!isAcquired) {
            if (isCurrLocked) currNode.lock.unlock();
            if (isNextLocked) {
                if (currNode.next == null) {
                    tail.get().lock.unlock();
                } else {
                    currNode.next.lock.unlock();
                }
            }
            if (isPrevLocked) {
                if (currNode.prev == null) {
                    head.get().lock.unlock();
                } else {
                    currNode.prev.lock.unlock();
                }
            }
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

        public Node(K key, V value, Node p, Node n) {
            this.key = key;
            this.value = value;
            this.prev = p;
            this.next = n;
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
        ConcurrentCacheSimplified<Integer, String> cache = new ConcurrentCacheSimplified<Integer, String>(5, CacheType.LRU);

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
