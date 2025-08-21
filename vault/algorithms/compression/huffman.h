//
// Created by nicol on 8/20/2025.
//

#ifndef VAULT_HUFFMAN_H
#define VAULT_HUFFMAN_H
#include <string>

namespace huffman {
    struct huffman_node {
        char data;
        int frequency;
        huffman_node* left;
        huffman_node* right;

        huffman_node(char data, int frequency) : data(data), frequency(frequency), left(nullptr), right(nullptr) {}
    };

    std::string huffman_encode(const std::string& text);
    std::string huffman_decode(const std::string& encoded_text, huffman_node* root);
    void cleanup_tree(huffman_node* root);
}

#endif //VAULT_HUFFMAN_H