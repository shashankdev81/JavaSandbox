package leetcode2;


import java.util.HashSet;
import java.util.Set;

public class MinJumps {

    public static void main(String[] args) {
        MinJumps minJumps = new MinJumps();
        System.out.println(minJumps.minimumJumps(new int[]{8, 3, 16, 6, 12, 20}, 15, 13, 11));

    }

    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        Set<Integer> forbiddenNos = new HashSet<>();
        for (int i = 0; i < forbidden.length; i++) {
            forbiddenNos.add(forbidden[i]);
        }

        System.out.println("minimumJumps a,b,x:" + a + "," + b + "," + x);
        int dest = a;
        int prev = a;
        int minJumps = 1;
        boolean isBackPossible = true;
        while (dest - prev != 1) {
            if (dest < 0) {
                minJumps = -1;
                break;
            }
            prev = dest;
            if (dest < x && !forbiddenNos.contains(dest + a)) {
                dest = dest + a;
                isBackPossible = true;
            } else if (dest > x && isBackPossible && !forbiddenNos.contains(dest - b)) {
                dest = dest - b;
                isBackPossible = false;
            } else if (dest == x) {
                break;
            } else {
                minJumps = -1;
                break;
            }
            System.out.println("prev, dest, isBackPossible, minJumps:" + prev + "," + dest + "," + isBackPossible + "," + minJumps);
            minJumps++;
        }
        return minJumps;
    }
}


// 0,1,2,3, 4, 5, 6, 7, 8, 9, 10, x=11, 12, 13, 14, 15
//forbdn = 3,6,8,12,16,20
// a=15, b=13

