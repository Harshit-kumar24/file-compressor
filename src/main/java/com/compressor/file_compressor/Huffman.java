package com.compressor.file_compressor;


import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    public Map<Character, String> generateCodes(HuffmanNode root) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodesHelper(root, "", huffmanCodes);
        return huffmanCodes;
    }

    private void generateCodesHelper(HuffmanNode root, String code, Map<Character, String> huffmanCodes) {
        if (root == null) {
            return;
        }
        if (root.data != '\0') {
            huffmanCodes.put(root.data, code);
        }
        generateCodesHelper(root.left, code + "0", huffmanCodes);
        generateCodesHelper(root.right, code + "1", huffmanCodes);
    }

    public HuffmanNode buildHuffmanTree(String data) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : data.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(freqMap.size(), new HuffmanComparator());
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            HuffmanNode node = new HuffmanNode();
            node.data = entry.getKey();
            node.frequency = entry.getValue();
            node.left = null;
            node.right = null;
            queue.add(node);
        }

        while (queue.size() > 1) {
            HuffmanNode x = queue.poll();
            HuffmanNode y = queue.poll();

            HuffmanNode sum = new HuffmanNode();
            sum.data = '\0';
            sum.frequency = x.frequency + y.frequency;
            sum.left = x;
            sum.right = y;
            queue.add(sum);
        }
        return queue.poll();
    }

    public boolean[] compress(String data, Map<Character, String> huffmanCodes) {
        StringBuilder compressedData = new StringBuilder();
        for (char c : data.toCharArray()) {
            compressedData.append(huffmanCodes.get(c));
        }
        boolean[] booleanArray = new boolean[compressedData.length()];
        for (int i = 0; i < compressedData.length(); i++) {
            booleanArray[i] = compressedData.charAt(i) == '1';
        }
        return booleanArray;
    }

    public String decompress(boolean[] compressedData, HuffmanNode root) {
    	  StringBuilder decompressedData = new StringBuilder();
          HuffmanNode current = root;
          for (boolean bit : compressedData) {
              current = bit ? current.right : current.left;
              if (current.left == null && current.right == null) {
                  decompressedData.append(current.data);
                  current = root;
              }
          }

        return decompressedData.toString();
    }
}
