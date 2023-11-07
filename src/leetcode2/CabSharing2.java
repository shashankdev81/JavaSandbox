package leetcode2;


import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

class CabSharing2 {

    public static void main(String[] args) {
        CabSharing2 cabSharing2 = new CabSharing2();
        System.out.println(cabSharing2.carPooling(new int[][]{{2, 6, 8}, {2, 2, 6}}, 2));

    }

    public boolean carPooling(int[][] trips, int capacity) {
        Map<PickOrDrop, PickOrDrop> tripMap = new TreeMap<>();
        for (int i = 0; i < trips.length; i++) {
            PickOrDrop pickup = new PickOrDrop(trips[i][1], false, trips[i][0]);
            PickOrDrop drop = new PickOrDrop(trips[i][2], true, -1 * trips[i][0]);
            if (tripMap.containsKey(pickup)) {
                tripMap.get(pickup).capacityChange += pickup.capacityChange;
            } else {
                tripMap.put(pickup, pickup);
            }
            if (tripMap.containsKey(drop)) {
                tripMap.get(drop).capacityChange += drop.capacityChange;
            } else {
                tripMap.put(drop, drop);
            }
        }
        int maxCap = 0;
        for (PickOrDrop leg : tripMap.values()) {
            maxCap += leg.capacityChange;
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