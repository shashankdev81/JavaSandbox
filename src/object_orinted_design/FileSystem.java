package object_orinted_design;

import java.util.HashMap;
import java.util.Map;

public class FileSystem {


    private FSNode root = new FSNode();

    public FileSystem() {

    }

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        System.out.println(fileSystem.createPath("/leet", 1));
        System.out.println(fileSystem.createPath("/leet/code", 1));
        System.out.println(fileSystem.get("/leet/code"));
        System.out.println(fileSystem.createPath("/c/d", 1));
        System.out.println(fileSystem.get("/c"));

    }

    private String sanitise(String path) {
        if (path == null || path.isEmpty() || path.length() <= 1 || path.charAt(0) != '/') {
            return null;
        }
        return path.substring(1);
    }

    public boolean createPath(String path, int value) {
        String santisedPath = sanitise(path);
        if (santisedPath == null) {
            return false;
        }
        return root.add(santisedPath.split("/"), 0, value);
    }

    public int get(String path) {
        String santisedPath = sanitise(path);
        if (santisedPath == null) {
            return -1;
        }
        return root.get(santisedPath.split("/"), 0);
    }

    public class FSNode {

        int value;
        private Map<String, FSNode> fsMap = new HashMap<>();

        public boolean add(String[] parts, int idx, int value) {
            if (idx == parts.length) {
                this.value = value;
                return true;
            }
            if (idx < parts.length - 1 && !this.fsMap.containsKey(parts[idx])) {
                return false;
            } else {
                this.fsMap.putIfAbsent(parts[idx], new FSNode());
            }
            //System.out.println(parts[idx] + "," + idx + "," + this.fsMap.containsKey(parts[idx]));
            return this.fsMap.get(parts[idx]).add(parts, idx + 1, value);

        }

        public int get(String[] parts, int idx) {
            if (idx == parts.length) {
                return this.value;
            }
            //System.out.println(parts[idx] + "," + idx + "," + this.fsMap.containsKey(parts[idx]));
            if (!this.fsMap.containsKey(parts[idx])) {
                return -1;
            }
            return this.fsMap.get(parts[idx]).get(parts, idx + 1);

        }
    }
}

/**
 * Your FileSystem object will be instantiated and called as such: FileSystem obj = new
 * FileSystem(); boolean param_1 = obj.createPath(path,value); int param_2 = obj.get(path);
 */