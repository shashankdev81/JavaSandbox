package leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TextJustification {

    private int MAX_LINE_LENGTH = 16;

    public List<String> fullJustify(String[] words, int maxWidth) {
        MAX_LINE_LENGTH = maxWidth;
        Queue<String> queue = new LinkedList<String>();
        List<String> result = new LinkedList<String>();
        int currSize = 0;
        for (String word : words) {
            if (!isAppendable(queue, currSize, word)) {
                result.add(drainQueue(queue, currSize, false));
                currSize = 0;
            }
            queue.add(word);
            currSize += word.length();
        }
        if (!queue.isEmpty()) {
            result.add(drainQueue(queue, currSize, true));
        }
        return result;

    }

    private boolean isAppendable(Queue<String> queue, int currSize, String word) {
        int minWhiteSpaces = queue.size();
        return currSize + word.length() + minWhiteSpaces <= MAX_LINE_LENGTH;
    }

    private String drainQueue(Queue<String> queue, int currSize, boolean isLast) {
        int spaces = MAX_LINE_LENGTH - currSize;
        int minSlots = queue.size() == 1 ? 1 : (queue.size() - 1);
        int emptySlotWidth = spaces / minSlots;
        int undistributedSpace = spaces % minSlots;
        int[] slotWidth = new int[minSlots];
        for (int i = 0; i < slotWidth.length; i++) {
            if (isLast) {
                slotWidth[i] = 1;
            } else {
                slotWidth[i] = emptySlotWidth + (undistributedSpace > 0 ? 1 : 0);
                undistributedSpace--;
            }
        }
        boolean isFirst = true;
        StringBuilder builder = new StringBuilder();

        int k = 0;
        while (!queue.isEmpty()) {
            builder.append(queue.poll());
            if (!queue.isEmpty()) {
                builder.append(emptySpace(slotWidth[k]));
                k++;
            }
        }
        if (builder.length() < MAX_LINE_LENGTH) {
            builder.append(emptySpace(MAX_LINE_LENGTH - builder.length()));
        }
        return builder.toString();
    }

    private String emptySpace(int width) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < width; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        TextJustification textJustification = new TextJustification();
        System.out.println(textJustification.fullJustify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16));
        System.out.println(textJustification.fullJustify(new String[]{"What", "must", "be", "acknowledgment", "shall", "be"}, 16));
        System.out.println(textJustification.fullJustify(new String[]{"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"}, 20));
        System.out.println(textJustification.fullJustify(new String[]{"a"}, 1));
    }
}
