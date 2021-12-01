package leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class LongestStringChainGraph {


    private class Word {

        private int maxPathLength = -1;
        private String value;

        private List<Word> predecessors;

        public Word(String v) {
            this.value = v;
            predecessors = new LinkedList<Word>();
        }

        public boolean equals(Object o) {
            return value.equalsIgnoreCase(o.toString());
        }

        public int hashcode() {
            return Objects.hashCode(value);
        }

        public int getMaxPathLength() {
            if (maxPathLength != -1) {
                return maxPathLength;
            }
            if (predecessors.isEmpty()) {
                return 1;
            }
            for (Word pred : predecessors) {
                maxPathLength = Math.max(maxPathLength, pred.getMaxPathLength() + 1);
            }
            return maxPathLength;
        }
    }

    private class ArrayOfWords {
        private List<Word> words;

        public ArrayOfWords() {
            this.words = new ArrayList<>();
        }
    }

    public int longestStrChain(String[] words) {
        Map<String, Word> wordsFactory = new HashMap<String, Word>();
        int maxWidth = Collections.max(Arrays.stream(words).map(str -> str.length()).collect(Collectors.toList()));
        ArrayOfWords[] widthToWords = new ArrayOfWords[maxWidth];

        for (String str : words) {
            Word word = new Word(str);
            wordsFactory.putIfAbsent(str, word);
            if (widthToWords[str.length() - 1] == null) {
                widthToWords[str.length() - 1] = new ArrayOfWords();
            }
            widthToWords[str.length() - 1].words.add(word);
        }

        int ind1 = 0;
        int ind2 = 0;
        while (ind1 < widthToWords.length && ind2 < widthToWords.length) {
            while (widthToWords[ind1] == null) {
                ind1++;
            }
            ind2 = ind1 + 1;
            if (ind2 >= widthToWords.length) {
                break;
            }
            while (widthToWords[ind2] == null) {
                ind2++;
            }

            for (Word s1 : widthToWords[ind1].words) {
                for (Word s2 : widthToWords[ind2].words) {

                    if (isPredecessor(s2.value, s1.value)) {
                        wordsFactory.get(s1.value).predecessors.add(wordsFactory.get(s2.value));
                    }
                }
            }
            ind1 = ind2;
            ind2 = ind1;

        }
        int pathLength = Integer.MIN_VALUE;
        for (Word word : wordsFactory.values()) {
            pathLength = Math.max(pathLength, word.getMaxPathLength());
        }
        return pathLength;
    }

    private boolean isPredecessor(String s1, String s2) {
        if (s1.length() - s2.length() != 1) {
            return false;
        }
        int mismatchCount = 0;
        char[] s1Arr = s1.toCharArray();
        char[] s2Arr = s2.toCharArray();
        int i = 0;
        int j = 0;
        while (i < s1.length()) {
            if (j == s2.length() || s1Arr[i] != s2Arr[j]) {
                mismatchCount++;
                i++;
            } else {
                i++;
                j++;
            }

        }

        return mismatchCount == 1;
    }

    public static void main(String[] args) {
        LongestStringChainGraph chain = new LongestStringChainGraph();
        int length1 = chain.longestStrChain(new String[]{"a", "b", "ba", "bca", "bda", "bdca"});
        long start = System.currentTimeMillis();
        int length2 = chain.longestStrChain(new String[]{"klmnowx", "abcdefgiklmno", "fgij", "bcfghijklmno", "fgjpqrst", "uy", "abceklmnouvw", "pqrstuwy", "fghijlno", "mnouvwxy", "klmnopqt", "klmnopqrstuy", "aeuvw", "muvw", "abcdeklmnow", "fhijpqrst", "mpqrst", "klmnoprt", "fghijklno", "abcdelmo", "klnuvwxy", "klmnopst", "abcdeklmnov", "fghj", "luvwxy", "ghklmnopqrst", "pqrstwx", "abcdklmno", "cdefghij", "pqrs", "efghijklmno", "fghjklmno", "adeklmno", "rs", "kuvwxy", "ghij", "befghijklmno", "ln", "hijklmnopqrst", "ghpqrst", "fgiklmnopqrst", "pqrtuvwxy", "pqrsty", "jklmnopqrst", "lnouvwxy", "klmnoqsuvwxy", "abcdeghklmno", "fi", "fghijlnpqrst", "abdklmnouvw", "uwx", "abcdekln", "klmno", "abcdekn", "abcdemuvw", "pqs", "fghijpqt", "klmnopqrstuw", "n", "nopqrstuvwxy", "abcdefghj", "fghiklmnopqrst", "klmnorst", "abcdemnouvw", "fgh", "pqt", "abfghij", "o", "nouvw", "abcdklmnouvw", "abeklmno", "abcden", "klmnopqrstwxy", "q", "fghijklmnoprt", "klmnovx", "abceuvw", "klmnopsuvwxy", "hj", "abcdefgh", "fhjklmno", "klmnoquvwxy", "wxy", "klmnopqrstuvwy", "kln", "abcdegklmno", "mno", "gklmno", "klnouvw", "fghijklmnoqr", "fghijpqrst", "mnuvwxy", "ghipqrst", "klmnoqrtuvwxy", "acdfghij", "uwy", "fghjklmnopqrst", "mnpqrstuvwxy", "abcdeknouvw"});
        long end = System.currentTimeMillis();
        System.out.println("time=" + Long.toString(Long.valueOf(end) - Long.valueOf(start)));
        //System.out.println(length1);
        System.out.println(length2);
    }
}
