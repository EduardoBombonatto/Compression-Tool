package src;

import src.tools.CompressFile;
import src.tools.DecompressFile;
import src.tools.Tools;

public class CompressionTool {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java src.CompressionTool '-c|-d' <filename>");
            System.exit(1);
        }

        String compDecomp = args[0];
        String filename = args[1];

        try {
            Tools tools;

            switch (compDecomp) {
                case "-c":
                    tools = new CompressFile();
                    ((CompressFile) tools).compressFile(filename);
                    break;
                case "-d":
                    tools = new DecompressFile();
                    ((DecompressFile) tools).decompressFile(filename);
                    break;
                default:
                    System.out.println("Choose a valid action : for compress : -c and for de-compress: -d");
                    break;
            }
            System.out.println("Action completed successfully");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
