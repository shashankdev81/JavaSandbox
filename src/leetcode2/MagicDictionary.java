package leetcode2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicDictionary {




    public static void main(String[] args) {
        MagicDictionary magicDictionary = new MagicDictionary();
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

    Map<Character, MagicDictionary> dictionaryMap;

    public MagicDictionary() {
        dictionaryMap = new HashMap<>();
    }
    public void buildDict(String[] dictionary) {
        int idx = 0;
        for (String word : dictionary) {
            dictionaryMap.putIfAbsent(word.charAt(idx), new MagicDictionary());
            dictionaryMap.get(word.charAt(idx)).add(word, idx + 1);
        }
    }

    private void add(String word, int idx) {
        if (idx == word.length()) {
            return;
        }
        dictionaryMap.putIfAbsent(word.charAt(idx), new MagicDictionary());
        dictionaryMap.get(word.charAt(idx)).add(word, idx + 1);
    }

    public boolean search(String searchWord) {
        return search(searchWord, 0, false);
    }

    public boolean search(String searchWord, int idx, boolean isAltered) {
        boolean isFound = false;
        if (idx >= searchWord.length()) {
            return isAltered;
        }
        if (dictionaryMap.containsKey(searchWord.charAt(idx))) {
            isFound = isFound || dictionaryMap.get(searchWord.charAt(idx))
                .search(searchWord, idx + 1, false);
        }
        if (!isAltered) {
            for (Map.Entry<Character, MagicDictionary> entry : dictionaryMap.entrySet()) {
                if (entry.getKey() == searchWord.charAt(idx)) {
                    continue;
                }
                isFound = isFound || entry.getValue().search(searchWord, idx + 1, true);
            }
        }
        return isFound;
    }

}

/**
 * Your MagicDictionary object will be instantiated and called as such: MagicDictionary obj = new
 * MagicDictionary(); obj.buildDict(dictionary); boolean param_2 = obj.search(searchWord);
 */