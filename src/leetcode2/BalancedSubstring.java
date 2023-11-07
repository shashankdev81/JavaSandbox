package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

class BalancedSubstring {

    public static void main(String[] args) {

        BalancedSubstring balancedSubstring = new BalancedSubstring();
        System.out.println(balancedSubstring.balancedString("QQQE"));
    }

    boolean check(Map<Character, Integer> mp, int max) {
        for (var ent : mp.entrySet()) {
            if (ent.getValue() > max) return false;
        }
        return true;
    }

    public int balancedString(String s) {
        Map<Character, Integer> mp = new HashMap<>();
        int n = s.length();
        int max = n / 4;
        for (int i = 0; i < n; i++) {
            mp.put(s.charAt(i), mp.getOrDefault(s.charAt(i), 0) + 1);
        }
        int ans = n;
        int l = 0, r = 0;
        for (; l <= r && r < n; r++) {
            mp.put(s.charAt(r), mp.get(s.charAt(r)) - 1);
            if (check(mp, max)) {
                ans = Math.min(ans, r - l + 1);
                for (; l <= r; ) {
                    mp.put(s.charAt(l), mp.get(s.charAt(l)) + 1);
                    l++;
                    if (check(mp, max)) ans = Math.min(ans, r - l + 1);
                    else break;
                }
            }
        }
        return ans;
    }
}