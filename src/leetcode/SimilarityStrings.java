package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class SimilarityStrings {

    public boolean areSentencesSimilarTwo(String[] sentence1, String[] sentence2, List<List<String>> similarPairs) {
        if (sentence1.length != sentence2.length) {
            return false;
        }
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        for (List<String> pairs : similarPairs) {
            String lt = pairs.get(0);
            String rt = pairs.get(1);
            addToGraph(lt, rt, graph, new HashSet<String>());
            addToGraph(rt, lt, graph, new HashSet<String>());
        }
        for (int i = 0; i < sentence1.length; i++) {
            Set<String> similarWords1 = graph.get(sentence1[i]);
            Set<String> similarWords2 = graph.get(sentence2[i]);
            if (similarWords1.contains(sentence2[i]) || similarWords2.contains(sentence1[i])) {
                continue;
            }
            similarWords1.retainAll(similarWords2);
            if (similarWords1.size() == 0) {
                return false;
            }

        }

        return true;
    }

    private void addToGraph(String word1, String word2, Map<String, Set<String>> graph, Set<String> visited) {

        visited.add(word2);
        graph.putIfAbsent(word1, new HashSet<String>());
        graph.get(word1).add(word2);

        if (graph.get(word2) == null) {
            return;
        }
        for (String similar : graph.get(word2)) {
            if (word1.equalsIgnoreCase(similar) || graph.get(word1).contains(similar) || visited.contains(similar)) {
                continue;
            }
            addToGraph(word1, similar, graph, visited);
            addToGraph(similar, word1, graph, visited);

        }

    }

    public static void main(String[] args) {
        SimilarityStrings similarityStrings = new SimilarityStrings();
        String[] sentence1 = {"great", "acting", "skills"};
        String[] sentence2 = {"fine", "drama", "talent"};
        String[][] similarPairs = new String[][]{{"great", "good"}, {"fine", "good"}, {"drama", "acting"}, {"skills", "talent"}};
        List<List<String>> similarPairsList = Arrays.stream(similarPairs).collect(Collectors.toList()).stream().map(a -> Arrays.asList(a)).collect(Collectors.toList());
        System.out.println(similarityStrings.areSentencesSimilarTwo(sentence1, sentence2, similarPairsList));
    }
}
