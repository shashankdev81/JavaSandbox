package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PasswordLeak {


    public static void main(String[] args) {
        PasswordLeak passwordLeak = new PasswordLeak();
        System.out.println(passwordLeak.peopleAwareOfSecret(7, 3, 5));
    }

    private class City implements Comparable<City>{
        int val;
        int roads;
        int newValue;
        List<City> cities;

        public City(int val, int roads){
            this.val = val;
            this.roads = roads;
            cities = new ArrayList<>();
        }

        public void addCity(City city){
            cities.add(city);
            roads++;
        }

        public int hashCode(){
            return Objects.hash(val, roads);
        }

        public boolean equals(Object obj){
            if(obj==null || !this.getClass().equals(obj.getClass())){
                return false;
            }
            City ext = (City)obj;
            return this.val==ext.val && this.roads==ext.roads;
        }

        public int compareTo(City ext){
            if(this.roads==ext.roads){
                return this.val-ext.val;
            }else{
                return ext.roads-this.roads;
            }
        }
    }

    public int peopleAwareOfSecret(int n, int delay, int forget) {

        Map<Integer, long[]> peopleMap = new HashMap<>();
        peopleMap.put(1, new long[]{1, 0});
        for (int d = 1; d <= n; d++) {
            long[] canShareAndRememberNow = peopleMap.get(d);
            long maxLeaked = canShareAndRememberNow[0];
            for (int j = d; j < d + forget; j++) {
                peopleMap.putIfAbsent(j, new long[]{0, 0});
                long[] canShareAndRememberFuture = peopleMap.get(j);
                if (j >= d + delay) {
                    canShareAndRememberFuture[0] = canShareAndRememberFuture[0] + maxLeaked;
                }
                canShareAndRememberFuture[1] = canShareAndRememberFuture[1] + maxLeaked;
            }
            System.out.println("day,share=" + d + "," + peopleMap.get(d)[0] + ","
                + peopleMap.get(d)[1]);
        }
        long allLeaked = peopleMap.get(n)[1] % 1000000007;
        return (int) allLeaked;
    }

}


