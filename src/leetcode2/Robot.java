package leetcode2;

public class Robot {

    public static void main(String[] args) {
        Robot robot = new Robot(20, 13);
        robot.step(12);
        robot.step(21);
        robot.step(17);
        System.out.println(robot.getPos());
        System.out.println(robot.getDir());
        System.out.println(robot.getDir());

    }

    private int W;

    private int H;

    private int x;

    private int y;

    private enum Dir {
        East(1, 0), West(-1, 0), North(0, 1), South(0, -1);
        int x;
        int y;

        Dir(int i, int j) {
            x = i;
            y = j;
        }
    }

    private Dir dir = Dir.East;

    public Robot(int width, int height) {
        W = width;
        H = height;
    }

    public void step(int num) {
        int count = num;
        while (count-- > 0) {
            if ((x == W - 1 && y == 0) || (y == H - 1 && x == W - 1) || (x == 0 && y == H - 1) || (y == 0 && x == 0)) {
                dir = turn(dir);
            }
            x += dir.x;
            y += dir.y;
        }
    }

    private Dir turn(Dir dir) {
        switch (dir) {
            case East:
                return Dir.North;
            case North:
                return Dir.West;
            case West:
                return Dir.South;
            case South:
                return Dir.East;
        }
        return Dir.East;
    }

    public int[] getPos() {
        return new int[]{x, y};
    }

    public String getDir() {
        return dir.name();
    }
}

/**
 * Your Robot object will be instantiated and called as such:
 * Robot obj = new Robot(width, height);
 * obj.step(num);
 * int[] param_2 = obj.getPos();
 * String param_3 = obj.getDir();
 */