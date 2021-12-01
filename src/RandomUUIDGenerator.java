import java.util.Base64;
import java.util.Random;

public class RandomUUIDGenerator {

    public static void main(String[] args) {

        long max = (long) Math.pow(10, 15);
        long random = randomWithRange(1, max);
        byte[] bytes = String.valueOf(random).getBytes();
        System.out.println("Long as String bytes=" + bytes.length);
        String uuid = Base64.getEncoder().encodeToString(bytes);
        System.out.println("UUID=" + uuid);

        long now = System.currentTimeMillis();
        long nowMinus = now << 14;
        System.out.println("now, nowMinus:" + now + "," + nowMinus);
    }

    private static long randomWithRange(long min, long max) {   //defining method for a random number generator

        long range = (max - min) + 1;
        return (long) (Math.random() * range) + min;
    }
}
