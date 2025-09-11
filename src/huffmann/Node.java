package src.huffmann;

public class Node {
    long freq;
    char c;
    Node left;
    Node right;

    public Node() {
    }

    public Node(long freq, char c) {
        this.freq = freq;
        this.c = c;
        this.left = this.right = null;
    }

    public Node(long freq, char c, Node left, Node right) {
        this.freq = freq;
        this.c = c;
        this.left = left;
        this.right = right;
    }
}
