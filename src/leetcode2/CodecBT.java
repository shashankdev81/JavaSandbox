//package leetcode2;
//
//import java.util.ArrayDeque;
//import java.util.List;
//import java.util.Queue;
//
//public class CodecBT {
//
//
//    class Node {
//
//        public int val;
//        public List<Node> children;
//
//        public Node() {
//        }
//
//        public Node(int _val) {
//            val = _val;
//        }
//
//        public Node(int _val, List<Node> _children) {
//            val = _val;
//            children = _children;
//        }
//    }
//
//    class Codec {
//
//        // Encodes a tree to a single string.
//        public String serialize(Node root) {
//            Queue<Pair> queue = new ArrayDeque<>();
//            StringBuilder nodeList = new StringBuilder();
//            queue.offer(new Pair(root, 0));
//            StringBuilder relations = new StringBuilder();
//            int k = 0;
//            while (!queue.isEmpty()) {
//                int count = queue.size();
//                while (count > 0) {
//                    Pair parent = queue.poll();
//                    nodeList.append(parent.getKey().val).append(",");
//                    for (Node child : parent.children) {
//                        nodeList.append(child.val).append(",");
//                        queue.offer(new Pair(child, k));
//                        relations.append(parent.getValue() + "-" + k).append(",");
//                        k++;
//                    }
//                    count--;
//                }
//
//            }
//            nodeList.substring(0, nodeList.length() - 1);
//            relations.substring(0, nodeList.length() - 1);
//            nodeList.append("|").append(relations);
//            return nodeList.toString();
//        }
//
//        // Decodes your encoded data to tree.
//        public Node deserialize(String data) {
//
//        }
//    }
//}