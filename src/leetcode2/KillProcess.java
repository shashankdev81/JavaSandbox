package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class KillProcess {

    public static void main(String[] args) {
        KillProcess stringCompress = new KillProcess();

    }

    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        Map<Integer, Node> pidMap = new HashMap<>();
        for (int i = 0; i < pid.size(); i++) {
            pidMap.putIfAbsent(ppid.get(i), new Node(ppid.get(i)));
            pidMap.putIfAbsent(pid.get(i), new Node(pid.get(i)));
            pidMap.get(ppid.get(i)).children.add(pidMap.get(pid.get(i)));
        }
        return pidMap.get(kill).kill();
    }

    public class Node {

        int pid;
        List<Node> children;

        public Node(int pid) {
            this.pid = pid;
            children = new ArrayList<>();
        }


        public List<Integer> kill() {
            List<Integer> killed = new ArrayList<>();
            killed.add(pid);
            for (Node child : children) {
                killed.addAll(child.kill());
            }
            return killed;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return pid == node.pid;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pid);
        }
    }
//    public int compress(char[] chars) {
//        int k = 0;
//        char prev = chars[0];
//        int count = 1;
//        for (int i = 1; i < chars.length; i++) {
//            if (chars[i] == prev) {
//                count++;
//            } else {
//                prev = chars[i];
//                chars[k] = chars[i - 1];
//                if (count > 1) {
//                    char[] countStr = String.valueOf(count).toCharArray();
//                    for (int j = 0; j < countStr.length; j++) {
//                        chars[++k] = countStr[j];
//                    }
//                }
//                k++;
//                count = 1;
//            }
//        }
//        chars[k] = chars[chars.length - 1];
//        char[] countStr = String.valueOf(count).toCharArray();
//        if (count > 1) {
//            for (int j = 0; j < countStr.length; j++) {
//                chars[++k] = countStr[j];
//            }
//        }
//        k++;
//        return k;
//
//    }
}
