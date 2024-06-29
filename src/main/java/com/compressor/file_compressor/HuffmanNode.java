package com.compressor.file_compressor;

import java.util.Comparator;

public class HuffmanNode {
	int frequency;
	char data;
	HuffmanNode left, right;
}

class HuffmanComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
        return x.frequency - y.frequency;
    }
}
