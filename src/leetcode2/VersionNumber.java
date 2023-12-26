package leetcode2;

import java.util.Arrays;
import java.util.NavigableMap;
import java.util.TreeMap;

public class VersionNumber {

    public static void main(String[] args) {
        VersionNumber versionNumber = new VersionNumber();
        System.out.println(versionNumber.compareVersion("1.01", "1.001"));
    }

    public int compareVersion(String version1, String version2) {
        String[] revisionArr1 = version1.split("\\.");
        String[] revisionArr2 = version2.split("\\.");
        int maxSize = Math.max(revisionArr1.length, revisionArr2.length);
        if (revisionArr1.length < maxSize) {
            revisionArr1 = Arrays.copyOf(revisionArr1, maxSize);
        } else if (revisionArr2.length < maxSize) {
            revisionArr2 = Arrays.copyOf(revisionArr2, maxSize);
        }

        for (int i = 0; i < revisionArr1.length; i++) {
            String left = revisionArr1[i] == null ? "0" : revisionArr1[i];
            String right = revisionArr2[i] == null ? "0" : revisionArr2[i];
            if (Integer.parseInt(left) != Integer.parseInt(right)) {
                int diff = Integer.parseInt(left) - Integer.parseInt(right);
                return diff < 0 ? -1 : 1;
            }
        }
        return 0;
    }

}
