package dp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GridPaintColor {

    private Map<Integer, Map<String, Long>> cache = new HashMap<>();

    private Set<String> combinations = new HashSet<>();

    public long calculate(int m, int n) {
        long sum = 0;
        for (int i = m - 1; i >= 0; i--) {
            sum += recurse(i, m, 0, n, "", -1);
            if (i < m - 3) {
                cache.remove(i + 2);
            }
        }
        return sum;
    }

    private long recurse(int row, int ROW_LENGTH, int col, int COL_WIDTH, String combination, int exclude) {
        if (col >= COL_WIDTH) {
            if (row == ROW_LENGTH - 1) {
                combinations.add(combination);
                return 0;
            } else if (row == ROW_LENGTH - 2) {
                long sum = checkCombination(combination);
                cache.putIfAbsent(row, new HashMap<>());
                cache.get(row).put(combination, sum);
                return sum;
            } else {
                long sum = checkCombinationFromCache(combination, row + 1);
                cache.putIfAbsent(row, new HashMap<>());
                cache.get(row).put(combination, sum);
                return sum;

            }
        }
        List<Integer> options = IntStream.range(0, 3).boxed().collect(Collectors.toList());
        if (exclude != -1) {
            options.remove(exclude);
        }
        long sum = 0;
        for (Integer color : options) {
            sum += recurse(row, ROW_LENGTH, col + 1, COL_WIDTH, combination + color, color);
        }
        return sum;
    }

    private long checkCombinationFromCache(String combination, int key) {
        List<String> validCombinations = getValidCombinations(combination);
        long sum = 0;
        for (String valid : validCombinations) {
            sum += cache.get(key).get(valid);
        }

        return sum;
    }

    private int checkCombination(String inCombination) {
        List<String> validCombinations = getValidCombinations(inCombination);

        return validCombinations.size();
    }

    private List<String> getValidCombinations(String inCombination) {
        List<String> validCombinations = new ArrayList<>();
        for (String validCombination : combinations) {
            boolean isValid = true;
            for (int i = 0; i < validCombination.length(); i++) {
                if (inCombination.charAt(i) == validCombination.charAt(i)) {
                    isValid = false;
                    break;
                }
                if (i < inCombination.length() - 1 && inCombination.charAt(i) == inCombination.charAt(i + 1)) {
                    isValid = false;
                    break;
                }

            }
            if (isValid) {
                validCombinations.add(validCombination);
            }

        }
        return validCombinations;
    }

    public static void main(String[] args) {
        GridPaintColor gridPaintColor = new GridPaintColor();
        System.out.println(gridPaintColor.calculate(14, 4));
    }
}
