package leetcode2;

import java.util.*;

class MaxStack {


    private TreeMap<Integer, Integer> sorted = new TreeMap<Integer, Integer>();

    private List<Integer> popped = new ArrayList<>();

    private Stack<Integer> stack = new Stack<>();

    public MaxStack() {

    }

    public static void main(String[] args) {
        MaxStack maxStack = new MaxStack();
        //test(maxStack);
        maxStack.push(5);
        maxStack.push(1);
        System.out.println(maxStack.popMax());
        System.out.println(maxStack.peekMax());


    }

    private static void test(MaxStack maxStack) {
        maxStack.push(5);
        maxStack.push(1);
        maxStack.push(5);
        System.out.println(maxStack.top());
        System.out.println(maxStack.popMax());
        System.out.println(maxStack.top());
        System.out.println(maxStack.peekMax());
        System.out.println(maxStack.pop());
        System.out.println(maxStack.top());
    }

    public void push(int x) {
        sorted.putIfAbsent(x, 0);
        sorted.put(x, sorted.get(x) + 1);
        stack.push(x);
    }

    public int pop() {
        while (stack.peek() != null && popped.contains(stack.peek())) {
            int removedEarlier = stack.pop();
            popped.remove(new Integer(removedEarlier));
        }
        int res = stack.pop();
        if (sorted.get(res) == 1) {
            sorted.remove(res);
        } else {
            sorted.put(res, sorted.get(res) - 1);
        }
        return res;

    }

    public int top() {
        return stack.peek();

    }

    public int peekMax() {
        return sorted.lastKey();
    }

    public int popMax() {
        int max = sorted.lastKey();
        if (stack.peek() != max) {
            popped.add(max);
        } else {
            stack.pop();
        }
        if (sorted.get(max) == 1) {
            sorted.remove(max);
        } else {
            sorted.put(max, sorted.get(max) - 1);
        }
        return max;
    }
}

/**
 * Your MaxStack object will be instantiated and called as such:
 * MaxStack obj = new MaxStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.peekMax();
 * int param_5 = obj.popMax();
 */
