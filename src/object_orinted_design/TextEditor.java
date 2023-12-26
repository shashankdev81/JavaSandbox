package object_orinted_design;

public class TextEditor {

    static CharacterNode sentinel = new CharacterNode();

    static CharacterNode tail = sentinel;

    static CharacterNode curr = sentinel;

    public TextEditor() {

    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.addText("leetcode");
        System.out.println(sentinel);
        editor.deleteText(4);
        System.out.println(sentinel);
        editor.addText("practice");
        System.out.println(sentinel);
        System.out.println(editor.cursorRight(3));
        System.out.println(sentinel);
        System.out.println(editor.cursorLeft(8));
        System.out.println(sentinel);
        editor.deleteText(10);
        System.out.println(sentinel);
        editor.cursorLeft(2);
        System.out.println(sentinel);
        editor.cursorRight(6);
        System.out.println(sentinel);
    }

    public void addText(String text) {
        for (char c : text.toCharArray()) {
            curr.next = new CharacterNode(curr, null, c);
            curr = curr.next;
        }
        tail = curr;
    }

    public int deleteText(int k) {
        int count = k;
        while (curr != sentinel && count > 0) {
            curr = curr.prev;
            count--;
        }
        curr.next.prev = null;
        curr.next = null;
        tail = curr;
        return k - count;
    }

    public String cursorLeft(int k) {
        int count = k;
        while (curr != sentinel && count > 0) {
            curr = curr.prev;
            count--;
        }
        StringBuilder result = printLeft();
        return result.toString();

    }

    private StringBuilder printLeft() {
        int count = 10;
        CharacterNode start = curr;
        StringBuilder result = new StringBuilder();
        while (start != sentinel && count > 0) {
            result.append(start.c);
            start = start.prev;
            count--;
        }
        return result.reverse();
    }

    public String cursorRight(int k) {
        int count = k;
        while (curr != tail && count > 0) {
            curr = curr.next;
            count--;
        }
        StringBuilder result = printLeft();
        return result.toString();
    }

    private static class CharacterNode {

        private CharacterNode prev;
        private CharacterNode next;
        private Character c;

        public CharacterNode(CharacterNode prev, CharacterNode next, Character c) {
            this.prev = prev;
            this.next = next;
            this.c = c;
        }

        public CharacterNode() {
        }

        @Override
        public String toString() {
            CharacterNode curr = sentinel.next;
            StringBuilder builder = new StringBuilder();
            while (curr != null) {
                builder.append(curr.c);
                curr = curr.next;
            }
            return "CharacterNode=" + builder.toString();
        }
    }
}
