package leetcode2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CombinationIterator {


    private List<String> allCombinations;

    private int i;

    public static void main(String[] args) {
        CombinationIterator combinationIterator = new CombinationIterator("abc", 2);
    }

    public CombinationIterator(String characters, int combinationLength) {
        allCombinations = combinations(characters, 0, combinationLength, new ArrayList<>());
        allCombinations = allCombinations.stream().sorted()
            .filter(s -> s.length() == combinationLength).collect(Collectors.toList());
        System.out.println(allCombinations);
    }

    private List<String> combinations(String chars, int idx, int cl, List<String> combinations) {
        List<String> combs = new ArrayList<>();
        if (idx == chars.length()) {
            return combinations;
        }
        for (String comb : combinations) {
            if (comb.length() + 1 <= cl) {
                combs.add(comb + chars.charAt(idx));
            }
        }
        combs.addAll(combinations);
        if (chars.length() - idx >= cl) {
            combs.add("" + chars.charAt(idx));
        }
        return combinations(chars, idx + 1, cl, combs);

    }

    public String next() {
        return allCombinations.get(i++);
    }

    public boolean hasNext() {
        return i < allCombinations.size();
    }
}

/**
 * Your CombinationIterator object will be instantiated and called as such: CombinationIterator obj
 * = new CombinationIterator(characters, combinationLength); String param_1 = obj.next(); boolean
 * param_2 = obj.hasNext();
 */