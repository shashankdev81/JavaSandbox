import java.util.*;

public class HuffManCoding {

    public static void main(String[] args) {
        HuffManCoding coding = new HuffManCoding();
        List<String> result = coding.enode("shashankbaravani");
        for (String code : result) {
            System.out.println(code);
        }
    }

    private class CharFreq implements Comparable<CharFreq> {
        Character c;
        Integer frequency;
        CharFreq left;
        CharFreq right;

        public CharFreq(Character c, Integer frequence) {
            this.c = c;
            this.frequency = frequence;
        }

        @Override
        public int compareTo(CharFreq o) {
            return o.frequency.compareTo(frequency);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CharFreq charFreq = (CharFreq) o;
            return c.equals(charFreq.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c);
        }
    }

    private List<String> enode(String word) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char ch : word.toCharArray()) {
            frequencyMap.putIfAbsent(ch, 0);
            frequencyMap.put(ch, frequencyMap.get(ch) + 1);
        }
        PriorityQueue<CharFreq> priorityQueue = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            priorityQueue.add(new CharFreq(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() >= 2) {
            CharFreq one = priorityQueue.poll();
            CharFreq two = priorityQueue.poll();
            CharFreq onePusTwo = new CharFreq(null, one.frequency + two.frequency);
            onePusTwo.left = one.frequency <= two.frequency ? one : two;
            onePusTwo.right = one.frequency <= two.frequency ? two : one;
            priorityQueue.add(onePusTwo);
        }

        CharFreq huffmanTree = priorityQueue.poll();
        Map<Character, String> codeMap = new HashMap<>();
        extractCodes(huffmanTree, codeMap, "");
        List<String> result = new ArrayList<>();
        for (char c : word.toCharArray()) {
            result.add(codeMap.get(c));
        }
        return result;
    }

    private void extractCodes(CharFreq node, Map<Character, String> codeMap, String code) {
        if (node == null) {
            return;
        }
        if (node.c != null) {
            codeMap.putIfAbsent(node.c, code);
            return;
        }
        extractCodes(node.left, codeMap, code + "0");
        extractCodes(node.right, codeMap, code + "1");

    }
}
