package object_orinted_design.multi_level_cache;

public class MainClass {

    public static void main(String[] args) {
        //TODO builder pattern?
        MultiLevelCache cache = new MultiLevelCache(5, new int[]{10, 20, 35, 60, 100}, new int[]{1, 2, 34, 5}, new int[]{1, 2, 34, 5});
        Result result = null;
        addAndWrite(cache, "Dummy", "1");
        addAndWrite(cache, "A", "1");
        addAndWrite(cache, "B", "2");
        getAndWrite(cache, "A");
        addAndWrite(cache, "A", "3");
        addAndWrite(cache, "B", "4");
        getAndWrite(cache, "B");
        addAndWrite(cache, "C", "1");
        addAndWrite(cache, "D", "2");
        getAndWrite(cache, "C");
        addAndWrite(cache, "E", "3");
        addAndWrite(cache, "F", "4");
        getAndWrite(cache, "E");

    }

    private static void addAndWrite(MultiLevelCache cache, String key, String value) {
        Result result;
        result = cache.add(new Key(key), value);
        System.out.println("add " + key + "=" + result.getValue() + ", time=" + result.getTime());
    }

    private static void getAndWrite(MultiLevelCache cache, String key) {
        Result result;
        result = cache.get(new Key(key));
        System.out.println("get " + key + "=" + result.getValue() + ", time=" + result.getTime());
    }
}
