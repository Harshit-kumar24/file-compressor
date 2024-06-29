package com.compressor.file_compressor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HuffmanController {
    private static final Logger logger = LoggerFactory.getLogger(HuffmanController.class);
    private final Huffman huffman = new Huffman();
    private HuffmanNode huffmanTree;
    private Map<Character, String> huffmanCodes;

    @PostMapping("/compress")
    public ResponseEntity<byte[]> compressFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            logger.warn("No file uploaded");
            return ResponseEntity.badRequest().body(null);
        }
        String data = new String(file.getBytes(), StandardCharsets.UTF_8);
        logger.info("Original data size: {} bits", data.length() * 8);

        huffmanTree = huffman.buildHuffmanTree(data);
        huffmanCodes = huffman.generateCodes(huffmanTree);
        boolean[] compressedData = huffman.compress(data, huffmanCodes);

        // Convert boolean array to byte array
        byte[] byteArray = convertBooleanArrayToByteArray(compressedData);

        logger.info("Compressed data size: {} bits", compressedData.length);
        return ResponseEntity.ok(byteArray);
    }

    @PostMapping("/decompress")
    public ResponseEntity<String> decompressFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            logger.warn("No file uploaded");
            return ResponseEntity.badRequest().body("No file uploaded");
        }
        byte[] byteArray = file.getBytes();
        
        // Convert byte array back to boolean array
        boolean[] compressedData = convertByteArrayToBooleanArray(byteArray);

        String decompressedData = huffman.decompress(compressedData, huffmanTree);
        return ResponseEntity.ok(decompressedData);
    }
    
    
    private byte[] convertBooleanArrayToByteArray(boolean[] booleanArray) {
        int byteArrayLength = (booleanArray.length + 7) / 8;
        byte[] byteArray = new byte[byteArrayLength];
        for (int i = 0; i < booleanArray.length; i++) {
            if (booleanArray[i]) {
                byteArray[i / 8] |= 1 << (7 - i % 8);
            }
        }
        return byteArray;
    }
    

private boolean[] convertByteArrayToBooleanArray(byte[] byteArray) {
    boolean[] booleanArray = new boolean[byteArray.length * 8];
    for (int i = 0; i < byteArray.length * 8; i++) {
        booleanArray[i] = (byteArray[i / 8] & (1 << (7 - i % 8))) != 0;
    }
    return booleanArray;
}
}
