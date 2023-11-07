package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

class ExamRoom {

    int capacity; // this is just to keep track of the max allowed size
    TreeSet<Integer> seats;
    /* why treeset, because seats will be sorted automatically
       and get first()/last() element in log(n) time.
    */
    private TreeMap<Integer, Integer> votes = new TreeMap<>();

    private TreeMap<Integer, Integer> leaderToTimeMap = new TreeMap<>();


    public ExamRoom(int n) {
        this.capacity = n;
        this.seats = new TreeSet<>();
    }

    public static void main(String[] args) {
        ExamRoom room = new ExamRoom(10);
        room.seat();
        room.seat();
        room.seat();
        room.seat();
        room.leave(4);
        room.seat();
    }

    public int seat() {
        int seatNumber = 0;
		/*
		  Return 0 for first attempt ( as mentioned in question)
		  Otherwise, we need to calculate the max distance by checking the whole treeset : O(n) time.
		  Note that "distance" variable is initialized to first appearing seat,
		  why because the distance calculation is based on current seat and the seat before that.
		  Find the maximum distance and update the seat number accordingly.
		  distance calculation -> (current seat - previous seat )/ 2
		  Update the max distance at each step.
		  New seat number will be ->  previous seat number + max distance

		  Now, before returning the end result,  check for one more edge case:
		  That is, if the max distance calculated is less than ->  capacity-1-seats.last()

		  Why because -> if last seat number in treeset is far from last position,
		  then  the largest distance possible is the last position.

		*/
        if (seats.size() > 0) {
            Integer prev = null;
            int distance = seats.first();
            for (Integer seat : seats) {
                if (prev != null) {
                    int d = (seat - prev) / 2;
                    if (distance < d) {
                        distance = d;
                        seatNumber = prev + distance;
                    }
                }
                prev = seat;
            }

            if (distance < capacity - 1 - seats.last()) {
                seatNumber = capacity - 1;
            }
        }
        seats.add(seatNumber);
        return seatNumber;
    }

    public void leave(int p) {
        seats.remove(p);
		/* simply remove the seat number from treeset
		 and treeset will be automatically adjust its order in log(n) time.
		*/
    }
}
