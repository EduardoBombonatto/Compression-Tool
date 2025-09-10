package tools;

import java.util.Map;

public class HuffmanCodeGenerator {
    public static void generateCodes(HuffBaseNode node, String prefix, Map<Character, String> codeTable){
        if (node == null) return;

        if (node.isLeaf()) {
            HuffLeafNode leaf =  (HuffLeafNode) node;

            if (prefix.equals("")) {
                codeTable.put(leaf.value(), "0");
            } else {
                codeTable.put(leaf.value(), prefix);
            }
        } else {
            HuffInternalNode internal = (HuffInternalNode) node;
            generateCodes(internal.left(), prefix + "0", codeTable);
            generateCodes(internal.right(), prefix + "1", codeTable);
        }
    }
}
