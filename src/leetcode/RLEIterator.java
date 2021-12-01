package leetcode;

public class RLEIterator {

    //index of current value
    private int index;

    //offset for a given value at index
    private int run;

    private int[] encodedArray;

    public RLEIterator(int[] encoding) {
        encodedArray = encoding;
        index = 0;
        run = 0;
    }

    public int next(int n) {
        int offsets = n;
        int item = 0;
        //exhaust n by consuming run length of each value
        while (offsets > 0 && index <= encodedArray.length - 2) {
            //if we have to cover lesser ground than the run length of current value
            if (offsets + run <= encodedArray[index]) {
                run += offsets;
                item = encodedArray[index + 1];
                //we are the end of run length of current value
                if (run == encodedArray[index]) {
                    incrementIndex();
                }
                return item;
            } else {
                //consume whats left of the run length of current value
                offsets = offsets - (encodedArray[index] - run);
                incrementIndex();
            }

        }
        return -1;
    }

    private void incrementIndex() {
        index += 2;
        //ignore all values which 0 run length
        while (index <= (encodedArray.length - 2) && encodedArray[index] == 0) {
            index += 2;
        }
        //reset run length
        run = 0;
    }

    public static void main(String[] args) {
        RLEIterator iterator = new RLEIterator(new int[]{3, 8, 0, 9, 2, 5});
        System.out.println(iterator.next(2));
        System.out.println(iterator.next(1));
        System.out.println(iterator.next(1));
        System.out.println(iterator.next(2));

    }
}
