import leetcode2.Employee;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;
import java.util.zip.Adler32;

public class Sandbox {


    public static void main(String[] args) throws UnsupportedEncodingException {
        //test();
        //dump2();
        TreeSet<String> names = new TreeSet<>();
        names.add("Divya");
        names.add("Raj");
        names.add("Shashank");
        names.add("Shastri");
        names.add("Shanker");
        names.add("Shahrukh");
        names.add("Z1");
        names.add("Z2");
        names.add("Z3");

        String prefix = "Shas";
        char[] charArr = prefix.toCharArray();
        charArr[0] = ++charArr[0];
        String prefix2 = new String(charArr);
        System.out.println(prefix);
        System.out.println(prefix2);
        SortedSet<String> res1 = names.tailSet(prefix);
        //SortedSet<String> res1 = new TreeSet<>(names.tailSet(prefix));
        SortedSet<String> res2 = names.tailSet(prefix2);
        System.out.println(res1);
        System.out.println(res2);
        res1.removeAll(res2);
        System.out.println(res1);


        boolean isPowerOfTwo = Math.ceil(Math.log(4)) == Math.floor(Math.log(4));
        double ceil = Math.pow(2, 20);
        double root = Math.log(4);
        List<Double> powers = Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20}).stream()
                .mapToDouble(i -> i.doubleValue()).map(d -> Math.pow(2, d)).boxed().collect(Collectors.toList());

        Map<Integer, Integer> isPowerMap = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            isPowerMap.put(i, Double.valueOf(Math.pow(2, i)).intValue());
        }
        System.out.println(ceil);
        System.out.println(root);

        ExecutorService service = Executors.newFixedThreadPool(100);
        System.out.println(Arrays.asList(new String[]{"cat", "dog", "cat", "dog", "rat", "bat"}).stream().collect(Collectors.groupingBy(s -> s)).values());
        Map<Integer, Set<Integer>> userToLangMap = new HashMap<>();
        userToLangMap.entrySet().stream().flatMap(e -> e.getValue().stream()
                        .map(v -> new AbstractMap.SimpleEntry(v, e.getKey())))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));
        List<Integer> toTeach = new ArrayList<>();
        toTeach.clear();
        List<Student> studlist = new ArrayList<Student>();
        studlist.add(new Student("1726", "John", "New York"));
        studlist.add(new Student("4321", "Max", "California"));
        studlist.add(new Student("2234", "Andrew", "Los Angeles"));
        studlist.add(new Student("3743", "Michael", "New York"));
        studlist.add(new Student("5223", "Michael", "New York"));
        studlist.add(new Student("7765", "Sam", "California"));
        studlist.add(new Student("3442", "Mark", "New York"));
        studlist.add(new Student("3443", "Mark", "New York"));
        Map<String, List<Student>> students = studlist.stream().collect(Collectors.groupingBy(s -> s.stud_location));
        System.out.println(students);
        Collection<String> res = studlist.stream().map(s -> s.stud_name).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(res);

        Employee[] arrayOfEmps = {
                new Employee(1, "Jeff Bezos", 100000.0),
                new Employee(2, "Bill Gates", 200000.0),
                new Employee(3, "Mark Zuckerberg", 300000.0)
        };

        Arrays.asList(arrayOfEmps).stream().forEach(employee -> {
            employee.salary += 1000;
            employee.name = "Mr. " + employee.name;
        });
        Arrays.asList(arrayOfEmps).stream().forEach(e -> {
            System.out.println(e);
        });

        Employee firstEmpMeetsCriteria = Arrays.asList(arrayOfEmps).stream().filter(e -> e.salary > 200000.0).findFirst()
                .orElseGet(() -> new Employee(-1, "None", 0));
        Arrays.asList(arrayOfEmps).stream().filter(e -> e.salary > 200000.0).toArray(Employee[]::new);

        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Mark", "Zuckerberg"));
        System.out.println(namesNested.stream().flatMap(e -> e.stream()).peek(e -> e = "Mr. " + e).collect(Collectors.toList()));
        System.out.println(Arrays.asList(arrayOfEmps).stream().peek(employee -> {
            employee.salary += 1000;
            employee.name = "Mr. " + employee.name;
        }).filter(e -> e.name.length() > 20).collect(Collectors.toList()));
    }


    private static void dump2() {
        int x = Collections.binarySearch(Arrays.asList(new Integer[]{1, 2, 5, 6}).subList(0, 1), 3);
        System.out.println(x);
//        int[] arr = new int[]{4, 9, 3};
//        final AtomicInteger val = new AtomicInteger(2);
//        while (val.get() < 10) {
//            int max = Arrays.stream(arr).map(i -> i > val.get() ? val.get() : i * 2).sum();
//            int sum1 = Arrays.stream(arr).map(i -> (i > val.get() ? val.get() : i)).sum();
//            val.incrementAndGet();
//        }
//        TreeMap<Integer, Map<String, Integer>> secondLevelAgg  = new TreeMap<>();
//        String[] arr1 = "test".split(" ");
//        List<String> folderList = new LinkedList<>();
//        folderList.toArray(new String[]{});
//        Map<String, LinkedHashMap<Integer, Integer>> folders = new HashMap<>();
//        new LinkedList<Integer>(folders.get("1").values()).get(folders.get("1").values().size()-1);
//        secondLevelAgg.subMap(10,20).values().stream().map(m -> m.get("tweetName")).mapToInt(i -> i.intValue()).sum();
//        List<Integer> list1 = new ArrayList<>();
//        System.out.println(list1.stream().reduce((i1, i2) -> i1 + i2).orElseGet(() -> 0));
//        String name = "Shashank Baravani is a good person";
//        System.out.println(name.chars().mapToObj(c -> (char) c)
//                .filter(c -> c != ' ')
//                .map(c -> "|" + c).reduce((s, s2) -> s + s2).get());
//        int sum = Arrays.asList(name.split(" ")).stream().map(s -> 1).reduce((i1, i2) -> i1 + i2).get();
//        List<List<Integer>> list = new ArrayList<>();
//        list.add(Arrays.asList(new Integer[]{1, 2, 3}));
//        list.add(Arrays.asList(new Integer[]{4, 5, 6}));
//        list.add(Arrays.asList(new Integer[]{7, 8, 9}));
//        List<Integer> num = list.stream().flatMap(integers -> integers.stream()).map(i -> i * 2).collect(Collectors.toList());
//
//        System.out.println(num);
//        MyfucnctionalInterface func1 = () -> {
//            System.out.println("Test");
//        };
//        Set<Integer> reachables = new HashSet<>();
//        reachables.stream().map(i -> i).collect(Collectors.toList());
//        test(func1);
    }

    @FunctionalInterface
    interface MyfucnctionalInterface {
        void doThis();
    }

    private static void test(MyfucnctionalInterface func1) {
        func1.doThis();
        Map<Integer, Integer> unsorted = new HashMap<>();
        unsorted.put(6, 4);
        unsorted.put(1, 20);
        Map<Integer, Integer> sorted1 = unsorted.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        Map<Integer, Integer> sorted2 = unsorted.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));

