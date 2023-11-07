package leetcode2;

import java.util.*;

public class BikeTaxi {

    static int innerCount;

    public static void main(String[] args) {
        BikeTaxi bikeTaxi = new BikeTaxi();
//        long then = System.currentTimeMillis();
//        System.out.println(bikeTaxi.assignBikes(new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}, {6, 0}, {7, 0}, {8, 0}, {9, 0}}
//                , new int[][]{{0, 999}, {1, 999}, {2, 999}, {3, 999}, {4, 999}, {5, 999}, {6, 999}, {7, 999}, {8, 999}, {9, 999}}));
//        long now = System.currentTimeMillis();
//        System.out.println(now - then);
//
//        System.out.println(bikeTaxi.assignBikes(new int[][]{{0, 0}, {2, 1}}, new int[][]{{1, 2}, {3, 3}}));
        System.out.println(bikeTaxi.assignBikes(new int[][]{{0, 0}, {1, 1}, {2, 0}, {2, 2}, {3, 0}}, new int[][]{{1, 0}, {2, 2}, {2, 1}}));
        System.out.println(innerCount);
    }

    public int assignBikes(int[][] workers, int[][] bikes) {
        int cost = 0;
        List<Worker> workerList = new ArrayList<>();
        List<Bike> bikeList = new ArrayList<>();
        for (int i = 0; i < workers.length; i++) {
            workerList.add(new Worker(i, workers[i][0], workers[i][1]));
        }
        for (int j = 0; j < bikes.length; j++) {
            bikeList.add(new Bike(j, bikes[j][0], bikes[j][1]));
        }
        cost = iterate(workerList, bikeList, 0, new HashMap<>());
        return cost;
    }

    private int iterate(List<Worker> workerList, List<Bike> bikeList, int costSoFar, Map<String, Integer> costMap) {
        if (workerList.isEmpty() || bikeList.isEmpty()) {
            return 0;
        }
        int cost = Integer.MAX_VALUE;
        for (Bike bike : bikeList) {
            if (bike.isVisited) {
                continue;
            }
            int bikeCost = 0;
//            String key1 = bike.id + "-" + bikeList.stream().filter(w -> !w.isVisited).map(w -> w.id).collect(Collectors.toList());
//            String key2 = "-" + workerList.stream().filter(w -> !w.isVisited).map(w -> w.id).collect(Collectors.toList());
//            if (costMap.containsKey(key1 + key2)) {
//                bikeCost = costMap.get(key1 + key2);
//            } else {
//                bikeCost = assign(workerList, bikeList, bike, costSoFar, costMap);
//                costMap.put(key1 + key2, bikeCost);
//            }
            bikeCost = assign(workerList, bikeList, bike, costSoFar, costMap);
            cost = Math.min(cost, bikeCost);
        }
        return cost == Integer.MAX_VALUE ? 0 : cost;
    }

    private int assign(List<Worker> workerList, List<Bike> bikeList, Bike bike, int costSoFar, Map<String, Integer> costMap) {
        if (workerList.isEmpty() || bikeList.isEmpty()) {
            return costSoFar;
        }
        int cost = Integer.MAX_VALUE;
        Worker assigned = null;
        for (Worker worker : workerList) {
            innerCount++;
            if (worker.isVisited) {
                continue;
            }
            int dist = Math.abs(worker.x - bike.x) + Math.abs(worker.y - bike.y);
            if (cost > dist) {
                cost = dist;
                assigned = worker;
            }
        }
        if (assigned != null) {
            bike.isVisited = true;
            assigned.isVisited = true;
            cost += iterate(workerList, bikeList, costSoFar + cost, costMap);
            bike.isVisited = false;
            assigned.isVisited = false;
        }
        return cost == Integer.MAX_VALUE ? 0 : cost;
    }


    public class Worker {
        int id;
        int x;
        int y;
        boolean isVisited;

        public Worker(int index, int i, int j) {
            id = index;
            x = i;
            y = j;
        }
    }

    public class Bike {
        int id;
        int x;
        int y;
        boolean isVisited;

        public Bike(int index, int i, int j) {
            id = index;
            x = i;
            y = j;
        }
    }
}