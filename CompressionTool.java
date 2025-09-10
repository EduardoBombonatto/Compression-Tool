import tools.HuffTree;
import tools.HuffmanBuilder;
import tools.HuffmanCodeGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompressionTool {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java CompressionTool <filename>");
            System.exit(1);
        }

        String filename = args[0];
        File file = new File(filename);

        if (!file.exists()) {
            System.err.println("File " + filename + " does not exist.");
            System.exit(1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int c;
            Map<Character, Integer> freqMap = new HashMap<>();

            while ((c = br.read()) != -1){
                char ch = (char)c;
                if (freqMap.containsKey(ch)) {
                    freqMap.put(ch, freqMap.get(ch) + 1);
                } else {
                    freqMap.put(ch, 1);
                }
            }
            HuffTree huffTree = HuffmanBuilder.buildTree(freqMap);
            Map<Character, String> codeTable = new HashMap<>();
            HuffmanCodeGenerator.generateCodes(huffTree.root(), "", codeTable);

            for (Map.Entry<Character, String> entry : codeTable.entrySet()) {
                System.out.println(entry.getKey() + " : "  + entry.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error opening file " + filename + ": " + e.getMessage());
            System.exit(1);
        }
    }
}
