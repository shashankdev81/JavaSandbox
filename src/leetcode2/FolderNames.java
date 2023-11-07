package leetcode2;

import java.util.*;

public class FolderNames {


    public static void main(String[] args) {
        FolderNames folderNames = new FolderNames();
        System.out.println(folderNames.getFolderNames(new String[]{"kaido", "kaido(1)", "kaido", "kaido(1)", "kaido(2)"}));

    }

    private Map<String, Map<Integer, Integer>> folders = new HashMap<>();

    private Set<String> taken = new HashSet<>();

    public String[] getFolderNames(String[] names) {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            int suffix = getSuffix(name);
            folders.putIfAbsent(name, new HashMap<>());
            String newFolder = suffix == 0 ? name : name + "(" + suffix + ")";
            while (taken.contains(newFolder)) {
                suffix++;
                newFolder = name + "(" + suffix + ")";
            }
            taken.add(newFolder);
            folders.get(name).put(i, suffix);
        }
        List<String> folderList = new LinkedList<>();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            int index = folders.get(name).get(i);
            String folderName = index == 0 ? name : name + "(" + folders.get(name).get(i) + ")";
            folderList.add(folderName);
        }

        return folderList.toArray(new String[]{});
    }

    private int getSuffix(String name) {
        if (!folders.containsKey(name)) {
            return 0;
        } else {
            return 1 + new LinkedList<Integer>(folders.get(name).values()).get(folders.get(name).values().size() - 1);
        }
    }
}