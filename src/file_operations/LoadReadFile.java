package src.file_operations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LoadReadFile {

    public String readAsString(String fileName) throws IOException {
        String data = new String(Files.readAllBytes(Path.of(fileName)));
        if (data == null) {
            throw new FileNotFoundException("Provide the correct file name!");
        }
        return data;
    }

    /**
     * @param data --> string of data after reading a file
     * @return mapFreq
     */
    public Map<Character, Long> charFrequencyFile(String data) {
        Map<Character, Long> mapFreq = new HashMap<>();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            mapFreq.put(c, mapFreq.getOrDefault(c, 0L) + 1);
        }
        return mapFreq;
    }

    /**
     * Add the sufix "-compressed" to the file name
     *
     * @param fileName original name of the file
     * @return new name
     */
    public String addCompressedSuffix(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0) {
            String name = fileName.substring(0, dotIndex);
            String extension = fileName.substring(dotIndex);
            return name + "-compressed" + extension;
        } else {
            return fileName + "-compressed";
        }
    }

    public String replaceCompressedSuffixToDecompressed(String fileName) {
        if (fileName.contains("-compressed")) {
            return fileName.replace("-compressed", "-decompressed");
        }
        return fileName + "-decompressed";
    }
}
