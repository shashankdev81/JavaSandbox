package heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ProfitMaximisation {

    public static void main(String[] args) {
        int seats[] = {2, 3, 4, 5, 1};
        int n = 6;
        int sales = 0;
        PriorityQueue<Seats> prospectiveSales = new PriorityQueue<Seats>(new SeatsComparator());
        //SeatsValuationStrategy strategy = new EmptySeatsValueMaximisationStrategy();
        SeatsValuationStrategy strategy = new EmptySeatsValueMinimisationStrategy();

        for (int i = 0; i < seats.length; i++) {
            prospectiveSales.add(new Seats(seats[i], strategy.getEachSeatValue(seats[i], i + 1), i + 1));
        }

        while (n > 0) {
            Seats costliestSeat = prospectiveSales.poll();
            sales += costliestSeat.moneyPerSeat;
            costliestSeat.seats--;
            costliestSeat.moneyPerSeat = strategy.getEachSeatValue(costliestSeat.seats, costliestSeat.rowNo);
            prospectiveSales.add(costliestSeat);
            n--;
        }

        System.out.println("Max profit=" + sales);
    }

    private static class Seats {
        int seats = 0;
        int moneyPerSeat = 0;
        int rowNo;

        public Seats(int seats, int moneyPerSeat, int rowNo) {
            this.seats = seats;
            this.moneyPerSeat = moneyPerSeat;
        }
    }

    private static class SeatsComparator implements Comparator<Seats> {

        @Override
        public int compare(Seats o1, Seats o2) {
            return Integer.compare(o2.moneyPerSeat * o2.seats, o1.seats * o1.moneyPerSeat);
        }
    }

    private interface SeatsValuationStrategy {
        int getEachSeatValue(int noOfSeats, int rowNo);
    }

    private static class EmptySeatsValueMaximisationStrategy implements SeatsValuationStrategy {

        @Override
        public int getEachSeatValue(int noOfEmptySeats, int rowNo) {
            return noOfEmptySeats;
        }
    }

    private static class EmptySeatsValueMinimisationStrategy implements SeatsValuationStrategy {

        int k = 2;

        @Override
        public int getEachSeatValue(int noOfEmptySeats, int rowNo) {
            return (int) ((rowNo * k) * (noOfEmptySeats == 0 ? 1 : 1 / noOfEmptySeats));
        }
    }
}
