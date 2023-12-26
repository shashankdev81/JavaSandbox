package leetcode2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import org.asynchttpclient.util.StringUtils;

public class Calculator {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        // System.out.println(calculator.calculate("1+1"));
        //System.out.println(calculator.calculate("6-4/2"));
        System.out.println(calculator.calculate("2*(5+5*2)/3+(6/2+8)"));
    }

    Set<Character> commands = new HashSet<>(
        Arrays.asList(new Character[]{'-', '+', '*', '/', '(', ')'}));



    public int calculate(String s) {
        int result = 1;
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (commands.contains(s.charAt(i))) {
                stack.push("" + s.charAt(i));
            } else {
                stack.push(Integer.valueOf("" + s.charAt(i)).toString());
            }
        }

        return calculate(stack);
    }

    private int recurse(Stack<String> stack) {
        Stack<String> temp = new Stack<>();
        stack.pop();
        while (!stack.isEmpty() && !stack.peek().equals("(")) {
            temp.push(stack.pop());
        }
        stack.pop();
        Stack<String> temp2 = new Stack<>();
        while (!temp.isEmpty()) {
            temp2.push(temp.pop());
        }
        return calculate(temp2);
    }

    private int calculate(Stack<String> stack) {
        int result = 0;
        while (!stack.isEmpty()) {
            if (stack.peek().equals(")")) {
                stack.push(Integer.valueOf(recurse(stack)).toString());
            } else if (!stack.isEmpty() && stack.peek().equals("/")) {
                stack.pop();
                int res = stack.peek().equals(")") ? recurse(stack) / result
                    : Integer.valueOf(stack.pop()) / result;
                stack.push(Integer.toString(res));
            } else if (!stack.isEmpty() && stack.peek().equals("*")) {
                stack.pop();
                int res = calculate(stack) * result;
                stack.push(Integer.toString(res));
            } else if (stack.peek().equals("+")) {
                stack.pop();
                result += calculate(stack);
            } else if (stack.peek().equals("-")) {
                stack.pop();
                result = calculate(stack) - result;
            } else {
                result = Integer.valueOf(stack.pop());
            }
        }
        return result;
    }

}
