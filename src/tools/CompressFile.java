package src.tools;

import src.file_operations.LoadReadFile;
import src.huffmann.Node;
import src.huffmann.Tree;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class CompressFile implements Tools {
    LoadReadFile loadReadFile = new LoadReadFile();
    Tree huffmanTree = new Tree();

    public void compressFile(String fileName) throws IOException {
        String data = loadReadFile.readAsString(fileName);

        Map<Character, Long> mapFreqFile = loadReadFile.charFrequencyFile(data);

        Node root = huffmanTree.createTree(mapFreqFile);

        Map<Character, String> codeTable = huffmanTree.huffmanPrefixTable(root);
        System.out.println("Compress huffman code: " + codeTable.size());

        String outputFilename = loadReadFile.addCompressedSuffix(fileName);
        CompressFile.enconded(data, outputFilename, codeTable);
    }

    public static void enconded(String fileData, String outputFilename, Map<Character, String> huffmanCodes) throws IOException {
        StringBuilder encodedText = new StringBuilder();

        for (int i = 0; i < fileData.length(); i++) {
            char c = fileData.charAt(i);
            encodedText.append(huffmanCodes.get(c));
        }
        byte[] byteOutput = encodeToByteArray(encodedText.toString());
        int padding = 8 - encodedText.length() % 8;
        if (padding == 8) padding = 0;
        compressDataToFile(outputFilename, huffmanCodes, byteOutput, padding);
    }

    public static void compressDataToFile(String outputFilename, Map<Character, String> huffmanCodes, byte[] byteOutput, int padding) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFilename))) {
            dos.writeInt(padding);
            writeHeader(huffmanCodes, dos);
            dos.write(byteOutput);
        }
    }

    /**
     * @param dos          DataOutputStream to the file
     * @param huffmanCodes The huffman code table.
     * @throws IOException this method writes header content to the compressed file
     *                     Header content contains - the size of huffman code, charcter - huffcode size for a character and value of huffman code for character
     */
    public static void writeHeader(Map<Character, String> huffmanCodes, DataOutputStream dos) throws IOException {
        dos.writeInt(huffmanCodes.size());
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            dos.writeChar(entry.getKey());
            dos.writeByte(entry.getValue().length());
            writeHuffmanCodeAsBits(dos, entry.getValue());
        }
    }

    /**
     * @param dos         DataOutputStream to the file
     * @param huffmanCode The huffman code table.
     * @throws IOException generate the bit sequence for compressed file for each huffman code
     */
    public static void writeHuffmanCodeAsBits(DataOutputStream dos, String huffmanCode) throws IOException {
        int bitsToWrite = huffmanCode.length();
        int bitPosition = 0;
        while (bitsToWrite > 0) {
            byte toWrite = 0;
            for (int i = 0; i < 8 && bitPosition < huffmanCode.length(); i++) {
                toWrite |= (byte) ((huffmanCode.charAt(bitPosition) == '1' ? 1 : 0) << (7 - i));
                bitPosition++;
                bitsToWrite--;
            }
            dos.writeByte(toWrite);
        }
    }

    /**
     * @param encodedText content of the given file in the for loop of encoded
     * @return generate byte array for the content of the given file
     */
    public static byte[] encodeToByteArray(String encodedText) {
        int byteSize = (int) Math.ceil(encodedText.length() / 8.0);
        byte[] output = new byte[byteSize];
        for (int i = 0; i < encodedText.length(); i += 8) {
            String byteStr = encodedText.substring(i, Math.min(i + 8, encodedText.length()));
            output[i / 8] = (byte) Integer.parseInt(byteStr, 2);
        }
        return output;
    }
}
