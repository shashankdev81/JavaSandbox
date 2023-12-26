import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import leetcode2.Employee;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;
import java.util.zip.Adler32;

public class Sandbox {

    private static final List<Integer> finalList = new ArrayList<>();

    private static String DIR = "/Users/shashank.balchandra/Documents/workspace/java/JavaSandbox/";

    public Sandbox() {

        Circle circle = new Circle(5);
        Square square = new Square(4);

        AreaCalculator areaCalculator = new AreaCalculator();
        PerimeterCalculator perimeterCalculator = new PerimeterCalculator();

        circle.accept(areaCalculator);
        square.accept(areaCalculator);

        circle.accept(perimeterCalculator);
        square.accept(perimeterCalculator);
        System.out.println("Total area: " + areaCalculator.getTotalArea());
        //System.out.println("Total perimeter: " + perimeterCalculator.getTotalPerimeter());
//        new Thread(new Writer(1, "TestFile.txt", 0)).start();
//        new Thread(new Writer(2, "TestFile.txt", 100000)).start();
//        new Thread(new Writer(3, "TestFile.txt", 200000)).start();
//        new Thread(new Writer(4, "TestFile.txt", 300000)).start();
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    private class Writer implements Runnable {

        private int writerId;

        private String fileName;

        private int offset;

        private long SIZE = 102400 * 1024;

        private RandomAccessFile randomAccessFile;

        public Writer(int writerId, String fileName, int offset) {
            this.writerId = writerId;
            this.fileName = fileName;
            this.offset = offset;
            try {
                randomAccessFile = new RandomAccessFile(DIR + fileName, "rw");
                randomAccessFile.setLength(SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void write(String content) throws IOException {
            randomAccessFile.seek(offset);
            randomAccessFile.write(content.getBytes(), 0, content.getBytes().length);
            offset += content.length();
        }


        @Override
        public void run() {
            while (true) {
                try {
                    write("Some random stuff " + writerId);
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<Integer> merge(List<Integer> list1, List<Integer> list2) {
        List<Integer> merged = new ArrayList<>();
        if (list1.isEmpty() && list2.isEmpty()) {
            return merged;
        } else if (list1.isEmpty()) {
            return list2;
        } else if (list2.isEmpty()) {
            return list1;
        }
        List<Integer> removed = list1.get(0) <= list2.get(0) ?
            list1 : list2;
        List<Integer> notRemoved = list1.get(0) <= list2.get(0) ? list2 : list1;
        merged.add(removed.get(0));
        merged.addAll(merge(removed.subList(1, removed.size()), notRemoved));
        return merged;
    }

    public static void main(String[] args) {
        //Sandbox sandbox = new Sandbox();
        int col = 'A'-'A' + 1;

        System.out.println(col);
    }

    public static void main1(String[] args) throws UnsupportedEncodingException {
         List<String> visited = new ArrayList<>();

        //Sandbox sandbox = new Sandbox();
        //ExecutorService service =  Executors.newFixedThreadPool(100);
        CompletableFuture<Double> outcome = CompletableFuture.supplyAsync(() -> {
            return doSomething();
        }).thenApplyAsync(result -> {
            result.res = result.x * result.x + result.y * result.y;
            return result;
        }).thenApplyAsync(result -> {
                return Math.sqrt(result.res);
            }
        );
        try {
            System.out.println(outcome.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
        }
        //.thenAccept(result -> System.out.println(result));

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            return "Result1";
        });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            return "Result2";
        });
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
            return "Result3";
        });
        CompletableFuture.allOf(f1, f2, f3).thenRun(() -> {
            System.out.println(f1.join() + "," + f2.join() + "," + f3.join());
        });

        CompletableFuture<String> originalFuture = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Integer> composedFuture = originalFuture.thenComposeAsync(result -> {
            // Asynchronous function that returns a new CompletableFuture
            return CompletableFuture.supplyAsync(() -> result.length());
        });

        CompletableFuture<CompletableFuture<Integer>> x = originalFuture.thenApplyAsync(result -> {
            CompletableFuture<Integer> intermediate = CompletableFuture.supplyAsync(
                () -> result.length());
            return intermediate;
        });

    }

    private static Result doSomething() {
        return new Result(3, 4);
    }

    private static class Result {

        int x;
        int y;
        int res;

        public Result(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main2(String[] args) throws UnsupportedEncodingException {
        List<Integer> one = new ArrayList<>(
            Arrays.stream(new Integer[]{1, 2, 3}).collect(Collectors.toList()));
        List<Integer> two = new ArrayList<>(
            Arrays.stream(new Integer[]{4, 6, 8}).collect(Collectors.toList()));
        List<Integer> three = new ArrayList<>(
            Arrays.stream(new Integer[]{10, 15, 20}).collect(Collectors.toList()));
        List<Integer> finalList = merge(three, merge(one, two));
        //test();
        //dump2();
        TreeMap<String, List<Integer>> diffMap = new TreeMap<>();
        for (int i = 0; i < 10; i++) {
            diffMap.merge("Shashank", new ArrayList<>(i), (v1, v2) -> {
                List<Integer> merged = new ArrayList<>(v1);
                merged.addAll(v2);
                return merged;
            });
        }
        System.out.println(diffMap);
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
        List<Double> powers = Arrays.asList(
                new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
            .stream().mapToDouble(i -> i.doubleValue()).map(d -> Math.pow(2, d)).boxed()
            .collect(Collectors.toList());

        Map<Integer, Integer> isPowerMap = new HashMap<>();

        for (int i = 0; i < 20; i++) {
            isPowerMap.put(i, Double.valueOf(Math.pow(2, i)).intValue());
        }
        System.out.println(ceil);
        System.out.println(root);

        ExecutorService service = Executors.newFixedThreadPool(100);
        System.out.println(
            Arrays.asList(new String[]{"cat", "dog", "cat", "dog", "rat", "bat"}).stream()
                .collect(Collectors.groupingBy(s -> s)).values());
        Map<Integer, Set<Integer>> userToLangMap = new HashMap<>();
        userToLangMap.entrySet().stream().flatMap(e -> e.getValue().stream()
                .map(v -> new AbstractMap.SimpleEntry(v, e.getKey())))
            .collect(Collectors.groupingBy(Map.Entry::getKey,
                Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));
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
        Map<String, List<Student>> students = studlist.stream()
            .collect(Collectors.groupingBy(s -> s.stud_location));
        System.out.println(students);
        Collection<String> res = studlist.stream().map(s -> s.stud_name)
            .collect(Collectors.toCollection(ArrayList::new));
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

        Employee firstEmpMeetsCriteria = Arrays.asList(arrayOfEmps).stream()
            .filter(e -> e.salary > 200000.0).findFirst()
            .orElseGet(() -> new Employee(-1, "None", 0));
        Arrays.asList(arrayOfEmps).stream().filter(e -> e.salary > 200000.0)
            .toArray(Employee[]::new);

        List<List<String>> namesNested = Arrays.asList(
            Arrays.asList("Jeff", "Bezos"),
            Arrays.asList("Bill", "Gates"),
            Arrays.asList("Mark", "Zuckerberg"));
        System.out.println(namesNested.stream().flatMap(e -> e.stream()).peek(e -> e = "Mr. " + e)
            .collect(Collectors.toList()));
        System.out.println(Arrays.asList(arrayOfEmps).stream().peek(employee -> {
            employee.salary += 1000;
            employee.name = "Mr. " + employee.name;
        }).filter(e -> e.name.length() > 20).collect(Collectors.toList()));
    }

    private void write(String content, String fileName, int offset) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DIR + fileName));
        Reader reader = new BufferedReader(new FileReader(DIR + fileName));
        FileWriter fileWriter = new FileWriter("");
        fileWriter.write(content, 1000, content.getBytes().length);
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
        Map<Integer, Integer> sorted1 = unsorted.entrySet().stream()
            .sorted(Map.Entry.comparingByKey()).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new));
        Map<Integer, Integer> sorted2 = unsorted.entrySet().stream()
            .sorted(Map.Entry.comparingByValue()).collect(
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
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
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

    // Element interface
    interface Shape {

        void accept(Visitor visitor);
    }

    // Concrete Element - Circle
    class Circle implements Shape {

        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        public double getRadius() {
            return radius;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    // Concrete Element - Square
    class Square implements Shape {

        private double side;

        public Square(double side) {
            this.side = side;
        }

        public double getSide() {
            return side;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    // Visitor interface
    interface Visitor {

        void visit(Circle circle);

        void visit(Square square);
    }

    // Concrete Visitor - AreaCalculator
    class AreaCalculator implements Visitor {

        double totalArea = 0;

        @Override
        public void visit(Circle circle) {
            // Calculate area for a circle
            double area = Math.PI * circle.getRadius() * circle.getRadius();
            totalArea += area;
        }

        @Override
        public void visit(Square square) {
            // Calculate area for a square
            double area = square.getSide() * square.getSide();
            totalArea += area;
        }

        public double getTotalArea() {
            return totalArea;
        }
    }

    // Concrete Visitor - PerimeterCalculator
    class PerimeterCalculator implements Visitor {

        double totalPerimeter = 0;

        @Override
        public void visit(Circle circle) {
            // Calculate perimeter for a circle
            double perimeter = 2 * Math.PI * circle.getRadius();
            totalPerimeter += perimeter;
        }

        @Override
        public void visit(Square square) {
            // Calculate perimeter for a square
            double perimeter = 4 * square.getSide();
            totalPerimeter += perimeter;
        }

        public double getTotalPerimeter() {
            return totalPerimeter;
        }
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
