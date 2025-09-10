package tools;

public class HuffInternalNode implements HuffBaseNode {
    private final int weight;
    private final HuffBaseNode left;
    private final HuffBaseNode right;

    HuffInternalNode(HuffBaseNode left,
                     HuffBaseNode right, int weight) {
        this.left = left;
        this.right = right;
        this.weight = weight;
    }

    HuffBaseNode left() {
        return left;
    }

    HuffBaseNode right() {
        return right;
    }

    @Override
    public int weight() {
        return weight;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
