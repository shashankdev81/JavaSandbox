package leetcode2;

import java.util.*;

class MemoryAllocatorBinarySearch {

    public static void main(String[] args) {
        MemoryAllocatorBinarySearch memoryAllocator = new MemoryAllocatorBinarySearch(10);
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

    private Map<Integer, List<Integer>> memoryAllocMap = new HashMap<>();

    private Set<Integer> memoryList = new TreeSet<>();

    public MemoryAllocatorBinarySearch(int n) {
        for (int i = 0; i < n; i++) {
            memoryList.add(i);
        }
    }

    public int allocate(int size, int mID) {
        ArrayList<Integer> searchSpace = new ArrayList<>(memoryList);
        int firstAddr = -1;
        for (int i = 0; i < searchSpace.size() - 1; i++) {
            if (isContigous(searchSpace.subList(i, i + size))) {
                firstAddr = searchSpace.get(i);
                for (int blockIndex = i; blockIndex < i + size; blockIndex++) {
                    int memoryBlock = searchSpace.get(blockIndex);
                    memoryList.remove(memoryBlock);
                    memoryAllocMap.putIfAbsent(mID, new LinkedList<>());
                    memoryAllocMap.get(mID).add(memoryBlock);
                }
                break;
            }

        }
        return firstAddr;
    }

    private boolean isContigous(List<Integer> memoryList) {
        for (int i = 0; i < memoryList.size() - 1; i++) {
            if (memoryList.get(i + 1) - memoryList.get(i) != 1) {
                return false;
            }
        }
        return true;
    }

    public int free(int mID) {
        List<Integer> freed = memoryAllocMap.get(mID);
        memoryList.addAll(freed);
        memoryAllocMap.remove(mID);
        return freed.size();
    }


}
