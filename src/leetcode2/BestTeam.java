package leetcode2;

import java.util.Arrays;
import java.util.Set;
import java.util.Stack;

public class BestTeam {

    public static void main(String[] args) {
        BestTeam bestTeam = new BestTeam();
        System.out.println(
            bestTeam.bestTeamScore(new int[]{1,2,3,5}, new int[]{8,9,10,1}));
    }

    public int bestTeamScore(int[] scores, int[] ages) {
        int[][] ageScoreArr = new int[scores.length][2];
        for (int i = 0; i < scores.length; i++) {
            ageScoreArr[i][0] = ages[i];
            ageScoreArr[i][1] = scores[i];
        }
        Arrays.sort(ageScoreArr, (a1, a2) -> a1[0] - a2[0]);
        Stack<int[]> stack = new Stack<>();
        int sumSoFar = 0;
        int i = 0;
        while (i < ageScoreArr.length) {
            if (stack.isEmpty() || stack.peek()[1] <= ageScoreArr[i][1]) {
                stack.push(ageScoreArr[i]);
                sumSoFar += ageScoreArr[i][1];
                i++;
            } else if (stack.peek()[1] > ageScoreArr[i][1]) {
                int newSum = 0;
                while (stack.peek()[1] > ageScoreArr[i][1]) {
                    newSum += ageScoreArr[i][1];
                    i++;
                    if (newSum > sumSoFar) {
                        break;
                    }
                }

                if (newSum > sumSoFar) {
                    while (!stack.isEmpty() && stack.peek()[1] > ageScoreArr[i][1]) {
                        sumSoFar -= stack.pop()[1];
                    }
                    sumSoFar += newSum;
                }
            } else {
                sumSoFar += ageScoreArr[i][1];
                i++;
            }
        }
        return sumSoFar;

    }

}
