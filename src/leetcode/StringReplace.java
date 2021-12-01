package leetcode;

public class StringReplace {

    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        char[] wordArray = s.toCharArray();
        for (int i = 0; i < sources.length; i++) {
            if (isExists(wordArray, indices[i], sources[i].length(), sources[i].toCharArray())) {
                int oldLength = wordArray.length;
                wordArray = replace(wordArray, indices[i], sources[i].length(), targets[i].length(), targets[i].toCharArray());
                int newLength = wordArray.length;
                incrementTargetIndex(indices, indices[i], newLength - oldLength);
            }
        }

        return new String(wordArray);

    }

    private void incrementTargetIndex(int[] inpIndces, int after, int diff) {
        for (int i = 0; i < inpIndces.length; i++) {
            if (inpIndces[i] > after) {
                inpIndces[i] += diff;
            }
        }
    }

    private boolean isExists(char[] word, int index, int length, char[] substring) {
        if ((index + length) > word.length) {
            return false;
        }
        boolean isExists = true;
        int i = index;
        int j = 0;
        while (isExists && i < (index + length)) {
            isExists = isExists && (word[i++] == substring[j++]);
        }
        return isExists;
    }

    private char[] replace(char[] inputWord, int start, int sourceLength, int targetLength, char[] target) {
        char[] newWord = new char[inputWord.length + targetLength - sourceLength];
        copy(inputWord, 0, start, newWord, 0);
        copy(target, 0, target.length, newWord, start);
        copy(inputWord, start + sourceLength, inputWord.length, newWord, start + targetLength);
        return newWord;
    }

    private void copy(char[] inputWord, int start, int end, char[] newWord, int curr) {
        for (int i = start, k = curr; i < end; i++, k++) {
            newWord[k] = inputWord[i];
        }
    }

    private char[] replace1(char[] inputWord, int startOfSubstr, int sourceLength, int targetLength, char[] target) {
        char[] newWord = new char[inputWord.length + targetLength - sourceLength];
        int indOfNewWord = 0;
        int oldInd = 0;
        while (indOfNewWord < startOfSubstr) {
            newWord[indOfNewWord] = inputWord[oldInd];
            indOfNewWord++;
            oldInd++;
        }
        int subInd = 0;
        int maxInd = indOfNewWord + targetLength;
        while (indOfNewWord < maxInd) {
            newWord[indOfNewWord] = target[subInd];
            indOfNewWord++;
            subInd++;
        }
        oldInd = startOfSubstr + sourceLength;
        while (oldInd < inputWord.length && indOfNewWord < (inputWord.length + targetLength - 1)) {
            newWord[indOfNewWord] = inputWord[oldInd];
            indOfNewWord++;
            oldInd++;
        }
        return newWord;
    }

    public static void main(String[] args) {
        StringReplace replace = new StringReplace();
        String replaced1 = replace.findReplaceString("abcd", new int[]{0, 2}, new String[]{"a", "cd"}, new String[]{"eee", "ffff"});
        String replaced2 = replace.findReplaceString("abcd", new int[]{0, 2}, new String[]{"ab", "ec"}, new String[]{"eee", "ffff"});
        String replaced3 = replace.findReplaceString("vmokgggqzp", new int[]{3, 5, 1}, new String[]{"kg", "ggq", "mo"}, new String[]{"s", "so", "bfr"});
        System.out.println(replaced1);
        System.out.println(replaced2);
        System.out.println(replaced3);
        System.out.println(replaced3.equalsIgnoreCase("vbfrssozp"));
    }
}

