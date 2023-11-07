package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

class Pair {
    int pos;
    boolean isBackward;

    Pair(int pos, boolean isBackward) {
        this.pos = pos;
        this.isBackward = isBackward;
    }
}


class MinJumps2 {

    public static void main(String[] args) {
        MinJumps2 minJumps2 = new MinJumps2();
        //System.out.println(minJumps2.minimumJumps(new int[]{8, 3, 16, 6, 12, 20}, 15, 13, 11));
        System.out.println(minJumps2.minimumJumps(new int[]{1, 6, 2, 14, 5, 17, 4}, 16, 9, 7));
    }

    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        Set<Integer> visited = new HashSet<>();
        for (int e : forbidden) {
            visited.add(e);
        }

        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(0, false));
        visited.add(0);

        int limit = 6000;// given in constarints (2000+2000+2000)
        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int pos = q.peek().pos;
                boolean isBackward = q.peek().isBackward;
                q.poll();

                if (pos == x) return level;

                if (!isBackward) {

                    int nextBackPos = pos - b;
                    if (nextBackPos > 0 && !visited.contains(nextBackPos)) {
                        visited.add(nextBackPos);
                        q.add(new Pair(nextBackPos, true));
                    }

                }
                int nextForPos = pos + a;
                if (nextForPos < limit && !visited.contains(nextForPos)) {
                    visited.add(nextForPos);
                    q.add(new Pair(nextForPos, false));
                }
            }
            level++;
        }
        return -1;
    }
}