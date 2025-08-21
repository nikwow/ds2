//
// Created by nicol on 8/20/2025.
//

#include "huffman.h"

#include <map>
#include <queue>

namespace {
    struct compare_node {
        bool operator()(const huffman::huffman_node* left, const huffman::huffman_node* right) const {
            return left->frequency > right->frequency;
        }
    };

    void generate_codes(const huffman::huffman_node* root, const std::string& current_code, std::map<char, std::string>& huffman_codes) {
        if (root == nullptr) return;

        if (!root->left && !root->right) huffman_codes[root->data] = current_code;

        generate_codes(root->left, current_code + "0", huffman_codes);
        generate_codes(root->right, current_code + "1", huffman_codes);
    }

    huffman::huffman_node* build_tree(const std::string& text) {
        std::map<char, int> freq_map;

        if (text.empty()) return nullptr;
        for (char ch : text) freq_map[ch]++;

        std::priority_queue<huffman::huffman_node*, std::vector<huffman::huffman_node*>, compare_node> pq;
        for (auto pair : freq_map) pq.push(new huffman::huffman_node(pair.first, pair.second));

        if (pq.size() == 1) {
            huffman::huffman_node* root_node = pq.top();
            pq.pop();
            auto* internal_node = new huffman::huffman_node('\0', root_node->frequency);
            internal_node->left = root_node;
            pq.push(internal_node);
        }
        while (pq.size() > 1) {
            huffman::huffman_node* left = pq.top(); pq.pop();
            huffman::huffman_node* right = pq.top(); pq.pop();

            auto* internal_node = new huffman::huffman_node('\0', left->frequency + right->frequency);
            internal_node->left = left;
            internal_node->right = right;
            pq.push(internal_node);
        }
        return pq.top();
    }
}

std::string huffman::huffman_encode(const std::string& text) {
    if (text.empty()) return "";

    huffman_node* root = build_tree(text);
    std::map<char, std::string> huffman_codes;
    generate_codes(root, "", huffman_codes);
    std::string encoded_text;

    for (char ch : text) encoded_text += huffman_codes[ch];

    cleanup_tree(root);
    return encoded_text;
}

std::string huffman::huffman_decode(const std::string& encoded_text, huffman_node* root) {
    if (root == nullptr) return "";

    std::string decoded_text;
    huffman_node* current_node = root;

    for (char bit : encoded_text) {
        if (bit == '0') current_node = current_node->left;
        else current_node = current_node->right;

        if (!current_node->left && !current_node->right) {
            decoded_text += current_node->data;
            current_node = root;
        }
    }
    return decoded_text;
}

void huffman::cleanup_tree(huffman_node* root) {
    if (root == nullptr) return;

    cleanup_tree(root->left);
    cleanup_tree(root->right);
    delete root;
}