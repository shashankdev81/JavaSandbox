package leetcode2;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class StockSpanner {


    private Stack<int[]> st = new Stack<>();

    public StockSpanner() {

    }

    public static void main(String[] args) {
        StockSpanner stockSpanner = new StockSpanner();
        System.out.println(stockSpanner.next(100));
        System.out.println(stockSpanner.next(80));
        System.out.println(stockSpanner.next(60));
        System.out.println(stockSpanner.next(70));
        System.out.println(stockSpanner.next(60));
        System.out.println(stockSpanner.next(75));
        System.out.println(stockSpanner.next(85));
    }

    public int next(int price) {

        int span = 1;

        while (st.size() > 0 && price >= st.peek()[0]) {
            span += st.pop()[1];
        }
        String[] logArr = "test".split(" ");
        boolean isNumeric = Arrays.stream(logArr).skip(1)
            .allMatch(s -> s.chars().allMatch(Character::isDigit));
        st.push(new int[]{price, span});
        return span;
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such: StockSpanner obj = new
 * StockSpanner(); int param_1 = obj.next(price);
 */