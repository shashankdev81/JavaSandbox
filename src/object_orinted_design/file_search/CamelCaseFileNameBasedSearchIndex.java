package object_orinted_design.file_search;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

public class CamelCaseFileNameBasedSearchIndex {

    private FileSearchIndex index = new InMemoryHashTableIndex();

    public static void main(String[] args) {
        CamelCaseFileNameBasedSearchIndex index = new CamelCaseFileNameBasedSearchIndex();
        index.search("F");
//        index.search("FI");
//        index.search("FS");
//        index.search("FSA");
        index.reindex();
        index.search("F");

    }

    public CamelCaseFileNameBasedSearchIndex() {
        String[] strArray = new String[]{"FiniteStateMachine.java", "FileSumCalculator.java", "FirstSortAlgo.java",
                "FooBarClass.java"};
        final Random random = new Random();
        final int millisInDay = 24 * 60 * 60 * 1000;
        for (String file : strArray) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Time time = new Time((long) random.nextInt(millisInDay));
            index.index(new FileName(file, time.getTime(), time.getTime(), "Shashank"));
        }
    }

    public void search(String keyword) {
        System.out.println("------------Search results for " + keyword);
        for (String result : index.search(keyword)) {
            System.out.println(result);
        }

    }

    public void reindex() {
        index.reindex(SortType.CREATED_AT);
    }

    public class InMemoryHashTableIndex implements FileSearchIndex {

        private Set<FileName> files;

        private Map<String, Set<FileName>> index;

        private Comparator<FileName> sortStrategy;

        private SortType sortType = SortType.LEXICOGRAPHIC;

        public InMemoryHashTableIndex() {
            files = new HashSet<>();
            index = new HashMap<>();
            setSortStrategy(SortType.LEXICOGRAPHIC);
        }

        private void setSortStrategy(SortType sortType) {
            if (sortType.equals(SortType.LEXICOGRAPHIC)) {
                sortStrategy = new LexicographicSortStrategy();
            } else if (sortType.equals(SortType.CREATED_AT)) {
                sortStrategy = new CreatedAtSortStrategy();
            }
        }

        public InMemoryHashTableIndex(Comparator<String> sortStrategy) {
            index = new HashMap<>();
            sortStrategy = sortStrategy;
        }

        public void index(FileName fileName) {
            files.add(fileName);
            List<String> tokens = tokenise(fileName.getName());
            for (String token : tokens) {
                index.putIfAbsent(token, new TreeSet<FileName>(sortStrategy));
                index.get(token).add(fileName);
            }
        }

        @Override
        public void purge(String fileName) {

        }

        @Override
        public void update(String fileName) {

        }

        @Override
        public void load(String path) {

        }

        public Set<String> search(String key) {
            Set<String> result = new HashSet<>();
            if (index.containsKey(key)) {
                result = index.get(key).stream().map(s -> s.getName()).collect(Collectors.toSet());
            }
            return result;

        }

        @Override
        public void reindex(SortType sortType) {
            index = new HashMap<>();
            setSortStrategy(sortType);
            for (FileName file : files) {
                List<String> tokens = tokenise(file.getName());
                for (String token : tokens) {
                    index.putIfAbsent(token, new TreeSet<FileName>(sortStrategy));
                    index.get(token).add(file);
                }
            }
        }

        private List<String> tokenise(String fileName) {
            StringBuilder builder = new StringBuilder();
            List<String> tokens = new ArrayList<String>();
            for (char c : fileName.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    builder.append(c);
                    tokens.add(builder.toString());
                }
            }
            return tokens;
        }

    }

    private class LexicographicSortStrategy implements Comparator<FileName> {

        @Override
        public int compare(FileName o1, FileName o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    private class CreatedAtSortStrategy implements Comparator<FileName> {

        @Override
        public int compare(FileName o1, FileName o2) {
            return Long.compare(o1.getCreatedAt(), o2.getCreatedAt());
        }
    }
}
