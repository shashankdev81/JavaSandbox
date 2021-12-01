package leetcode;

import java.util.Stack;

public class LongestFilePathSimpler {
    public int lengthLongestPath(String input) {
        String[] lines = input.split("\n");
        Stack<Integer> directories = new Stack<Integer>();
        Stack<String> path = new Stack<String>();
        int maxLengthSoFar = -1;
        int currPathLength = 0;
        for (int i = 0; i < lines.length; ) {
            String dirOrFile = lines[i];
            int level = indents(dirOrFile);
            String trimmedDirOrFile = dirOrFile.replaceAll("\t", "");
            if (isFile(trimmedDirOrFile)) {
                maxLengthSoFar = Math.max(maxLengthSoFar, currPathLength + trimmedDirOrFile.length() + 1);
                i++;
                continue;
            }
            if (directories.isEmpty() || level > directories.peek()) {
                directories.add(level);
                path.add(trimmedDirOrFile);
                currPathLength += path.peek().length() + 1;
                i++;
            } else {
                while (!directories.isEmpty() && directories.peek() >= level) {
                    directories.pop();
                    String top = path.pop();
                    currPathLength = currPathLength - (top.length() + 1);
                }
            }
        }

        return maxLengthSoFar == -1 ? 0 : maxLengthSoFar - 1;

    }

    private boolean isFile(String dirOrFile) {
        return dirOrFile.contains(".");
    }

    private int indents(String str) {
        if (!str.startsWith("\t")) {
            return 0;
        }
        return 1 + indents(str.substring(1, str.length()));
    }

    public static void main(String[] args) {
        LongestFilePathSimpler longestFilePath = new LongestFilePathSimpler();
        System.out.println(longestFilePath.lengthLongestPath("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"));
        System.out.println(longestFilePath.lengthLongestPath("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"));
        System.out.println(longestFilePath.lengthLongestPath("a"));
        System.out.println(longestFilePath.lengthLongestPath("dir\n        file.txt"));
        System.out.println(longestFilePath.lengthLongestPath("file1.txt\nfile2.txt\nlongfile.txt"));
    }

}
