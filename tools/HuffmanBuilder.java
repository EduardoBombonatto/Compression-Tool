package tools;

import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanBuilder {
    public static HuffTree buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffTree> pq = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.add(new HuffTree(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffTree first = pq.poll();
            HuffTree second = pq.poll();
            HuffTree merged = new HuffTree(
                    first.root(), second.root(),  first.weight() + second.weight()
            );
            pq.add(merged);
        }
        return pq.poll();
    }
}
