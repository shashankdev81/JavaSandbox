package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

public class LockSmith {

    public static void main(String[] args) {
        LockSmith lockSmith = new LockSmith();
        System.out.println(lockSmith.openLock(new String[]{"0201", "0101", "0102", "1212", "2002"}, "0202"));
    }

    public int openLock(String[] deadends, String target) {
        String lock = new String("0000");
        Set<String> blocked = new HashSet<>();
        for (int i = 0; i < deadends.length; i++) {
            blocked.add(deadends[i]);
        }
        return dfs(lock, blocked, target);
    }

    private int dfs(String lock, Set<String> blocked, String target) {
        //int a = ch - '0';
        System.out.println(lock + "," + blocked + "," + target);
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(lock);
        Set<String> visited = new HashSet<>();
        visited.add("0000");
        int attempts = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();
            while (count > 0) {
                String curr = queue.poll();
                System.out.println(curr);
                visited.add(curr);
                if (target.equals(curr)) {
                    System.out.println("Match:" + curr + "," + target);
                    return attempts;
                }
                for (int w = 0; w < 4; w++) {
                    for (int j = -1; j < 2; j += 2) {
                        String next = upDown(curr, w, j);
                        if (!visited.contains(next) && !blocked.contains(next)) {
                            attempts++;
                            queue.offer(next);
                        }
                    }
                }
                count--;
            }
        }
        return attempts == 0 ? -1 : attempts;
    }

    private String upDown(String lock, int wheelIndex, int j) {
        String newLock = new String(lock);
        char c = newLock.charAt(wheelIndex);
        String upDown = getUpDownChar(j, c);
        newLock = newLock.substring(0, wheelIndex) + upDown + newLock.substring(wheelIndex + 1, lock.length());
        return newLock;
    }

    private String getUpDownChar(int j, char c) {
        int upDown = 0;
        if (c == '0') {
            upDown = j == -1 ? 1 : 9;
        } else if (c == '9') {
            upDown = j == -1 ? 0 : 8;
        } else {
            upDown = c - '0' + j;
        }
        Map<Integer, List<Integer>> roadMap = new HashMap<>();
        List<Integer> endCities = roadMap.entrySet().stream().filter(e -> e.getValue().size()==1).map(e -> e.getKey()).collect(Collectors.toList());


        return String.valueOf(upDown);

    }
}