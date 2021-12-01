package leetcode;

import java.util.*;

public class CustomSortString {

    public String customSortString(String order, String str) {
        Map<Character, Integer> lettersInStr = new LinkedHashMap<Character, Integer>();
        for (char c : str.toCharArray()) {
            Character ch = new Character(c);
            lettersInStr.putIfAbsent(ch, 0);
            lettersInStr.put(ch, lettersInStr.get(ch) + 1);
        }
        char[] newStr = new char[str.length()];
        char[] strArr = str.toCharArray();
        char[] orderArr = order.toCharArray();
        int orderInd = 0;
        int newStrInd = 0;
        while (orderInd < order.length()) {
            Character curr = new Character(orderArr[orderInd]);
            if (lettersInStr.containsKey(curr)) {
                //repeat
                int count = lettersInStr.get(curr);
                for (int i = 0; i < count; i++) {
                    newStr[newStrInd++] = curr.charValue();
                }
                lettersInStr.remove(curr);

            }
            orderInd++;
        }
        Iterator<Character> strItr = lettersInStr.keySet().iterator();
        for (int i = 0; i < str.length(); i++) {
            Character curr = new Character(strArr[i]);
            if (lettersInStr.containsKey(curr)) {
                newStr[newStrInd++] = curr.charValue();
            }

        }
        return new String(newStr);
    }

    public static void main(String[] args) {
        String order = "hucw";
        String str = "utzoampdgkalexslxoqfkdjoczajxtuhqyxvlfatmptqdsochtdzgypsfkgqwbgqbcamdqnqztaqhqanirikahtmalzqjjxtqfnh";
        CustomSortString customSortString = new CustomSortString();
        System.out.println(customSortString.customSortString(order, str).trim());
    }
}
