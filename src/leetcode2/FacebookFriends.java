package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

class FacebookFriends {

    private Map<Integer, Integer> personToGroupMap = new LinkedHashMap<>();

    private Map<Integer, Set<Integer>> groupToPeopleMap = new LinkedHashMap<>();

    private int groupCount = 0;

    public static void main(String[] args) {
        FacebookFriends facebookFriends = new FacebookFriends();
        int time = facebookFriends.earliestAcq(new int[][]{{0, 0, 3}, {2, 0, 2}, {1, 3, 2}, {3, 3, 1}}, 4);
        //int time = facebookFriends.earliestAcq(new int[][]{{0, 2, 0}, {1, 0, 1}, {3, 0, 3}, {4, 1, 2}, {7, 3, 1}}, 4);
        System.out.println(time);
    }

    public int earliestAcq(int[][] logs, int n) {
        Arrays.sort(logs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        Set<Integer> uniquePeople = new HashSet<>();
        for (int i = 0; i < logs.length; i++) {
            uniquePeople.add(logs[i][1]);
            uniquePeople.add(logs[i][2]);
        }

        for (int i = 0; i < logs.length; i++) {
            if (personToGroupMap.get(logs[i][1]) != null && personToGroupMap.get(logs[i][2]) == null) {
                int group = personToGroupMap.get(logs[i][1]);
                groupToPeopleMap.get(group).add(logs[i][2]);
                personToGroupMap.put(logs[i][2], group);
            } else if (personToGroupMap.get(logs[i][1]) == null && personToGroupMap.get(logs[i][2]) != null) {
                int group = personToGroupMap.get(logs[i][2]);
                groupToPeopleMap.get(group).add(logs[i][1]);
                personToGroupMap.put(logs[i][1], group);
            } else if (personToGroupMap.get(logs[i][1]) == null && personToGroupMap.get(logs[i][2]) == null) {
                personToGroupMap.put(logs[i][1], groupCount);
                personToGroupMap.put(logs[i][2], groupCount);
                groupToPeopleMap.putIfAbsent(groupCount, new HashSet<>());
                groupToPeopleMap.get(groupCount).add(logs[i][1]);
                groupToPeopleMap.get(groupCount).add(logs[i][2]);
            } else if (personToGroupMap.get(logs[i][1]) == personToGroupMap.get(logs[i][2])) {
                //do Nothing
            } else {
                convergeGroups(logs, i);
            }
            if (groupToPeopleMap.size() == 1 && groupToPeopleMap.values().stream().findFirst().get().size() == uniquePeople.size()) {
                return logs[i][0];
            }
            groupCount++;
        }

        return -1;
    }


    private void convergeGroups(int[][] logs, int x) {
        int group1 = personToGroupMap.get(logs[x][1]);
        int group2 = personToGroupMap.get(logs[x][2]);
        Set<Integer> groupd2People = groupToPeopleMap.get(group2);
        groupToPeopleMap.get(group1).addAll(groupd2People);
        for (Integer person : groupd2People) {
            personToGroupMap.put(person, group1);
        }
        groupToPeopleMap.remove(group2);

    }
}