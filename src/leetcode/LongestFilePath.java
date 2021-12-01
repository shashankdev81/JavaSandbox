package leetcode;

import java.util.Stack;

public class LongestFilePath {
    public int lengthLongestPath(String input) {
        String[] lines = input.split("\n");
        Stack<Integer> directories = new Stack<Integer>();
        Stack<String> path = new Stack<String>();
        int maxLengthSoFar = -1;
        int currLength = 0;
        for (String dirOrFile : lines) {
            int level = indents(dirOrFile);
            String trimmedDirOrFile = dirOrFile.replaceAll("\t", "").trim();
            if (directories.isEmpty() || level > directories.peek()) {
                directories.add(level);
                path.add(trimmedDirOrFile);
                currLength += path.peek().length() + 1;
                if (dirOrFile.contains(".")) {
                    maxLengthSoFar = Math.max(maxLengthSoFar, currLength);
                }
            } else if (level == directories.peek()) {
                if (dirOrFile.contains(".")) {
                    maxLengthSoFar = Math.max(maxLengthSoFar, currLength + trimmedDirOrFile.length() + 1);
                } else {
                    String top = path.pop();
                    path.add(trimmedDirOrFile);
                    directories.pop();
                    directories.add(level);
                    if (top.length() < path.peek().length() && dirOrFile.contains(".")) {
                        maxLengthSoFar += path.peek().length() - top.length();
                    }
                    currLength = currLength - top.length() + path.peek().length();
                }
            } else {
                while (!directories.isEmpty() && directories.peek() > level) {
                    directories.pop();
                    String top = path.pop();
                    currLength = currLength - (top.length() + 1);
                }

                if (level == directories.peek()) {
                    String top = path.pop();
                    path.add(dirOrFile.replaceAll("\t", ""));
                    directories.pop();
                    directories.add(level);
                    currLength = currLength - top.length() + path.peek().length();
                }

                directories.add(level);
            }
        }

        return maxLengthSoFar == -1 ? 0 : maxLengthSoFar - 1;

    }

    private int indents(String str) {
        if (!str.startsWith("\t")) {
            return 0;
        }
        return 1 + indents(str.substring(1, str.length()));
    }

    public static void main(String[] args) {
        LongestFilePath longestFilePath = new LongestFilePath();
        System.out.println(longestFilePath.lengthLongestPath("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"));
        System.out.println(longestFilePath.lengthLongestPath("dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"));
        System.out.println(longestFilePath.lengthLongestPath("a"));
        System.out.println(longestFilePath.lengthLongestPath("dir\n        file.txt"));
        System.out.println(longestFilePath.lengthLongestPath("file1.txt\nfile2.txt\nlongfile.txt"));
    }

}
