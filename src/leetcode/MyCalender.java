package leetcode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MyCalender {
    private List<Integer> timeSlots = new LinkedList<Integer>();

    public boolean book(int start, int end) {
        if (timeSlots.isEmpty() || start > timeSlots.get(timeSlots.size() - 1)) {
            timeSlots.add(start);
            timeSlots.add(end);
            return true;
        }
        int startInd = Collections.binarySearch(timeSlots, start);
        int endInd = Collections.binarySearch(timeSlots, end);

        if (startInd > 0 && isValidSlot(startInd, endInd) && endInd > 0 && endInd - startInd == 1) {
            timeSlots.add(startInd + 1, start);
            timeSlots.add(startInd + 2, end);
            return true;
        }
        if (startInd < 0 && endInd < 0 && startInd == endInd && isValidSlot(-startInd - 1, -endInd)) {
            startInd = -startInd;
            endInd = -endInd;
            timeSlots.add(startInd - 1, start);
            timeSlots.add(startInd, end);
            return true;
        }
        return false;

    }

    private boolean isValidSlot(int startInd, int endInd) {
        return (startInd + 1) % 2 != 0 && (endInd + 1) % 2 == 0;
    }


    public static void main(String[] args) {
        MyCalender calender = new MyCalender();
        System.out.println(calender.book(10, 20));
        System.out.println(calender.book(15, 25));
        System.out.println(calender.book(20, 30));
//        System.out.println(calender.book(30, 40));
//        System.out.println(calender.book(20, 30));
//        System.out.println(calender.book(24, 28));

    }
}
