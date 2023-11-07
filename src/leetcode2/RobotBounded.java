package leetcode2;

import java.util.*;
import java.util.stream.Collectors;

public class RobotBounded {

    public static void main(String[] args) {

        RobotBounded robotBounded = new RobotBounded();
        System.out.println(robotBounded.isRobotBounded("GLGLGGLGL"));
    }

    public boolean isRobotBounded(String instructions) {
        if (instructions == null || instructions.equals("")) {
            return true;
        }
        Queue<Character> instQueue = new ArrayDeque<Character>();
        List<Character> instList = instructions.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        instQueue.addAll(instList);
        return move(instQueue, new LinkedHashSet<>(), Direction.NORTH, 0, 0, 0);
    }

    private boolean move(Queue<Character> queue, Set<String> pastPositions, Direction dir, int i, int j, int count) {
        String pos = i + ":" + j + ":" + dir.name();
        System.out.println(pastPositions);
        System.out.println(pos);
        int distanceFromOrigin = Math.max(i, j);
        if (count == queue.size() && dir.equals(Direction.NORTH)  && distanceFromOrigin > 0) {
            return false;
        }

        if (pastPositions.contains(pos) && count == queue.size()) {
            return true;
        }
        pastPositions.add(pos);
        Character inst = queue.poll();
        Direction newDir = dir;
        if (inst == 'G') {
            if (dir.equals(Direction.NORTH)) {
                j++;
            }
            if (dir.equals(Direction.SOUTH)) {
                j--;
            }
            if (dir.equals(Direction.WEST)) {
                i--;
            }
            if (dir.equals(Direction.EAST)) {
                i++;
            }
        } else if (inst == 'L') {
            if (dir.equals(Direction.NORTH)) {
                newDir = Direction.WEST;
            }
            if (dir.equals(Direction.SOUTH)) {
                newDir = Direction.EAST;
            }
            if (dir.equals(Direction.WEST)) {
                newDir = Direction.SOUTH;
            }
            if (dir.equals(Direction.EAST)) {
                newDir = Direction.NORTH;
            }
        } else if (inst == 'R') {
            if (dir.equals(Direction.NORTH)) {
                newDir = Direction.EAST;
            }
            if (dir.equals(Direction.SOUTH)) {
                newDir = Direction.WEST;
            }
            if (dir.equals(Direction.WEST)) {
                newDir = Direction.NORTH;
            }
            if (dir.equals(Direction.EAST)) {
                newDir = Direction.SOUTH;
            }
        }
        queue.offer(inst);
        return move(queue, pastPositions, newDir, i, j, count + 1);

    }

    private enum Direction {
        NORTH, SOUTH, EAST, WEST;
    }


}