//        System.out.println(sorted1.entrySet());
//        System.out.println(sorted1.values());
//
//        System.out.println(sorted2.entrySet());
//        System.out.println(sorted2.values());

        TreeSet<Integer> treeSet = new TreeSet<>();
        Set<Cell> set = new HashSet<>();
        set.add(new Cell(6, 10));
        set.add(new Cell(2, 20));
        set.add(new Cell(4, 15));
        List<Cell> list = new ArrayList<>(set);
        Collections.sort(list, new CellKeyComparator());
        System.out.println(list);
        Collections.sort(list, new CellValueComparator());
        System.out.println(list);

        Map<String, TreeMap<Integer, String>> map = new HashMap<>();
        map.putIfAbsent("1", new TreeMap<>(Comparator.reverseOrder()));
        Stack<Integer> stack = new Stack<>();
        TreeSet<Integer> sorted = new TreeSet<>();
    }

    static class Cell {
        int key;
        int val;

        public Cell(int i, int j) {
            this.key = i;
            this.val = j;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return key == cell.key;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "key=" + key +
                    ", val=" + val +
                    '}';
        }
    }

    static class CellKeyComparator implements Comparator<Cell> {


        @Override
        public int compare(Cell o1, Cell o2) {
            return Integer.compare(o1.key, o2.key);
        }
    }

    static class CellValueComparator implements Comparator<Cell> {


        @Override
        public int compare(Cell o1, Cell o2) {
            return Integer.compare(o1.val, o2.val);
        }
    }

    public void dump() {

        String input = String.valueOf(System.currentTimeMillis());

        Adler32 adler32 = new Adler32();

        // Update the checksum with the data bytes
        adler32.update(input.getBytes());
        Queue<Integer> unused = new PriorityBlockingQueue<>();
        List<Integer> results = new ArrayList();
        Collections.sort(results);
        Arrays.asList(unused.toArray());
        // Get the Adler-32 checksum value
        long checksumValue1 = adler32.getValue();
        int checksumValue2 = (int) adler32.getValue();
        //byte[] intBytes = ByteBuffer.allocate(4).putInt(checksumValue2).array();
        List<Integer> x = new ArrayList<>();
        System.out.println("Adler-32 Checksum: " + String.valueOf(checksumValue1));
        System.out.println("Adler-32 Checksum: " + String.valueOf(checksumValue2));
        System.out.println("Encoded String: " + encodeToAlphanumeric(checksumValue2));
        Queue<Map.Entry<Integer, Integer>> q = new PriorityQueue(10, new ElementComparator());
        Map<Integer, Integer> map = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            q.offer(entry);
        }
        while (!q.isEmpty()) {
            System.out.println(q.poll().getValue());
        }
        //int[] arr = q.stream().map(e -> e.getKey()).sorted().sequential().limit(k).mapToInt(v -> v.intValue()).toArray();

    }

    public static class ElementComparator implements Comparator<Map.Entry<Integer, Integer>> {

        @Override
        public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
            return Integer.compare(o1.getValue(), o2.getValue());
        }
    }

    // Custom method to encode an integer as an alphanumeric string
    public static String encodeToAlphanumeric(int value) {
        // Define a character set containing alphanumeric characters
        String charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        // Calculate the base of the character set
        int base = charset.length();

        // Initialize a StringBuilder to build the encoded string
        StringBuilder encoded = new StringBuilder();

        // Perform custom encoding
        while (value > 0) {
            int remainder = value % base;
            encoded.insert(0, charset.charAt(remainder));
            value = value / base;
        }

        return encoded.toString();
    }

}

class Student {
    String stud_id;
    String stud_name;
    String stud_location;

    Student(String sid, String sname, String slocation) {
        this.stud_id = sid;
        this.stud_name = sname;
        this.stud_location = slocation;
    }
}
