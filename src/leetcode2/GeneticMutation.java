package leetcode2;

import java.util.HashSet;
import java.util.Set;

public class GeneticMutation {

    private char[] choices = new char[]{'A', 'C', 'G', 'T'};

    private int noOfMutations = Integer.MAX_VALUE;

    public static void main(String[] args) {
        GeneticMutation geneticMutation = new GeneticMutation();
        System.out.println(geneticMutation.minMutation("AACCGGTT", "AAACGGTA",
            new String[]{"AACCGGTA", "AACCGCTA", "AAACGGTA"}));
        System.out.println(Character.forDigit(1, 10));
    }

    public int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> validMutations = new HashSet<>();
        for (String mutation : bank) {
            validMutations.add(mutation);
        }
        mutate(startGene, endGene, 0, validMutations, 0);
        return noOfMutations;
    }

    private void mutate(String startGene, String endGene, int idx, Set<String> validMutations,
        int mutationsCount) {
        System.out.println("startGene=" + startGene);
        if (startGene.equals(endGene)) {
            System.out.println("equals=" + startGene);
            noOfMutations = Math.min(noOfMutations, mutationsCount);
            return;
        }
        if (idx == endGene.length()) {
            return;
        }
        mutate(startGene, endGene, idx + 1, validMutations, mutationsCount);
        if (idx == 2) {
            System.out.println("");
        }
        for (int i = 0; i < choices.length; i++) {
            if (choices[i] == startGene.charAt(idx)) {
                continue;
            }
            String mutated =
                startGene.substring(0, idx) + choices[i] + startGene.substring(idx + 1);
            if (validMutations.contains(mutated)) {
                mutate(mutated, endGene, idx + 1, validMutations, mutationsCount + 1);
            }
        }
    }
}