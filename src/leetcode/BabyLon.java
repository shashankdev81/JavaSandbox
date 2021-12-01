package leetcode;

import java.util.*;

public class BabyLon {

    private int STACK_COUNT = 12;

    private int COLORS = 4;

    private class TileStack {

        private List<List<Integer>> stacks = new LinkedList<>();

        public TileStack() {
            int ind = 0;
            for (int i = 0; i < COLORS; i++) {
                for (int k = 0; k < STACK_COUNT / COLORS; k++) {
                    List<Integer> stack = new ArrayList<>();
                    stack.add(i);
                    stacks.add(stack);
                }
            }
        }


        public boolean putOnTop(int s1, int s2) {
            List<Integer> stack1 = stacks.get(s1);
            List<Integer> stack2 = stacks.get(s2);
            boolean isSameColour = stack1.get(stack1.size() - 1) == stack2.get(stack2.size() - 1);
            boolean isSameHeight = stack1.size() == stack2.size();
            if (!isSameColour && !isSameHeight) {
                return false;
            }
            stacks.get(s2).addAll(stack1);
            stacks.remove(s1);
            return true;
        }


    }

    private class Board {

        private TileStack player1Stack = new TileStack();

        private TileStack player2Stack = new TileStack();

        private boolean isPlayerOneWins = false;

        public boolean isPlayerOneWins(int s1, int s2) {
            move(s1, s2, true);
            return isPlayerOneWins;

        }

        private void move(int s1, int s2, boolean isPlayerOne) {
            if (isPlayerOne && player1Stack.putOnTop(s1, s2)) {
                if (player1Stack.stacks.size() == 1) {
                    isPlayerOneWins = true;
                }
                for (int i = 0; i < player2Stack.stacks.size(); i++) {
                    for (int j = 0; j < player2Stack.stacks.size(); j++) {
                        if (i != j) {
                            move(i, j, false);
                        }
                    }
                }

            } else if (!isPlayerOne && player2Stack.putOnTop(s1, s2)) {
                for (int i = 0; i < player1Stack.stacks.size(); i++) {
                    for (int j = 0; j < player1Stack.stacks.size(); j++) {
                        if (i != j) {
                            move(i, j, true);
                        }
                    }
                }
            }
        }

    }
}
