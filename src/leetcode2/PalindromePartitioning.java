package leetcode2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PalindromePartitioning {

    public static void main(String[] args) {
        PalindromePartitioning palindromePartitioning = new PalindromePartitioning();
        System.out.println(palindromePartitioning.partition("cococ"));
    }

    public List<List<String>> partition(String s) {
        ArrayDeque<Character> queue = new ArrayDeque<>();
        queue.addAll(s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        return getPalindromes(queue, "", new ArrayList<>()).stream().filter(str -> str.stream()
                .mapToInt(i -> i.length()).sum() >= s.length()).collect(Collectors.toList());
    }

    private List<List<String>> getPalindromes(ArrayDeque<Character> queue, String inPalin, List<String> palindromesSoFar) {
        List<List<String>> result = new ArrayList<>();
        if (queue.isEmpty()) {
            result.add(palindromesSoFar);
            return result;
        }
        Character head = queue.pollFirst();
        List<String> palindromes = new ArrayList<>(palindromesSoFar);
        boolean isPalin = isPalindrome(inPalin + head);
        System.out.println((inPalin + head) + "=" + isPalin);
        if (isPalin) {
            palindromes.add(inPalin + head);
            result.addAll(getPalindromes(queue, "", palindromes));
        }
        result.addAll(getPalindromes(queue, inPalin + head, palindromesSoFar));
        queue.offerFirst(head);
        return result;
    }

    private boolean isPalindrome(String palin) {
        char[] charArr = palin.toCharArray();
        for (int i = 0; i < charArr.length / 2; i++) {
            if (charArr[i] != charArr[charArr.length - i - 1]) {
                return false;
            }
        }
        return true;
    }
}