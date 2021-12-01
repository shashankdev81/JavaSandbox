package leetcode;

public class RLEIteratorSimpler {

    private class Node {
        private int value;
        private int run;
        private int offset;
        private Node next;

        public Node(int value, int run, Node next) {
            this.value = value;
            this.run = run;
            this.next = next;
        }

        public int deduct(int partOfOffset) {
            if (partOfOffset > (run - this.offset)) {
                int diff = run - this.offset;
                this.offset = run;
                return partOfOffset - diff;
            } else {
                this.offset += partOfOffset;
                return 0;
            }
        }
    }

    private int[] encodedArray;

    private Node head;


    private Node curr;

    public RLEIteratorSimpler(int[] encoding) {
        Node prev = null;
        for (int i = 0; i < encoding.length; i += 2) {
            if (encoding[i] == 0) {
                continue;
            }
            Node node = new Node(encoding[i + 1], encoding[i], null);
            if (head == null) {
                head = node;
                prev = node;
            } else if (prev.value == encoding[i + 1]) {
                prev.run += encoding[i];
            } else {
                prev.next = node;
                prev = node;
            }
        }
        curr = head;
    }

    public int next(int n) {
        int offsets = n;

        while (offsets > 0 && curr != null) {
            offsets = curr.deduct(offsets);
            if (offsets > 0) {
                curr = curr.next;
            }
        }

        return curr == null ? -1 : curr.value;
    }


    public static void main(String[] args) {
        RLEIteratorSimpler iterator = new RLEIteratorSimpler(new int[]{3, 8, 0, 9, 2, 5});
        System.out.println(iterator.next(2));
        System.out.println(iterator.next(1));
        System.out.println(iterator.next(1));
        System.out.println(iterator.next(2));

    }
}
