package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

class BalancedSubstring2 {
    public int balancedString(String s) {
        //List all characters in a list

        Set<Character> alphabetSet = new HashSet<>(Arrays.asList(new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}));
        List<Character> chars = s.toUpperCase().chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        // Remove all characters aleady taken
        for (Character seen : chars) {
            if (alphabetSet.contains(seen)) {
                alphabetSet.remove(seen);
            }
        }
        Queue<Character> alphabetQueue = new ArrayDeque<>(alphabetSet);

        // Iterate over char array and remove a series of chars that are
        Map<Character, Integer> charToOccurances = new HashMap<Character, Integer>();
        //Set<Character> seen = new HashSet<>();
        StringBuilder newString = new StringBuilder();
        int maxLength = s.length() / 4;
        int index = 0;
        for (Character c : chars) {
            charToOccurances.putIfAbsent(c, 0);
            charToOccurances.put(c, charToOccurances.get(c) + 1);
        }
        for (Map.Entry<Character, Integer> entry : charToOccurances.entrySet()) {
            charToOccurances.put(entry.getKey(), entry.getValue() - maxLength);
        }
        for (Character c : chars) {
            if (charToOccurances.get(c) > 0) {
                charToOccurances.put(c, charToOccurances.get(c) - 1);
                newString.append(alphabetQueue.poll());
            } else {
                newString.append(c);
            }
        }
        char[] origStr = s.toCharArray();
        char[] newStr = newString.toString().toCharArray();
        int min = Integer.MAX_VALUE;
        System.out.println("orig, new " + s + "," + newString);
        for (int i = 0; i < origStr.length; i++) {
            if (origStr[i] == newStr[i]) {
                continue;
            }
            int subStrLength = 0;
            while (origStr[i] != newStr[i]) {
                subStrLength++;
                i++;
                if (i == origStr.length) {
                    break;
                }
            }
            if (subStrLength < min) {
                min = subStrLength;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;

    }
}