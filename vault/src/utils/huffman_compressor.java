package utils;

import java.util.*;

public class huffman_compressor {
    private static class huffman_node implements Comparable<huffman_node>{
        char ch;
        int freq;
        huffman_node left, right;

        huffman_node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        public boolean is_leaf() {
            return left == null && right == null;
        }

        public int compareTo(huffman_node o) {
            return this.freq - o.freq;
        }
    }

    private Map<Character, String> huffman_code;
    private huffman_node root;

    public String compress(String input) {
        Map<Character, Integer> freq_map = build_freqmap(input);
        root = build_tree(freq_map);
        huffman_code = build_codes(root);

        StringBuilder encoded = new StringBuilder();
        for (char c : input.toCharArray()) {
            encoded.append(huffman_code.get(c));
        }
        return encoded.toString();
    }

    public String decompress(String encoded) {
        StringBuilder decoded = new StringBuilder();
        huffman_node node = root;

        for (char bit : encoded.toCharArray()) {
            node = (bit == '0') ? node.left : node.right;
            if (node.is_leaf()) {
                decoded.append(node.ch);
                node = root;
            }
        }
        return decoded.toString();
    }

    private Map<Character, Integer> build_freqmap(String input) {
        Map<Character, Integer> map = new HashMap<>();

        for (char c : input.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }

    private huffman_node build_tree(Map<Character, Integer> freq_map) {
        PriorityQueue<huffman_node> queue = new PriorityQueue<>();

        for (var entry : freq_map.entrySet()) {
            queue.add(new huffman_node(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            huffman_node left = queue.poll();
            huffman_node right = queue.poll();
            huffman_node parent = new huffman_node('\0', left.freq + right.freq);
            parent.left = left;
            parent.right = right;
            queue.add(parent);
        }
        return queue.poll();
    }

    private Map<Character, String> build_codes(huffman_node root) {
        Map<Character, String> map = new HashMap<>();
        build_helper(root, "", map);
        return map;
    }

    private void build_helper(huffman_node node, String code, Map<Character, String> map) {
        if (node == null) {
            return;
        }

        if (node.is_leaf()) {
            map.put(node.ch, code);
        }

        build_helper(node.left, code + "0", map);
        build_helper(node.right, code + "1", map);
    }
}