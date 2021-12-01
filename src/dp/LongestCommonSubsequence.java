package dp;

public class LongestCommonSubsequence {

    private String word1;

    private String word2;

    public LongestCommonSubsequence(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
    }

    public int getLCS() {

        int[][] matrix = new int[word2.length() + 1][word1.length() + 1];

        for (int i = 0, j = 0; j <= word1.length(); j++) {
            matrix[i][j] = 0;
        }
        for (int i = 0, j = 0; i <= word2.length(); i++) {
            matrix[i][j] = 0;
        }
        print(matrix);

        for (int i = 1; i <= word2.length(); i++) {
            for (int j = 1; j <= word1.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    matrix[i][j] = 1 + matrix[i - 1][j - 1];
                } else {
                    matrix[i][j] = maxOf(matrix[i - 1][j], matrix[i][j - 1]);
                }
            }
        }
        print(matrix);
        return matrix[word2.length()][word1.length()];
    }

    private int maxOf(int x, int y) {
        return x > y ? x : y;
    }

    private void print(int[][] matrix) {
        for (int i = 0; i <= word2.length(); i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j <= word1.length(); j++) {
                builder.append(matrix[i][j]).append("|");
            }
            System.out.println(builder.toString());
        }
        System.out.println("---------------------------------------------------------------");
    }

    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence("abbcdgf", "bbadcgf");
        System.out.println("LCS=" + lcs.getLCS());
    }

}
