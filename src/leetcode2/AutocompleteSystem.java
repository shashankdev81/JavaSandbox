package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class AutocompleteSystem {

    public static void main(String[] args) {
        AutocompleteSystem autocompleteSystem = new AutocompleteSystem(
            new String[]{"i love you", "island", "iroman", "i love leetcode"},
            new int[]{5, 3, 2, 2});
        System.out.println(autocompleteSystem.input('i'));
        System.out.println(autocompleteSystem.input(' '));
        System.out.println(autocompleteSystem.input('a'));
        System.out.println(autocompleteSystem.input('#'));

        System.out.println(autocompleteSystem.input('i'));
        System.out.println(autocompleteSystem.input(' '));
        System.out.println(autocompleteSystem.input('a'));
        System.out.println(autocompleteSystem.input('#'));

        System.out.println(autocompleteSystem.input('i'));
        System.out.println(autocompleteSystem.input(' '));
        System.out.println(autocompleteSystem.input('a'));
        System.out.println(autocompleteSystem.input('#'));


    }

    private Map<String, Set<ScoredMatch>> suggestionsMap;

    private StringBuilder typed;


    public AutocompleteSystem(String[] sentences, int[] times) {
        typed = new StringBuilder();
        suggestionsMap = new HashMap<>();
        index(sentences, times, false);
    }

    private void index(String[] sentences, int[] times, boolean reIndex) {
        for (int sentIdx = 0; sentIdx < sentences.length; sentIdx++) {
            final String sentence = sentences[sentIdx];
            for (int charIdx = 0; charIdx <= sentence.length(); charIdx++) {
                suggestionsMap.putIfAbsent(sentence.substring(0, charIdx),
                    new TreeSet<>((s1, s2) -> {
                        if (s1.times == s2.times) {
                            return s1.match.compareTo(s2.match);
                        } else {
                            return s2.times - s1.times;
                        }
                    }));
                if (!reIndex) {
                    suggestionsMap.get(sentence.substring(0, charIdx))
                        .add(new ScoredMatch(sentence, times[sentIdx]));
                } else {
                    Set<ScoredMatch> allMatches = suggestionsMap.get(
                        sentence.substring(0, charIdx));
                    List<ScoredMatch> thisSentenceMatches = allMatches.stream()
                        .filter(m -> m.match.equals(sentence))
                        .collect(Collectors.toList());
                    allMatches.removeAll(thisSentenceMatches);
                    for (ScoredMatch scoredMatch : thisSentenceMatches) {
                        scoredMatch.times++;
                    }
                    allMatches.addAll(thisSentenceMatches);
                }
            }
        }
    }

    public List<String> input(char c) {
        List<String> results = new ArrayList<>();
        if (c == '#') {
            index(new String[]{typed.toString()}, new int[]{1},
                suggestionsMap.containsKey(typed.toString()));
            typed = new StringBuilder();
            return results;
        }
        typed.append(c);
        Set<ScoredMatch> matches = suggestionsMap.get(typed.toString());
        if (matches == null || matches.isEmpty()) {
            return results;
        }
        return matches.stream().limit(3).map(s -> s.match).collect(Collectors.toList());
    }

    public class ScoredMatch {

        private String match;

        private int times;

        public ScoredMatch(String match, int times) {
            this.match = match;
            this.times = times;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ScoredMatch that = (ScoredMatch) o;
            return times == that.times && Objects.equals(match, that.match);
        }

        @Override
        public int hashCode() {
            return Objects.hash(match, times);
        }
    }


}
