package leetcode;

import java.util.*;

public class LongestStringChainEfficient {


    public int longestStrChain(String[] words) {

        int counter = 0;
        List<String> sortedList = Arrays.asList(words);
        Collections.sort(sortedList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        int k = 0;
        String[] sorted = sortedList.toArray(new String[0]);
        int[] dp = new int[sorted.length];

        Map<String, Integer> longestChain = new HashMap<>();
        int max = 1;
        Arrays.fill(dp, 1);
        while (k < sorted.length) {
            int ind1 = k;
            int ind2 = k;
            int width = sorted[k].length();
            while (ind2 < sorted.length && sorted[ind2].length() == width) {
                ind2++;
                continue;
            }
            if (ind2 >= sorted.length) {
                break;
            }
            if (sorted[ind2].length() - width == 1) {
                for (int i = ind1; i < ind2; i++) {
                    for (int j = ind2; j < sorted.length; j++) {
                        if (sorted[j].length() - width > 1) {
                            break;
                        }
                        if (isPredecessor(sorted[j], sorted[i])
                                && dp[j] < (dp[i] + 1)) {
                            counter++;
                            dp[j] = dp[i] + 1;
                        }
                    }
                }
                k = ind2;

            } else {
                //sequence broken
                ind1 = ind2;
                k = ind1;
            }

        }
        System.out.println("no of words=" + words.length + ", counter=" + counter);
        return Arrays.stream(dp).max().getAsInt();
    }


    private boolean isPredecessor1(String s1, String s2) {
        int mismatchCount = 0;
        for (int i = 0; i < s2.length(); i++) {
            StringBuilder tempStr = new StringBuilder(s2);
            char c = s2.charAt(i);
            tempStr.deleteCharAt(i);
            if (s1.equalsIgnoreCase(tempStr.toString())) {
                return true;
            }
        }

        return false;
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
        LongestStringChainEfficient chain = new LongestStringChainEfficient();
//        int length = chain.longestStrChain(new String[]{"klmnowx", "abcdefgiklmno", "fgij", "bcfghijklmno", "fgjpqrst", "uy", "abceklmnouvw", "pqrstuwy", "fghijlno", "mnouvwxy", "klmnopqt", "klmnopqrstuy", "aeuvw", "muvw", "abcdeklmnow", "fhijpqrst", "mpqrst", "klmnoprt", "fghijklno", "abcdelmo", "klnuvwxy", "klmnopst", "abcdeklmnov", "fghj", "luvwxy", "ghklmnopqrst", "pqrstwx", "abcdklmno", "cdefghij", "pqrs", "efghijklmno", "fghjklmno", "adeklmno", "rs", "kuvwxy", "ghij", "befghijklmno", "ln", "hijklmnopqrst", "ghpqrst", "fgiklmnopqrst", "pqrtuvwxy", "pqrsty", "jklmnopqrst", "lnouvwxy", "klmnoqsuvwxy", "abcdeghklmno", "fi", "fghijlnpqrst", "abdklmnouvw", "uwx", "abcdekln", "klmno", "abcdekn", "abcdemuvw", "pqs", "fghijpqt", "klmnopqrstuw", "n", "nopqrstuvwxy", "abcdefghj", "fghiklmnopqrst", "klmnorst", "abcdemnouvw", "fgh", "pqt", "abfghij", "o", "nouvw", "abcdklmnouvw", "abeklmno", "abcden", "klmnopqrstwxy", "q", "fghijklmnoprt", "klmnovx", "abceuvw", "klmnopsuvwxy", "hj", "abcdefgh", "fhjklmno", "klmnoquvwxy", "wxy", "klmnopqrstuvwy", "kln", "abcdegklmno", "mno", "gklmno", "klnouvw", "fghijklmnoqr", "fghijpqrst", "mnuvwxy", "ghipqrst", "klmnoqrtuvwxy", "acdfghij", "uwy", "fghjklmnopqrst", "mnpqrstuvwxy", "abcdeknouvw"});
//        System.out.println(length);
        int length = chain.longestStrChain(new String[]{"ksqvsyq", "ks", "kss", "czvh", "zczpzvdhx", "zczpzvh", "zczpzvhx", "zcpzvh", "zczvh", "gr", "grukmj", "ksqvsq", "gruj", "kssq", "ksqsq", "grukkmj", "grukj", "zczpzfvdhx", "gru"});
        System.out.println(length);
        //        int length = chain.longestStrChain(new String[]{"xbc", "pcxbcf", "xb", "cxbc", "pcxbc"});

//        int length1 = chain.longestStrChain(new String[]{"a", "b", "ba", "bca", "bda", "bdca"});
//        long start = System.currentTimeMillis();
//        long end = System.currentTimeMillis();
//        System.out.println("time=" + Long.toString(Long.valueOf(end) - Long.valueOf(start)));
//        //System.out.println(length1);
//        System.out.println(length2);
    }
}
