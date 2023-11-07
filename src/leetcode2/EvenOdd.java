package leetcode2;


import leetcode.TreeNode;

import java.util.*;

class EvenOdd {
    public boolean isEvenOddTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        return isEvendOdd(root, queue, 1, false);
    }

    private boolean isEvendOdd(TreeNode root, Queue<TreeNode> queue, int rowSize, boolean isEven) {
        if (queue.isEmpty()) {
            return true;
        }
        int count = rowSize;
        int added = 0;
        int prev = queue.peek().val;
        while (!queue.isEmpty() && count > 0) {
            TreeNode node = queue.poll();
            count--;
            if (isInCorrectNum(isEven, node)) {
                return false;
            }
            if (isIncorrectOrder(rowSize, isEven, count, prev, node)) {
                return false;
            }
            added = add(queue, added, node.left);
            added = add(queue, added, node.right);
            if (rowSize - count > 1) {
                prev = node.val;
            }
        }
        return isEvendOdd(root, queue, added, !isEven);
    }

    private int add(Queue<TreeNode> queue, int added, TreeNode left) {
        if (left != null) {
            queue.offer(left);
            added++;
        }
        Map<String, Boolean> memoryMap;
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());

        class Person {
            String name;
            int age;

            public Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
        }

        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 25));
        people.add(new Person("Bob", 30));
        people.add(new Person("Charlie", 22));

        Comparator<Person> ageComparator = (person1, person2) -> {
            if (Integer.compare(person1.age, person2.age) == 0) {
                return person1.name.compareTo(person2.name);
            }
            return Integer.compare(person1.age, person2.age);
        };


        return added;
    }

    private boolean isIncorrectOrder(int rowSize, boolean isEven, int count, int prev, TreeNode node) {
        return (isEven && rowSize - count > 1 && prev <= node.val) || (!isEven && rowSize - count > 1 && prev >= node.val);
    }

    private boolean isInCorrectNum(boolean isEven, TreeNode node) {
        return (isEven && node.val % 2 != 0) || (!isEven && node.val % 2 != 1);
    }
}