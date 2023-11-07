package leetcode2;


import java.util.*;
import java.util.stream.Collectors;

class VideosWatchedFriends {


    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        Map<Integer, Person> friendsMap = new HashMap<>();
        //o(n*v)
        for (int i = 0; i < friends.length; i++) {
            friendsMap.putIfAbsent(i, new Person(i));
            friendsMap.get(i).videos.addAll(watchedVideos.get(i));
        }
        //o(n*n)
        for (int i = 0; i < friends.length; i++) {
            for (int j = 0; j < friends[i].length; j++) {
                friendsMap.get(i).friends.add(friendsMap.get(friends[i][j]));
            }
        }
        List<String> results = new ArrayList<>();
        if (level != 0) {
            Person person = friendsMap.get(id);
            //o(n*v)
            Map<String, Integer> videos = person.getVideosWatched(level, person.id, new HashSet<>());
            //o(v)
            Map<Integer, List<String>> videosReverseIndex = videos.entrySet().stream().collect(Collectors.groupingBy(e -> e.getValue(), Collectors.mapping(e -> e.getKey(), Collectors.toList())));
            //o(vlogv)
            Map<Integer, List<String>> videosSortedReverseIndex = new TreeMap<>(videosReverseIndex);
            //o(v)
            results = videosSortedReverseIndex.entrySet().stream().flatMap(e -> e.getValue().stream()).sorted().collect(Collectors.toList());
        }
        return results;
    }

    public class Person {
        int id;
        List<String> videos;
        List<Person> friends;

        public Person(int idVal) {
            id = idVal;
            videos = new ArrayList<>();
            friends = new ArrayList<>();
        }

        public boolean equals(Object obj) {
            if (obj == null || !getClass().equals(obj.getClass())) {
                return false;
            }
            Person ext = (Person) obj;
            return id == ext.id;
        }

        public int hashCode() {
            return Objects.hash(id);
        }

        public Map<String, Integer> getVideosWatched(int level, int exclude, Set<String> visited) {
            Map<String, Integer> videosWatched = new TreeMap<>();

            if (visited.contains(id)) {
                return videosWatched;
            }
            if (level > 0) {
                for (Person friend : friends) {
                    if (friend.id == exclude) {
                        continue;
                    }
                    visited.add("" + friend.id);
                    Map<String, Integer> videosMap = friend.getVideosWatched(level - 1, id, visited);
                    for (Map.Entry<String, Integer> video : videosMap.entrySet()) {
                        String vid = video.getKey();
                        videosWatched.putIfAbsent(vid, 0);
                        videosWatched.put(vid, videosWatched.get(vid) + video.getValue());
                    }
                }
            } else {
                for (String vid : videos) {
                    videosWatched.put(vid, 1);
                }
            }
            return videosWatched;
        }
    }
}


//0 -> 1,2 {A,B}
//1 -> 0,3 {C}
//2 -> 0,3 {B,C}
//3 -> 1,2 {D}