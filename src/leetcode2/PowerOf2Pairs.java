package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

class PowerOf2Pairs {
    public static void main(String[] args) {
        PowerOf2Pairs powerOf2Pairs = new PowerOf2Pairs();
        System.out.println(powerOf2Pairs.countPairs(new int[]{1, 1, 1, 3, 3, 3, 7}));
    }

    private long mod = (long) Math.pow(10, 9) + 7;

    public int countPairs(int[] deliciousness) {

        final Set<Integer> powerSet = new HashSet<>();
        for (int i = 0; i <= 21; i++) {
            powerSet.add(Double.valueOf(Math.pow(2, i)).intValue());
        }
        //System.out.println(Arrays.stream(deliciousness).sorted().boxed().collect(Collectors.toList()));
        int res = 0;
        final Map<Integer, Integer> deliciousnessMap = new HashMap<>();
        for (int i = 0; i < deliciousness.length; i++) {
            int currNum = deliciousness[i];
            deliciousnessMap.putIfAbsent(currNum, 0);
            deliciousnessMap.put(currNum, deliciousnessMap.get(currNum) + 1);
        }
        Map<Integer, Integer> copyOfDeliciousnessMap = new HashMap<>();
        copyOfDeliciousnessMap.putAll(deliciousnessMap);
        for (Map.Entry<Integer, Integer> entry : deliciousnessMap.entrySet()) {
            final int key = entry.getKey();
            final int combs = entry.getValue();
            List<Integer> matches1 = powerSet.stream().filter(e -> {
                        System.out.println(e + "," + copyOfDeliciousnessMap.get(e - key));
                        return copyOfDeliciousnessMap.containsKey(e - key) && (e - key) != key;
                    })
                    .map(e -> e - key).collect(Collectors.toList());
            System.out.println("matches1=" + key + "," + matches1.size());
            List<Integer> matches2 = powerSet.stream().filter(e -> copyOfDeliciousnessMap.containsKey(e - key) && (e - key) == key)
                    .map(e -> e - key).collect(Collectors.toList());
            System.out.println("matches2=" + key + "," + matches2.size());
            res += matches1.stream().map(m -> deliciousnessMap.getOrDefault(m, 0) * combs).mapToInt(i -> i.intValue()).sum();
            res += (matches2.size() >= 1 && deliciousnessMap.get(key) > 1) ? deliciousnessMap.get(key) * (deliciousnessMap.get(key) - 1) : 0;
            copyOfDeliciousnessMap.remove(key);
        }
        //return traverse(deliciousness, 0, 0, 0, "");
        return (int) ((res) % mod);

    }

}