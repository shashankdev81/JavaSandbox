package leetcode2;

import java.util.*;

/*
Player who makes last valid move wins -  flips ++ to -- instring
* */
public class FlipGame {

    public static void main(String[] args) {

        Solution solution = new Solution();

        System.out.println(solution.canWin("++++"));
        System.out.println(solution.canWin("+"));
        System.out.println(solution.canWin("+++++++++"));


    }

    private static class Solution {

        public boolean canWin(String currentState) {
            return move(currentState.toCharArray(), 0, true);
        }

        private boolean move(char[] chars, int pos, boolean isFirstPlayer) {
            if (gameTerminated(chars, pos, isFirstPlayer)) {
                return true;
            }
            for (int i = pos; i < chars.length - 1; i++) {
                if (chars[i] == '+' && chars[i + 1] == '+') {
                    chars[i] = '-';
                    chars[i + 1] = '-';
                    if (move(chars, i + 1, !isFirstPlayer)) {
                        return true;
                    } else {
                        chars[i] = '+';
                        chars[i + 1] = '+';
                    }
                }

            }
            return false;
        }

        private boolean gameTerminated(char[] chars, int pos, boolean isFirstPlayer) {
            return !isConsecutivePlusExists(chars) && !isFirstPlayer;
        }

        private boolean isConsecutivePlusExists(char[] chars) {
            if (chars.length < 2) {
                return false;
            }
            for (int i = 0; i < chars.length - 1; i++) {
                if (chars[i] == '+' && chars[i + 1] == '+') {
                    return true;
                }
            }
            return false;
        }
    }
}

