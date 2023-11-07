package leetcode2;


import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.stream.Collectors;

class CabSharing {
    //2 1->3->5->7
    public boolean carPooling(int[][] trips, int capacity) {
        PriorityQueue<PickOrDrop> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < trips.length; i++) {
            PickOrDrop pickup = new PickOrDrop(trips[i][1], false, trips[i][0]);
            PickOrDrop drop = new PickOrDrop(trips[i][2], true, -1 * trips[i][0]);
            priorityQueue.add(pickup);
            priorityQueue.add(drop);
        }
        int maxCap = 0;
        while (!priorityQueue.isEmpty()) {
            maxCap += priorityQueue.poll().capacityChange;
            if (maxCap > capacity) {
                return false;
            }
        }
        return true;
    }

    public class PickOrDrop implements Comparable<PickOrDrop> {
        int location;
        boolean isDrop;
        int capacityChange;

        public boolean equals(Object o) {
            if (o == null || o.getClass() != getClass()) {
                return false;
            }
            PickOrDrop ext = (PickOrDrop) o;
            return location == ext.location && isDrop == ext.isDrop;

        }

        public PickOrDrop(int loc, boolean isDropFlag, int change) {
            location = loc;
            isDrop = isDropFlag;
            capacityChange = change;
        }

        public int compareTo(PickOrDrop pd) {
            return Integer.compare(this.location, pd.location);
        }
    }

}