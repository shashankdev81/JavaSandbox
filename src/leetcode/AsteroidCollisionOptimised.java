package leetcode;

import java.util.*;

public class AsteroidCollisionOptimised {

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack();
        for (int ast : asteroids) {
            collision:
            {
                while (!stack.isEmpty() && ast < 0 && 0 < stack.peek()) {
                    if (stack.peek() < -ast) {
                        stack.pop();
                        continue;
                    } else if (stack.peek() == -ast) {
                        stack.pop();
                    }
                    break collision;
                }
                stack.push(ast);
            }
        }

        int[] ans = new int[stack.size()];
        for (int t = ans.length - 1; t >= 0; --t) {
            ans[t] = stack.pop();
        }
        return ans;
    }

    public static void main(String[] args) {
        AsteroidCollisionOptimised asteroidCollision = new AsteroidCollisionOptimised();
        int[] res1 = asteroidCollision.asteroidCollision(new int[]{5, 10, -5});
        int[] res2 = asteroidCollision.asteroidCollision(new int[]{8, -8});
        int[] res3 = asteroidCollision.asteroidCollision(new int[]{10, 2, -5});
        int[] res4 = asteroidCollision.asteroidCollision(new int[]{-2, -1, 1, 2});
    }
}
