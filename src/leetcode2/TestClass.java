package leetcode2;

import java.util.*;

public class TestClass {

    public static void main(String[] args) {

//        System.out.println(removeDuplicateLetters("bcabc"));
//        System.out.println(removeDuplicateLetters("cbacdcbc"));
        Solution solution = new Solution();

        System.out.println(solution.restoreIpAddresses("25525511135"));
        System.out.println(solution.restoreIpAddresses("101023"));
        System.out.println(solution.restoreIpAddresses("0000"));


    }

    public static String removeDuplicateLetters(String s) {
        char[] chars = s.toCharArray();
        Map<Character, Integer> maxIndex = new HashMap<Character, Integer>();
        for (int i = 0; i < chars.length; i++) {
            maxIndex.put(chars[i], i);
        }
        Set<Character> seen = new HashSet<Character>();
        Stack<Character> result = new Stack<Character>();
        for (int i = 0; i < chars.length; i++) {
            if (seen.contains(chars[i])) {
                continue;
            }
            seen.add(chars[i]);
            while (!result.isEmpty() && result.peek() > chars[i] && maxIndex.get(result.peek()) > i) {
                Character c = result.pop();
                seen.remove(c);
            }
            result.push(chars[i]);

        }
        Character[] arr = new Character[result.size()];
        arr = result.toArray(arr);
        Stack<Character> result2 = new Stack<Character>();
        result2.addAll(result);
        Character[] arr2 = new Character[result2.size()];
        arr2 = result.toArray(arr2);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
        }
        String test = "";
        for (int i = 0; i < arr2.length; i++) {
            test += arr2[i];
        }
        System.out.println("Reverse=" + test);
        return builder.toString();
    }
}

class Solution {

    private List<String> results = new ArrayList();

    public List<String> restoreIpAddresses(String s) {
        results = new ArrayList();
        traverse(s.toCharArray(), 0, new Stack<Character>(), 4);
        return results;
    }

    public void traverse(char[] chars, int i, Stack<Character> soFar, int partsLeft) {
        if (toStr(soFar).equals("25.525.511.")) {
            System.out.println("Here");
        }
        if (isCompleteSequence(chars, i, partsLeft) || isInvalidSequence(chars, i, soFar)) {
            return;
        }
        int partLength = 0;
        String partNum = "";
        for (int j = i; j < chars.length; j++) {
            if (isIncompleteSequence(partsLeft, partLength)) {
                return;
            }
            partNum += chars[j];
            soFar.push(chars[j]);
            partLength++;
            if (partsLeft > 1 && Integer.valueOf(partNum) <= 255) {
                soFar.push('.');
                traverse(chars, j + 1, copyOf(soFar), partsLeft - 1);
                soFar.pop();
            }
        }
        if (toStr(soFar).equals("25.525.511.")) {
            System.out.println("hi");
        }
        results.add(toStr(soFar));
        return;
    }

    private boolean isCompleteSequence(char[] chars, int i, int partsLeft) {
        return i == chars.length && partsLeft == 0;
    }

    private boolean isInvalidSequence(char[] chars, int i, Stack<Character> soFar) {
        return !soFar.isEmpty() && i < chars.length && soFar.peek() == '.' && (chars[i] == '0');
    }

    private boolean isIncompleteSequence(int partsLeft, int partLength) {
        return partLength == 3 && partsLeft > 0;
    }

    private Stack<Character> copyOf(Stack<Character> soFar) {
        Stack<Character> newStack = new Stack<Character>();
        newStack.addAll(soFar);
        return newStack;
    }

    private String toStr(Stack<Character> soFar) {
        Character[] arr = new Character[soFar.size()];
        arr = soFar.toArray(arr);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
        }
        return builder.toString();
    }
}