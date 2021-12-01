package concurrency.nonblockingds;

import java.util.Arrays;
import java.util.List;

public class DecodeString {
    public String decodeString(String s) {
        System.out.println(s);
        char[] charArray = s.toCharArray();
        int ind = 0;
        StringBuilder decoded = new StringBuilder();
        List<Character> numerals = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        while (ind < charArray.length) {
            StringBuilder num = new StringBuilder();
            StringBuilder encoded = new StringBuilder();
            while (charArray[ind] != '[') {
                num.append(charArray[ind]);
                ind++;
            }
            ind++;
            boolean isAlphanumeric = false;
            while (charArray[ind] != ']') {
                encoded.append(charArray[ind]);
                ind++;
                isAlphanumeric = isAlphanumeric || numerals.contains(charArray[ind]);
            }
            ind++;
            for (int i = 0; i < Integer.valueOf(num.toString()); i++) {
                decoded.append(isAlphanumeric ? decodeString(encoded.toString()) : encoded);
            }
        }

        return decoded.toString();
    }

    public static void main(String[] args) {
        DecodeString decodeString = new DecodeString();
        System.out.println(decodeString.decodeString("3[a]2[bc]"));
        System.out.println(decodeString.decodeString("3[a2[c]]"));

    }
}
