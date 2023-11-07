package leetcode2;

import java.util.ArrayList;
import java.util.List;

public class IPAddress {

    public static void main(String[] args) {
        IPAddress ipAddress = new IPAddress();
        ipAddress.restoreIpAddresses("0000");
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> results = new ArrayList<>();
        if (s != null && !s.isEmpty()) {
            results = traverse("", "", s, 0);
        }
        return results;
    }

    public List<String> traverse(String sequence, String part, String remaining, int parts) {
        System.out.println(sequence + "," + remaining + "," + parts);
        List<String> results = new ArrayList<>();
        if (!remaining.isEmpty()) {
            if (parts == 4) {
                return results;
            }
            if (part.isEmpty() && remaining.charAt(0) == '0') {
                return results;
            }
            String expand = part + remaining.charAt(0);
            if (Integer.valueOf(expand) > 255) {
                return results;
            }
            results.addAll(traverse(sequence, expand, remaining.substring(1, remaining.length()), parts));
            results.addAll(traverse(sequence.isEmpty() ? expand : sequence + "." + expand, "", remaining.substring(1, remaining.length()), parts + 1));
            return results;
        } else {
            if (parts == 4) {
                results.add(sequence);
                return results;
            }

        }
        return results;

    }


    // public void traverse(char[] chars, int i, Stack<Character> soFar, int partsLeft) {
    //     if (isCompleteSequence(chars, i, partsLeft) || isInvalidSequence(chars, i, soFar)) {
    //         return;
    //     }
    //     int partLength = 0;
    //     String partNum = "";
    //     for (int j = i; j < chars.length; j++) {
    //         if (isIncompleteSequence(partsLeft, partLength)) {
    //             return;
    //         }
    //         partNum += chars[j];
    //         soFar.push(chars[j]);
    //         partLength++;
    //         if (partsLeft > 1 && Integer.valueOf(partNum) <= 255) {
    //             soFar.push('.');
    //             traverse(chars, j + 1, copyOf(soFar), partsLeft - 1);
    //             soFar.pop();
    //         }
    //     }
    //     if (toStr(soFar).equals("25.525.511.")) {
    //         System.out.println("hi");
    //     }
    //     results.add(toStr(soFar));
    //     return;
    // }

    // private boolean isCompleteSequence(char[] chars, int i, int partsLeft) {
    //     return i == chars.length && partsLeft == 0;
    // }

    // private boolean isInvalidSequence(char[] chars, int i, Stack<Character> soFar) {
    //     return !soFar.isEmpty() && i < chars.length && soFar.peek() == '.' && (chars[i] == '0');
    // }

    // private boolean isIncompleteSequence(int partsLeft, int partLength) {
    //     return partLength == 3 && partsLeft > 0;
    // }

    // private Stack<Character> copyOf(Stack<Character> soFar) {
    //     Stack<Character> newStack = new Stack<Character>();
    //     newStack.addAll(soFar);
    //     return newStack;
    // }

    // private String toStr(Stack<Character> soFar) {
    //     Character[] arr = new Character[soFar.size()];
    //     arr = soFar.toArray(arr);
    //     StringBuilder builder = new StringBuilder();
    //     for (int i = 0; i < arr.length; i++) {
    //         builder.append(arr[i]);
    //     }
    //     return builder.toString();
    // }
}
