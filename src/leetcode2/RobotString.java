package leetcode2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Stack;
import java.util.stream.Collectors;

public class RobotString {

    public static void main(String[] args) {
        RobotString robotString = new RobotString();
        System.out.println(robotString.robotWithString("bydizfve"));
    }

    public String robotWithString(String s) {
        ArrayDeque<Character> queue = new ArrayDeque<>();
        int minChar = Integer.MAX_VALUE;
        int minCharIdx = -1;
        char[] charArr = s.toCharArray();
        for (int i = 0; i < charArr.length; i++) {
            if ((int) charArr[i] < minChar) {
                minCharIdx = i;
            }
            queue.offer(charArr[i]);
        }
        Stack<Character> stack = new Stack<>();
        StringBuilder builder = new StringBuilder();
        stack.push(queue.pollFirst());
        int polled = 0;
        while (true) {
            while (!queue.isEmpty() && ((polled + 1) < minCharIdx
                || stack.peek() >= queue.peek())) {
                stack.push(queue.pollFirst());
                polled++;
            }
            if (!queue.isEmpty() && queue.peek() == minChar) {
                stack.push(queue.poll());
            }
            while (!stack.isEmpty() && (queue.isEmpty() || stack.peek() <= queue.peek())) {
                builder.append(stack.pop());
            }

            if (stack.isEmpty() && !queue.isEmpty()) {
                stack.push(queue.pollFirst());
            } else if (queue.isEmpty()) {
                break;
            }
        }
        return builder.toString();

    }
}
