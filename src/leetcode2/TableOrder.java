package leetcode2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TableOrder {

    public static void main(String[] args) {
        TableOrder tableOrder = new TableOrder();
        System.out.println(
            tableOrder.entityParser("&amp; is an HTML entity but &ambassador; is not."));
    }

    public List<List<String>> displayTable(List<List<String>> orders) {
        Set<String> foodItems = new TreeSet<>(
            orders.stream().map(o -> o.get(2)).collect(Collectors.toList()));
        Map<Integer, Map<String, Integer>> tableOrdersMap = new HashMap<>();
        for (List<String> order : orders) {
            Integer table = Integer.valueOf(order.get(0));
            String food = order.get(2);
            Integer quantity = Integer.valueOf(order.get(1));
            tableOrdersMap.putIfAbsent(table, getFoodMap(foodItems));
            tableOrdersMap.get(table).put(food, tableOrdersMap.get(table).get(food) + quantity);
        }
        List<List<String>> results = new ArrayList<>();
        List<String> header = new ArrayList<>();
        header.add("Table");
        header.addAll(foodItems);
        results.add(header);
        for (Map.Entry<Integer, Map<String, Integer>> entry : tableOrdersMap.entrySet()) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(entry.getKey()));
            row.addAll(entry.getValue().values().stream().map(i -> String.valueOf(i))
                .collect(Collectors.toList()));
            results.add(row);
        }
        return results;

    }

    private Map<String, Integer> getFoodMap(Set<String> foodItems) {
        Map<String, Integer> foodMap = new HashMap<>();
        for (String food : foodItems) {
            foodMap.put(food, 0);
        }
        return foodMap;
    }


    public String entityParser(String text) {
        Trie root = new Trie();
        root.add("&frasl;".chars().mapToObj(i -> (char) i).collect(Collectors.toList()), 0, '/');
        root.add("&quot;".chars().mapToObj(i -> (char) i).collect(Collectors.toList()), 0, '\"');
        root.add("&apos;".chars().mapToObj(i -> (char) i).collect(Collectors.toList()), 0, Character.valueOf('\\'));
        root.add("&amp;".chars().mapToObj(i -> (char) i).collect(Collectors.toList()), 0, '&');
        root.add("&gt;".chars().mapToObj(i -> (char) i).collect(Collectors.toList()), 0, '>');
        root.add("&lt;".chars().mapToObj(i -> (char) i).collect(Collectors.toList()), 0, '<');
        List<Character> textCharsList = text.chars().mapToObj(i -> (char) i)
            .collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < textCharsList.size(); ) {
            if (textCharsList.get(i).equals('&')) {
                Pair replacement = root.ifExistsGetReplacement(textCharsList, i, 0);
                if (replacement.key != null) {
                    i += (Integer) replacement.value;
                    builder.append(replacement.key);
                } else {
                    builder.append(textCharsList.get(i));
                    i++;
                }
            } else {
                builder.append(textCharsList.get(i));
                i++;
            }
        }
        return builder.toString();

    }

    class Trie {

        Map<Character, Trie> characterTrieMap = new HashMap<>();

        Character replacement;

        Pair ifExistsGetReplacement(List<Character> characters, int idx, int depth) {
            if (replacement != null || !characterTrieMap.containsKey(characters.get(idx))
                || idx == characters.size()) {
                return new Pair(replacement, depth);
            }
            return characterTrieMap.get(characters.get(idx))
                .ifExistsGetReplacement(characters, idx + 1, depth + 1);
        }

        void add(List<Character> characters, int idx, Character ch) {
            if (idx == characters.size()) {
                replacement = ch;
                return;
            }
            characterTrieMap.putIfAbsent(characters.get(idx), new Trie());
            characterTrieMap.get(characters.get(idx)).add(characters, idx + 1, ch);
        }
    }

    class Pair<K, V> {

        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
