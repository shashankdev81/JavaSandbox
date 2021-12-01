package dp;

public class InterleavedString {


    private boolean isInterleaved(String s1, String s2, String s) {
        if (s.length() != (s1.length() + s2.length())) {
            return false;
        }
        char[] charArr = s.toCharArray();
        int last = charArr.length - 1;
        boolean isLastS1 = isLast(charArr[last], s1);
        boolean isLastS2 = isLast(charArr[last], s2);
        if (last == 0 && (isLastS1 || isLastS2)) {
            return true;
        }
        if (!isLastS1 && !isLastS2) {
            return false;
        }

        if (isLastS1 && !isLastS2) {
            return isInterleaved(strippedOfLast(s1), s2, strippedOfLast(s));
        } else if (isLastS2 && !isLastS1) {
            return isInterleaved(s1, strippedOfLast(s2), strippedOfLast(s));
        } else {
            return isInterleaved(strippedOfLast(s1), s2, strippedOfLast(s)) || isInterleaved(s1, strippedOfLast(s2), strippedOfLast(s));
        }
    }

    private String strippedOfLast(String s1) {
        return s1.substring(0, s1.length() - 1);
    }

    private boolean isLast(char c, String s1) {
        return !s1.isEmpty() && c == s1.charAt(s1.length() - 1);
    }

    public static void main(String[] args) {
        InterleavedString interleavedString = new InterleavedString();
        System.out.println(interleavedString.isInterleaved("aab", "axy", "aaxaby"));
        System.out.println(interleavedString.isInterleaved("aab", "axy", "abaaxy"));
        System.out.println(interleavedString.isInterleaved("cat", "rat", "cartta"));
        System.out.println(interleavedString.isInterleaved("cat", "rat", "ratcat"));

    }
}
