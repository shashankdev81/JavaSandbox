package leetcode2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

public class AlienDictionary {

    public static void main(String[] args) {
        AlienDictionary alienDictionary = new AlienDictionary();
        System.out.println(
            alienDictionary.alienOrder(new String[]{"wrt", "wrf", "er", "ett", "rftt"}));
    }

    public String alienOrder(String[] words) {
        final Map<Character, CharObj> charObjFactory = new HashMap<>();
        final Map<CharObj, Set<CharObj>> charMap = new HashMap<>();
        Arrays.stream(words).map(w -> w.toCharArray()).forEach(arr -> {

            for (int i = 0; i < arr.length; i++) {
                charObjFactory.putIfAbsent(arr[i], new CharObj(arr[i]));
                if (i < arr.length - 1) {
                    charObjFactory.putIfAbsent(arr[i + 1], new CharObj(arr[i + 1]));
                    charMap.putIfAbsent(charObjFactory.get(arr[i]), new HashSet<>());
                    charMap.get(charObjFactory.get(arr[i])).add(charObjFactory.get(arr[i + 1]));
                }
            }
        });
        Set<CharObj> visited = new HashSet<>();
        List<CharObj> sorted = new ArrayList<>();
        Stack<CharObj> stack = new Stack<>();
        for (CharObj charObj : charMap.keySet()) {
            sort(charMap, charObj, visited, stack);
        }
        while (!stack.isEmpty()) {
            sorted.add(stack.pop());
        }
        return sorted.stream().map(s -> String.valueOf(s.c)).reduce((s, s2) -> s + s2)
            .orElseGet(() -> "");
    }

    private void sort(Map<CharObj, Set<CharObj>> charMap, CharObj charObj,
        Set<CharObj> visited, Stack<CharObj> stack) {
        if (visited.contains(charObj)) {
            return;
        }
        visited.add(charObj);
        for (CharObj nextCharObj : charMap.get(charObj)) {
            sort(charMap, nextCharObj, visited, stack);
        }
        stack.push(charObj);
    }


    private class CharObj {

        private Character c;
        private boolean isVisited;

        public CharObj(Character c) {
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CharObj charObj = (CharObj) o;
            return Objects.equals(c, charObj.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c);
        }
    }

}
