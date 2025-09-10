package tools;

public class HuffLeafNode implements HuffBaseNode{
    private final char element;
    private final int weight;

    HuffLeafNode(char element, int weight){
        this.element = element;
        this.weight = weight;
    }

    char value() {
        return element;
    }
    @Override
    public int weight(){
        return weight;
    }
    @Override
    public boolean isLeaf(){
        return true;
    }
}
