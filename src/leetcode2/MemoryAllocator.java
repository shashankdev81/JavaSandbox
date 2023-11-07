package leetcode2;

import java.util.*;

class MemoryAllocator {

    public static void main(String[] args) {
        MemoryAllocator memoryAllocator = new MemoryAllocator(10);
        memoryAllocator.allocate(1, 1);
        memoryAllocator.allocate(1, 2);
        memoryAllocator.allocate(1, 3);
        memoryAllocator.free(2);
        memoryAllocator.allocate(3, 4);
        memoryAllocator.allocate(1, 1);
        memoryAllocator.allocate(1, 1);
        memoryAllocator.free(1);
        memoryAllocator.allocate(10, 2);
        memoryAllocator.free(7);
    }

    Map<Integer, TreeMap<Integer, Integer>> memory = new HashMap<>();

    public MemoryAllocator(int n) {
        TreeMap<Integer, Integer> ranges = new TreeMap<>(); // <startAdd, endAdd>
        ranges.put(0, n - 1); // address start from 0
        memory.put(0, ranges); // 0 means free block
    }

    public int allocate(int size, int mID) {
        int addr = -1;
        int[] availableRange = new int[]{-1, -1};
        for (Map.Entry<Integer, Integer> range : memory.get(0).entrySet()) {
            int startAdd = range.getKey();
            int endAdd = range.getValue();
            if (endAdd - startAdd + 1 >= size) {
                addr = startAdd;
                availableRange[0] = startAdd;
                availableRange[1] = endAdd;
                break;
            }
        }
        if (addr != -1) {
            memory.computeIfAbsent(mID, x -> new TreeMap<>()).put(availableRange[0], availableRange[0] + size - 1);
            memory.get(0).remove(availableRange[0]);
            if (availableRange[1] - availableRange[0] + 1 > size)
                memory.get(0).put(availableRange[0] + size, availableRange[1]);
        }

        mergeRanges(mID);

        return addr;
    }

    public int free(int mID) {
        int cnt = 0;
        TreeMap<Integer, Integer> freeRanges = memory.get(mID);

        if (freeRanges != null) {
            for (Map.Entry<Integer, Integer> range : freeRanges.entrySet()) {
                int startAdd = range.getKey();
                int endAdd = range.getValue();
                cnt += endAdd - startAdd + 1;
                memory.get(0).put(startAdd, endAdd);
            }
        }

        memory.remove(mID);
        mergeRanges(0);
        return cnt;
    }

    private void mergeRanges(int mID) {
        TreeMap<Integer, Integer> curRanges = memory.get(mID);
        TreeMap<Integer, Integer> mergedRanges = new TreeMap<>();
        int[] lastRange = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        if (curRanges != null) {
            for (Map.Entry<Integer, Integer> range : curRanges.entrySet()) {
                int startAdd = range.getKey();
                int endAdd = range.getValue();
                if (startAdd - 1 == lastRange[1]) {
                    lastRange[1] = endAdd;
                } else {
                    if (lastRange[0] != Integer.MIN_VALUE) mergedRanges.put(lastRange[0], lastRange[1]);
                    lastRange[0] = startAdd;
                    lastRange[1] = endAdd;
                }
            }
        }
        if (lastRange[0] != Integer.MIN_VALUE) mergedRanges.put(lastRange[0], lastRange[1]);

        memory.put(mID, mergedRanges);
    }

}
