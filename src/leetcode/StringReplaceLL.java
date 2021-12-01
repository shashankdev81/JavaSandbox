package leetcode;

public class StringReplaceLL {


    private Node word;


    private class Node {
        private char c;
        private Node next;

        public Node(char c, Node next) {
            this.c = c;
            this.next = next;
        }

        public Node replace(int[] indices, int pos, String expectedWord, String targetWord) {
            Pair insertPoints = exists(indices[pos], expectedWord.toCharArray());
            Pair newWord = create(targetWord);
            if (insertPoints == null) {
                return null;
            } else if (insertPoints.start == null) {
                newWord.end.next = insertPoints.end;
                increment(indices, pos + 1, targetWord.length());
                return newWord.start;
            } else {
                insertPoints.start.next = newWord.start;
                newWord.end.next = insertPoints.end;
                increment(indices, pos + 1, targetWord.length());
                return word;
            }
        }


        private Pair exists(int pos, char[] substrArr) {
            Node curr = word;
            int ind = 0;
            int substrInd = 0;
            boolean exists = true;
            Node prevToStart = null;
            while (curr != null && ind < pos) {
                prevToStart = curr;
                curr = curr.next;
                ind++;
            }
            Node end = curr;
            while (exists && substrInd < substrArr.length && curr != null) {
                exists = exists && curr.c == substrArr[substrInd++];
                if (curr != end) {
                    end = curr;
                }
                curr = curr.next;
            }

            return exists ? new Pair(prevToStart, curr) : null;
        }

        @Override
        public String toString() {
            return c + (next == null ? "" : next.toString());
        }
    }

    private class Pair {
        private Node start;
        private Node end;

        public Pair(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }

    private void increment(int[] indices, int start, int length) {
        for (int i = start; i < indices.length; i++) {
            indices[i] = indices[i] + length - 1;
        }
    }

    private Pair create(String s) {
        Node curr = null;
        Node start = null;

        char[] wordArr = s.toCharArray();
        int index = 0;
        while (index < wordArr.length) {
            Node letter = new Node(wordArr[index++], null);
            if (start == null) {
                start = letter;
                curr = start;
            } else {
                curr.next = letter;
                curr = curr.next;
            }
        }
        return new Pair(start, curr);
    }

    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        word = create(s).start;
        for (int i = 0; i < sources.length; i++) {
            Node res = word.replace(indices, i, sources[i], targets[i]);
            word = res == null ? word : res;
        }
        return word.toString();
    }

    public static void main(String[] args) {
        StringReplaceLL replace = new StringReplaceLL();
        String replaced1 = replace.findReplaceString("abcd", new int[]{0, 2}, new String[]{"a", "cd"}, new String[]{"eee", "ffff"});
        String replaced2 = replace.findReplaceString("abcd", new int[]{0, 2}, new String[]{"ab", "ec"}, new String[]{"eee", "ffff"});
        String replaced3 = replace.findReplaceString("vmokgggqzp", new int[]{3, 5, 1}, new String[]{"kg", "ggq", "mo"}, new String[]{"s", "so", "bfr"});
        System.out.println(replaced1);
        System.out.println(replaced2);
        System.out.println(replaced3.equalsIgnoreCase("vbfrssozp"));
    }
}
