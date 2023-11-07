package leetcode2;

import java.util.Arrays;

public class CookiesJar {

    public static void main(String[] args) {
        CookiesJar cookiesJar = new CookiesJar();
        System.out.println(cookiesJar.distributeCookies(new int[]{8,15,10,20,8}, 2));
        System.out.println(cookiesJar.distributeCookies(new int[]{64,32,16,8,4,2,1,1000}, 8));
        System.out.println(cookiesJar.distributeCookies(new int[]{76265,7826,16834,63341,68901,58882,50651,75609}, 8));
    }


    public int distributeCookies(int[] cookies, int k) {
        return distribute(new int[k], cookies, 0);
    }

    private int distribute(int[] kArr, int[] cookies, int index) {
        int unfairness = Integer.MAX_VALUE;
        if (index == cookies.length) {
            return Arrays.stream(kArr).max().orElseGet(() -> -1);
        }
        for (int i = 0; i < kArr.length; i++) {
            kArr[i] += cookies[index];
            unfairness = Math.min(unfairness, distribute(kArr, cookies, index + 1));
            kArr[i] -= cookies[index];
        }
        return unfairness;
    }


}