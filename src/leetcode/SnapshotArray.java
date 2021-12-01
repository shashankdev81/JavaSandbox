package leetcode;

import java.util.*;

public class SnapshotArray {

    private int snapId = 0;


    private class PseudoArray {
        private Map<Integer, Integer> store = new HashMap<Integer, Integer>();
    }

    private Map<Integer, PseudoArray> snapshots = new HashMap<Integer, PseudoArray>();


    public SnapshotArray(int length) {
    }


    public void set(int index, int val) {
        snapshots.putIfAbsent(snapId, new PseudoArray());
        snapshots.get(snapId).store.put(index, val);
    }


    public int snap() {
        return snapId++;
    }

    public int get(int index, int snap_id) {
        if (snap_id == -1) {
            return 0;
        }
        if (snapshots.get(snap_id) == null || !snapshots.get(snap_id).store.containsKey(index)) {
            return get(index, --snap_id);
        } else {
            return snapshots.get(snap_id).store.get(index);
        }

    }


}
