package leetcode2;

import java.util.*;

class TrieProblem {

    public static void main(String[] args) {
        TrieProblem problem = new TrieProblem();
        List<String> dict = Arrays.asList(new String[]{"cat", "bat", "rat"});
        problem.replaceWords(dict, "the cattle was rattled by the battery");
    }

    public String replaceWords(List<String> dictionary, String in) {
        String sentence = new String(in);
        TrieNode trie = new TrieNode();
        for (String word : dictionary) {
            trie.add(word.toCharArray(), 0, word);
        }
        Map<Integer, String> partMap = new HashMap<>();
        int count = 0;
        for (String part : Arrays.asList(sentence.split(" "))) {
            partMap.put(count++, part);
        }
        count = 0;
        for (Map.Entry<Integer, String> part : partMap.entrySet()) {
            String replacement = trie.getReplacement(part.getValue().toCharArray(), 0);
            if (replacement != null) {
                partMap.put(count, replacement);
            }
            count++;
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, String> part : partMap.entrySet()) {
            builder.append(part.getValue()).append(" ");
        }
        System.out.println(builder.toString());
        return builder.toString().trim();
    }


    public class TrieNode {
        private Set<String> words = new HashSet<String>();
        private Map<Character, TrieNode> children = new HashMap<>();


        public void add(char[] charArr, int pos, String inWord) {
            if (pos == charArr.length) {
                words.add(inWord);
                return;
            }
            children.putIfAbsent(charArr[pos], new TrieNode());
            children.get(charArr[pos]).add(charArr, pos + 1, inWord);
        }

        public String getReplacement(char[] part, int pos) {
            if (!children.containsKey(part[pos])) {
                return words.isEmpty() ? null : words.stream().sorted().findFirst().get();
            }
            String prefixStr = null;
            for (String replacement : words) {
                if (isLonger(prefixStr, replacement)) {
                    prefixStr = replacement;
                }
            }
            String replacement = children.get(part[pos]).getReplacement(part, pos + 1);
            if (isLonger(prefixStr, replacement)) {
                prefixStr = replacement;
            }

            return prefixStr;
        }


        private boolean isLonger(String prefixStr, String replacement) {
            return prefixStr == null || (prefixStr != null && replacement != null && prefixStr.length() > replacement.length());
        }

        public String toString() {
            return gatherWords().toString();
        }

        private Set<String> gatherWords() {
            Set<String> results = new HashSet<>();
            for (TrieNode child : this.children.values()) {
                results.addAll(child.words);
                results.addAll(child.gatherWords());
            }

            return results;

        }
    }
}