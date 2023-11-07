package leetcode2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordDictionary {

    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("at");
        wordDictionary.addWord("and");
        wordDictionary.addWord("an");
        wordDictionary.addWord("add");
        wordDictionary.addWord("a");
        wordDictionary.addWord("bat");

//        wordDictionary.addWord("bad");
//        wordDictionary.addWord("dad");
//        wordDictionary.addWord("mad");
        System.out.println(wordDictionary.search("at"));
        System.out.println(wordDictionary.search(".at"));
//        System.out.println(wordDictionary.search("pad"));
//        System.out.println(wordDictionary.search("bad"));
//        System.out.println(wordDictionary.search(".ad"));
//        System.out.println(wordDictionary.search("b.."));


    }

    private Dict dict;

    public WordDictionary() {
        dict = new Dict();
    }

    public void addWord(String word) {
        dict.add(word.toCharArray(), 0);
    }

    public boolean search(String word) {
        return dict.isExists(word.toCharArray(), 0);
    }

    private class Dict {

        private Set<String> words = new HashSet<>();

        private Map<Character, Dict> recursiveMap = new HashMap<>();

        private void add(char[] wordArr, int pos) {
            if (pos >= wordArr.length) {
                words.add(String.valueOf(wordArr));
                return;
            }
            recursiveMap.putIfAbsent(wordArr[pos], new Dict());
            recursiveMap.get(wordArr[pos]).add(wordArr, pos + 1);
        }

        private boolean isExists(char[] wordArr, int pos) {
            if (words.contains(String.valueOf(wordArr))) {
                return true;
            }
            if (pos >= wordArr.length || (wordArr[pos] != '.' && !recursiveMap.containsKey(wordArr[pos]))) {
                return false;
            }
            if (wordArr[pos] == '.') {
                boolean isExists = false;
                char[] newWordArr = String.valueOf(wordArr).toCharArray();
                for (Character c : recursiveMap.keySet()) {
                    newWordArr[pos] = c;
                    isExists = isExists || recursiveMap.get(c).isExists(newWordArr, pos + 1);
                    if (isExists) {
                        return true;
                    }

                }
            } else {
                return recursiveMap.get(wordArr[pos]).isExists(wordArr, pos + 1);
            }
            return false;
        }
    }
}
