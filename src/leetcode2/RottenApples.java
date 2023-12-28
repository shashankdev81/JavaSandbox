package leetcode2;

import java.util.LinkedHashMap;
import java.util.Map;

public class RottenApples {

    public static void main(String[] args) {
        RottenApples rottenApples = new RottenApples();
        System.out.println(
            rottenApples.eatenApples(new int[]{3, 1, 1, 0, 0, 2}, new int[]{3, 1, 1, 0, 0, 2}));
    }


    public int eatenApples(int[] apples, int[] days) {
        LinkedHashMap<Integer, Integer> appleRotMap = new LinkedHashMap<>();
        int[] appleGrownArr = new int[apples.length];
        int eatenApples = 0;
        for (int i = 0; i < apples.length; i++) {
            int daysToRot = i + days[i];
            appleGrownArr[i] +=
                apples[i] + (i > 0 ? appleGrownArr[i - 1] : 0) - appleRotMap.getOrDefault(daysToRot,
                    0);
            appleGrownArr[i] = appleGrownArr[i] < 0 ? 0 : appleGrownArr[i];
            appleRotMap.put(daysToRot, appleRotMap.getOrDefault(daysToRot, 0) + apples[i]);
        }
        for (int i = 0; i < appleGrownArr.length; i++) {
            if (appleGrownArr[i] > 0) {
                eatenApples++;
            }
        }
        int i = apples.length;
        if (appleGrownArr[appleGrownArr.length - 1] > 1) {
            eatenApples += appleGrownArr[appleGrownArr.length - 1] - 1;
        }

        return eatenApples;
    }

}
