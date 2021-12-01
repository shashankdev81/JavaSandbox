package leetcode;

//import com.sun.xml.internal.ws.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class StringDecode {

    private List<Character> numbers = Arrays.asList(new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});

    public String decodeString(String s) {
        char[] encoded = s.toCharArray();
        StringBuilder word = new StringBuilder();
        int ind = 0;
        while (ind < s.length()) {

            StringBuilder occurrences = new StringBuilder();
            StringBuilder encodedSubstr = new StringBuilder();

            while (ind < s.length() && !numbers.contains(encoded[ind])) {
                word.append(encoded[ind++]);
            }
            while (ind < s.length() && numbers.contains(encoded[ind])) {
                occurrences.append(encoded[ind++]);
            }
            int count = 1;
            ind++;
            while (count != 0 && ind < s.length()) {
                if (encoded[ind] == '[') {
                    count++;
                    encodedSubstr.append(encoded[ind]);
                } else if (encoded[ind] == ']') {
                    count--;
                    if (count > 0) {
                        encodedSubstr.append(encoded[ind]);
                    }
                } else {
                    encodedSubstr.append(encoded[ind]);
                }
                ind++;
            }
            if (encodedSubstr.length() > 0) {
                String decodedSubstr = decodeString(encodedSubstr.toString());
                int times = Integer.valueOf(occurrences.toString());
                for (int i = 0; i < times; i++) {
                    word.append(decodedSubstr);
                }
            }
        }
        return word.toString();

    }

    public static void main(String[] args) {
        StringDecode decode = new StringDecode();
        System.out.println(decode.decodeString("3[a]2[bc]"));
        System.out.println(decode.decodeString("3[a2[c]]"));
        System.out.println(decode.decodeString("2[abc]3[cd]ef"));
        System.out.println(decode.decodeString("abc3[cd]xyz"));
    }
}
