package src.tools;

import src.file_operations.LoadReadFile;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DecompressFile implements Tools {
    LoadReadFile loadReadFile = new LoadReadFile();

    public void decompressFile(String fileName) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            int padding = dis.readInt();

            Map<String, Character> reverseCodeTable = readHeader(dis);

            byte[] compressedData = dis.readAllBytes();

            String decodedText = decode(compressedData, reverseCodeTable, padding);

            String outputFilename = loadReadFile.replaceCompressedSuffixToDecompressed(fileName);
            saveDecompressedFile(outputFilename, decodedText);

            System.out.println("Decompressed file has been saved to: " + outputFilename);
        }
    }

    private Map<String, Character> readHeader(DataInputStream dis) throws IOException {
        Map<String, Character> reverseCodeTable = new HashMap<>();
        int tableSize = dis.readInt();
        for (int i = 0; i < tableSize; i++) {
            char character = dis.readChar();
            int codeLength = dis.readUnsignedByte();
            String huffmanCode = readHuffmanCodeFromBits(dis, codeLength);
            reverseCodeTable.put(huffmanCode, character);
        }
        return reverseCodeTable;
    }

    private String readHuffmanCodeFromBits(DataInputStream dis, int codeLength) throws IOException {
        StringBuilder code = new StringBuilder();
        int bitsRead = 0;

        while (bitsRead < codeLength) {
            byte currentByte = dis.readByte();

            for (int i = 7; i >= 0 && bitsRead < codeLength; i--) {
                int bit = (currentByte >> i) & 1;
                code.append(bit);
                bitsRead++;
            }
        }
        return code.toString();
    }

    private String decode(byte[] compressedData, Map<String, Character> reverseCodeTable, int padding) {
        StringBuilder binaryString = new StringBuilder();

        for (byte b : compressedData) {
            String binaryByte = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            binaryString.append(binaryByte);
        }
        if (padding > 0) {
            binaryString.setLength(binaryString.length() - padding);
        }

        StringBuilder decodedText = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (int i = 0; i < binaryString.length(); i++) {
            currentCode.append(binaryString.charAt(i));
            if (reverseCodeTable.containsKey(currentCode.toString())) {
                decodedText.append(reverseCodeTable.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }
        return decodedText.toString();
    }

    private void saveDecompressedFile(String fileName, String content) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        }
    }
}
