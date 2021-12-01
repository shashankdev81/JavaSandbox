package leetcode;

import java.util.PriorityQueue;
import java.util.Queue;

public class HighWaterMark {


    private int lastAckInUnbrokenChain = Integer.MIN_VALUE;

    private Queue<Integer> pendingAcks = new PriorityQueue<Integer>();

    private int start;

    public void ack(int n) {
        if ((pendingAcks.isEmpty() && n == start) || (n == lastAckInUnbrokenChain + 1)) {
            lastAckInUnbrokenChain = n;
            while (!pendingAcks.isEmpty() && pendingAcks.peek() == (lastAckInUnbrokenChain + 1)) {
                lastAckInUnbrokenChain++;
                pendingAcks.poll();
            }
        } else {
            pendingAcks.add(n);
        }
    }

    public void start(int n) {
        start = n;
        ack(n);
    }

    public int getHWM() {
        return lastAckInUnbrokenChain;
    }


    public static void main(String[] args) {
        HighWaterMark hmw = new HighWaterMark();
        hmw.start(0);
        System.out.println(hmw.getHWM());
        hmw.ack(1);
        System.out.println(hmw.getHWM());
        hmw.ack(3);
        System.out.println(hmw.getHWM());
        hmw.ack(5);
        System.out.println(hmw.getHWM());
        hmw.ack(2);
        System.out.println(hmw.getHWM());
        hmw.ack(4);
        System.out.println(hmw.getHWM());

    }

}
