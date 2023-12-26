package leetcode2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FileSystem {

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        System.out.println(fileSystem.ls("/"));
        fileSystem.mkdir("/a/b/c");
        fileSystem.addContentToFile("/a/b/c/d", "hello");
        System.out.println(fileSystem.ls("/"));
        System.out.println(fileSystem.readContentFromFile("/a/b/c/d"));

    }

    private Map<String, StringBuilder> files;

    private Map<String, FileSystem> filesAndDirMap;


    public FileSystem() {
        this.files = new TreeMap<>();
        this.filesAndDirMap = new TreeMap<>();
    }

    public List<String> ls(String path) {
        String[] pathArr = path.substring(1, path.length()).split("/");
        if (pathArr[0].length() == 0) {
            return getFilesAndDirs(pathArr, 1);
        } else {
            return getFilesAndDirs(pathArr, 0);
        }
    }

    private List<String> getFilesAndDirs(String[] parts, int idx) {
        List<String> results = new ArrayList<>();
        if (idx == parts.length) {
            results.addAll(files.keySet());
            results.addAll(filesAndDirMap.keySet());
            results.stream().sorted().collect(Collectors.toList());
        } else if (!filesAndDirMap.containsKey(parts[idx])) {
            results.addAll(files.keySet());
        } else {
            List<Integer> suffixMatches = new ArrayList<>();
            List<Integer> prefixMatches = new ArrayList<>();
            prefixMatches.stream().filter(e -> suffixMatches.contains(e)).collect(Collectors.toList());
            results = filesAndDirMap.get(parts[idx]).getFilesAndDirs(parts, idx + 1);
        }
        return results;
    }

    public void mkdir(String path) {
        String[] pathArr = path.substring(1, path.length()).split("/");
        if (pathArr != null && pathArr.length > 0) {
            make(pathArr, 0);
        }

    }

    private void make(String[] pathArr, int idx) {
        if (idx == pathArr.length) {
            return;
        }
        filesAndDirMap.putIfAbsent(pathArr[idx], new FileSystem());
        filesAndDirMap.get(pathArr[idx]).make(pathArr, idx + 1);
    }

    public void addContentToFile(String filePath, String content) {
        String[] pathArr = filePath.substring(1, filePath.length()).split("/");
        if (pathArr != null && pathArr.length > 0) {
            write(pathArr, 0, content);
        }
    }

    private void write(String[] pathArr, int idx, String content) {
        if (idx == pathArr.length - 1) {
            files.putIfAbsent(pathArr[idx], new StringBuilder());
            files.get(pathArr[idx]).append(content);
            return;
        }
        filesAndDirMap.get(pathArr[idx]).write(pathArr, idx + 1, content);
    }

    public String readContentFromFile(String filePath) {
        String[] pathArr = filePath.substring(1, filePath.length()).split("/");
        if (pathArr == null || pathArr.length == 0) {
            return "";
        } else {
            return read(pathArr, 0);
        }
    }

    private String read(String[] pathArr, int idx) {
        if (idx == pathArr.length - 1) {
            return files.get(pathArr[idx]).toString();
        }
        return filesAndDirMap.get(pathArr[idx]).read(pathArr, idx + 1);
    }
}
