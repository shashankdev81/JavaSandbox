package leetcode2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FindService {

    public FindService() {

    }

    public static void main(String[] args) {
        FindService findService = new FindService();
        Node five = new Node(null, null, 5);
        Node four = new Node(null, five, 4);
        five.prev = four;
        Node three = new Node(null, four, 3);
        four.prev = three;
        Node two = new Node(null, three, 2);
        three.prev = two;
        Node one = new Node(null, two, 1);
        two.prev = one;

        Node four2 = new Node(null, null, 4);
        Node three2 = new Node(null, four2, 3);
        four2.prev = three2;
        System.out.println(findService.find(one, three2));
        three2.val = 100;
        System.out.println(findService.find(one, three2));

    }

    public int find(Node list1, Node list2) {
        int index = 0;
        while (list1 != null && list2 != null) {
            Node left = list1;
            Node right = list2;
            if (left.val == right.val && isMatchFound(left, right)) {
                return index;
            }
            list1 = list1.next;
            index++;
        }
        return -1;
    }

    private boolean isMatchFound(Node left, Node right) {
        while (left != null && right != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;
    }

    public static class Node {

        Node prev;
        Node next;
        int val;

        public Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }

        public Node() {
        }
    }

}
