package leetcode;

import java.util.*;

public class SearchSuggestion {

    private Node trie = new Node();
    private Map<String, Integer> wordMap = new HashMap<String, Integer>();
    private String[] productsArr;

    private class Node {
        private Node[] index = new Node[26];
        private String word;
        private int arrayInd = -1;

        public Node() {

        }

        public Node(String word) {
            this.word = word;
        }

        public void insert(String word, int pos) {
            int ind = word.charAt(pos) - 97;
            if (index[ind] == null) {
                index[ind] = new Node();
            }
            if (arrayInd == -1) {
                arrayInd = wordMap.get(word);
            }
            if (pos == word.length() - 1) {
                this.word = word;
            } else {
                index[ind].insert(word, pos + 1);
            }

        }

        public List<String> get(String word, int currPos, int maxPos) {
            List<String> res = new ArrayList<String>();
            if (currPos == maxPos) {
                res.add(productsArr[arrayInd]);
                if (arrayInd + 1 < productsArr.length) {
                    res.add(productsArr[arrayInd + 1]);
                }
                if (arrayInd + 2 < productsArr.length) {
                    res.add(productsArr[arrayInd + 2]);
                }
            } else {
                int ind = word.charAt(currPos) - 97;
                res = index[ind].get(word, currPos + 1, maxPos);
            }

            return res;
        }

    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<List<String>>();

        Arrays.sort(products);
        List<String> sortedProductsList = Arrays.asList(products);
        for (int ind = 1; ind <= searchWord.length(); ind++) {
            int i = Collections.binarySearch(sortedProductsList, searchWord.substring(0, ind));
            if (i < 0) {
                i = -i - 1;
            }
            List<String> inner = new ArrayList<String>();
            inner.add(products[i]);
            if (i + 1 < products.length) {
                inner.add(products[i + 1]);
            }
            if (i + 2 < products.length) {
                inner.add(products[i + 2]);
            }
            res.add(inner);

        }
        return res;
    }

    public List<List<String>> suggestedProducts1(String[] products, String searchWord) {
        List<List<String>> res = new ArrayList<List<String>>();
        Arrays.sort(products);
        productsArr = products;
        int k = 0;
        for (String product : productsArr) {
            wordMap.putIfAbsent(product, k++);
            trie.insert(product, 0);
        }

        for (int m = 0; m <= searchWord.length(); m++) {
            res.add(trie.get(searchWord, 0, m));
        }

        return res;
    }

    public static void main(String[] args) {
        SearchSuggestion searchSuggestion = new SearchSuggestion();
        List<List<String>> results1 = searchSuggestion.suggestedProducts(new String[]{"mobile", "mouse", "moneypot", "monitor", "mousepad"}, "mouse");
        List<List<String>> results2 = searchSuggestion.suggestedProducts1(new String[]{"mobile", "mouse", "moneypot", "monitor", "mousepad"}, "mouse");
        System.out.println(results1);
        System.out.println(results2);
    }
}
