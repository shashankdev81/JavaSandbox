package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class SimilarityStringsGraph {

    private Map<String, Set<String>> wordToSimilarWordsMap;

    private class Word {
        private String wordStr;
        private List<Word> adjs;


        public Set<String> getSimilarWords() {
            return getAdjs(new HashSet<String>());
        }

        private Set<String> getAdjs(Set<String> visited) {
            Set<String> words = new HashSet<>();
            words.add(wordStr);
            visited.add(wordStr);
            for (Word word : adjs) {
                if (!visited.contains(word.wordStr)) {
                    words.addAll(word.getAdjs(visited));
                }
            }

            return words;
        }

        public Word(String word) {
            this.wordStr = word;
            adjs = new ArrayList<Word>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Word node = (Word) o;
            return wordStr.equals(node.wordStr);
        }

        @Override
        public int hashCode() {
            return Objects.hash(wordStr);
        }
    }

    private Map<String, Word> strToNodeMap;

    public boolean areSentencesSimilarTwo(String[] sentence1, String[] sentence2, List<List<String>> similarPairs) {
        if (sentence1.length != sentence2.length) {
            return false;
        }
        strToNodeMap = new HashMap<String, Word>();
        wordToSimilarWordsMap = new HashMap<String, Set<String>>();
        for (List<String> pairs : similarPairs) {
            Word left = new Word(pairs.get(0));
            Word right = new Word(pairs.get(1));
            strToNodeMap.putIfAbsent(pairs.get(0), left);
            strToNodeMap.putIfAbsent(pairs.get(1), right);
            strToNodeMap.get(pairs.get(0)).adjs.add(strToNodeMap.get(pairs.get(1)));
            strToNodeMap.get(pairs.get(1)).adjs.add(strToNodeMap.get(pairs.get(0)));
        }


        for (List<String> pairs : similarPairs) {
            Set<String> similarWords = strToNodeMap.get(pairs.get(0)).getSimilarWords();
            similarWords.addAll(strToNodeMap.get(pairs.get(1)).getSimilarWords());
            for (String word : similarWords) {
                wordToSimilarWordsMap.putIfAbsent(word, similarWords);
            }
        }

        for (int i = 0; i < sentence1.length; i++) {
            if (sentence1[i].equalsIgnoreCase(sentence2[i])) {
                continue;
            }
            Set<String> simiList1 = wordToSimilarWordsMap.get(sentence1[i]);
            Set<String> simiList2 = wordToSimilarWordsMap.get(sentence2[i]);
            if (simiList1 == null || simiList1.isEmpty() || simiList2 == null || simiList2.isEmpty()) {
                return false;
            }
            if (!simiList1.stream().anyMatch(w -> simiList2.contains(w))) {
                return false;
            }
        }

        return true;
    }


    public static void main(String[] args) {
        SimilarityStringsGraph similarityStrings = new SimilarityStringsGraph();
        String[] sentence1 = {"I", "love", "leetcode"};
        String[] sentence2 = {"I", "love", "onepiece"};
        String[][] similarPairs = new String[][]{{"manga", "hunterXhunter"}, {"platform", "anime"}, {"leetcode", "platform"}, {"anime", "manga"}};
        List<List<String>> similarPairsList = Arrays.stream(similarPairs).collect(Collectors.toList()).stream().map(a -> Arrays.asList(a)).collect(Collectors.toList());
        System.out.println(similarityStrings.areSentencesSimilarTwo(sentence1, sentence2, similarPairsList));
    }
}
