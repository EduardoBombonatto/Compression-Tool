package src.huffmann;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Tree {

    public Node createTree(Map<Character, Long> mapChar) {
        PriorityQueue<Node> pq = new PriorityQueue<>(mapChar.size(), Comparator.comparingLong((Node a) -> a.freq));

        for (Map.Entry<Character, Long> m : mapChar.entrySet()) {
            pq.add(new Node(m.getValue(), m.getKey()));
        }
        Node root = null;
        while (pq.size() > 1) {
            Node hf1 = pq.poll();
            Node hf2 = pq.poll();

            Node hf3 = new Node(hf1.freq + hf2.freq, '-', hf1, hf2);
            pq.add(hf3);
            root = hf3;
        }
        return root;
    }

    public Map<Character, String> huffmanPrefixTable(Node root) {
        Map<Character, String> codeTable = new HashMap<>();
        prefixCodeTable(root, "", codeTable);
        return codeTable;
    }

    void prefixCodeTable(Node root, String s, Map<Character, String> codeTable) {
        if (root.left == null && root.right == null) {
            codeTable.put(root.c, s);
            return;
        }
        prefixCodeTable(root.left, s + "0", codeTable);
        prefixCodeTable(root.right, s + "1", codeTable);
    }
}
