package password;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class huffman {
    private static class huffman_node implements Comparable<huffman_node> {
        char ch;
        int freq;
        huffman_node left;
        huffman_node right;

        public huffman_node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
            this.left = null;
            this.right = null;
        }

        public huffman_node(int freq, huffman_node left, huffman_node right) {
            this.ch = '\0';
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(huffman_node o) {
            return this.freq - o.freq;
        }

        public boolean is_leaf() {
            return left == null && right == null;
        }
    }

    private huffman_node root;
    private Map<Character, String> huffman_code;
    private Map<String, Character> reverse;

    public huffman() {
        this.huffman_code = new HashMap<>();
        this.reverse = new HashMap<>();
    }

    public void build_tree(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }

        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<huffman_node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.offer(new huffman_node(entry.getKey(), entry.getValue()));
        }

        if (pq.size() == 1) {
            root = pq.poll();
            huffman_code.put(root.ch, "0");
            reverse.put("0", root.ch);
            return;
        }

        while (pq.size() > 1) {
            huffman_node left = pq.poll();
            huffman_node right = pq.poll();
            huffman_node parent = new huffman_node(left.freq + right.freq, left, right);
            pq.offer(parent);
        }

        this.root = pq.poll();
        generate_code(root, "");
    }

    private void generate_code(huffman_node node, String code) {
        if (node == null) {
            return;
        }

        if (node.is_leaf()) {
            huffman_code.put(node.ch, code);
            reverse.put(code,  node.ch);
        } else {
            generate_code(node.left, code + "0");
            generate_code(node.right, code + "1");
        }
    }

    public String encode(String text) {
        if (root == null || huffman_code.isEmpty()) {
            return null;
        }

        if (text == null || text.isEmpty()) {
            return "";
        }
        StringBuilder encoded_text = new StringBuilder();
        for (char c : text.toCharArray()) {
            String code = huffman_code.get(c);
            if (code == null) {
                return null;
            }
            encoded_text.append(code);
        }
        return encoded_text.toString();
    }

    public String decode(String text) {
        if (root == null || huffman_code.isEmpty()) {
            return null;
        }

        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder decoded_text = new StringBuilder();
        huffman_node current = root;
        for (char bit : text.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            } else {
                return null;
            }

            if (current.is_leaf()) {
                decoded_text.append(current.ch);
                current = root;
            }
        }
        return decoded_text.toString();
    }
}