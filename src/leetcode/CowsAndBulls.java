package leetcode;

import java.util.*;

import static java.lang.Math.min;

public class CowsAndBulls {

    public String getHint(String secret, String guess) {

        Map<Character, List<Integer>> charToLocMapOrig = new HashMap<Character, List<Integer>>();
        Map<Character, List<Integer>> charToLocMapGuess = new HashMap<Character, List<Integer>>();
        createCharIndex(secret, charToLocMapOrig);

        int noOfAs = 0;
        int noOfBs = 0;
        createCharIndex(guess, charToLocMapGuess);

        for (Character ch : charToLocMapGuess.keySet()) {
            if (charToLocMapOrig.containsKey(ch)) {
                List<Integer> origLocations = charToLocMapOrig.get(ch);
                List<Integer> guessLocations = charToLocMapGuess.get(ch);
                List<Integer> intersect = new ArrayList<Integer>();
                intersect.addAll(origLocations);
                intersect.retainAll(guessLocations);
                noOfAs += intersect.size();
                noOfBs += min(guessLocations.size() - intersect.size(), origLocations.size() - intersect.size());
            }
        }

        return noOfAs + "A" + noOfBs + "B";


    }

    private void createCharIndex(String secret, Map<Character, List<Integer>> charToLocMapOrig) {
        int loc = 0;
        for (char c : secret.toCharArray()) {
            Character ch = new Character(c);
            charToLocMapOrig.putIfAbsent(ch, new LinkedList<Integer>());
            charToLocMapOrig.get(ch).add(loc++);
        }
    }

    public static void main(String[] args) {
        CowsAndBulls cowsAndBulls = new CowsAndBulls();
        System.out.println(cowsAndBulls.getHint("1807", "7810"));
    }
}
