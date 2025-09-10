package tools;

public class HuffTree implements Comparable {
    private final HuffBaseNode root;

    HuffTree(char element, int weight) {
        this.root = new HuffLeafNode(element, weight);
    }

    HuffTree(HuffBaseNode left, HuffBaseNode right, int weight) {
        this.root = new HuffInternalNode(left, right, weight);
    }

    public HuffBaseNode root() {
        return this.root;
    }

    int weight() {
        return root.weight();
    }

    @Override
    public int compareTo(Object t) {
        HuffTree that = (HuffTree) t;
        return Integer.compare(root.weight(), that.weight());
    }
}
