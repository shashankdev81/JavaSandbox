package leetcode2;


import java.util.HashMap;
import java.util.Map;

class CroackingFrogs {

    public static void main(String[] args) {
        CroackingFrogs croackingFrogs = new CroackingFrogs();
        System.out.println(croackingFrogs.minNumberOfFrogs("croakccccc"));
    }

    public int minNumberOfFrogs(String croakOfFrogs) {
        Map<Character, Integer> map = new HashMap<>();
        int activeFrogs = 0;
        int ans = -1;
        for (char c : croakOfFrogs.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
            if (c == 'c')
                activeFrogs++;
            if (c == 'k')
                activeFrogs--;
            ans = Math.max(ans, activeFrogs);

            if (map.getOrDefault('c', 0) < map.getOrDefault('r', 0)
                    || map.getOrDefault('r', 0) < map.getOrDefault('o', 0)
                    || map.getOrDefault('o', 0) < map.getOrDefault('a', 0)
                    || map.getOrDefault('a', 0) < map.getOrDefault('k', 0))
                return -1;
        }
        return activeFrogs == 0 ? ans : -1;
    }

}