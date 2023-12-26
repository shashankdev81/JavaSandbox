package leetcode2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MagicDictionary2 {


    List<String> sortedWords;

    public MagicDictionary2() {
        sortedWords = new ArrayList<>();
    }

    public static void main(String[] args) {
        MagicDictionary2 magicDictionary = new MagicDictionary2();
        magicDictionary.buildDict(new String[]{"a", "b", "ab"});
        System.out.println(magicDictionary.search("a"));
        System.out.println(magicDictionary.search("b"));
        System.out.println(magicDictionary.search("c"));
        System.out.println(magicDictionary.search("d"));
        System.out.println(magicDictionary.search("e"));
        System.out.println(magicDictionary.search("f"));
        System.out.println(magicDictionary.search("ab"));
        System.out.println(magicDictionary.search("ba"));
        System.out.println(magicDictionary.search("abc"));

    }

    public void buildDict(String[] dictionary) {
        for (String word : dictionary) {
            sortedWords.add(word);
        }
        Collections.sort(sortedWords);
    }

    public boolean search(String searchWord) {
        int idx = Collections.binarySearch(sortedWords, searchWord);
        if (idx < 0) {
            idx = Math.abs(idx) - 1;
        }
        boolean isMatch0 =
            idx < sortedWords.size() && diffEqualsOne(sortedWords.get(idx), searchWord);
        boolean isMatch1 = idx > 0 && diffEqualsOne(sortedWords.get(idx - 1), searchWord);
        boolean isMatch2 =
            idx < sortedWords.size() - 1 && diffEqualsOne(sortedWords.get(idx + 1), searchWord);
        System.out.println(searchWord + "," + isMatch0 + "," + isMatch1 + "," + isMatch2);
        return isMatch0 || isMatch1 || isMatch2;
    }

    private boolean diffEqualsOne(String left, String right) {
        if (left.length() != right.length()) {
            return false;
        }
        int diff = 0;
        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                diff++;
            }
            if (diff == 2) {
                return false;
            }
        }
        System.out.println(left + "," + right + "," + diff);
        return diff == 1;
    }
}

/**
 * Your MagicDictionary object will be instantiated and called as such: MagicDictionary obj = new
 * MagicDictionary(); obj.buildDict(dictionary); boolean param_2 = obj.search(searchWord);
 */