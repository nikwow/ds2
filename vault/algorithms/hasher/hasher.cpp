//
// Created by nicol on 8/20/2025.
//

#include "hasher.h"

int hashing::multiplication(const std::string& key, int table) {
    long long hash = 0;

    for (char ch : key) {
        constexpr int PRIME = 31;

        hash = hash * PRIME + ch;
    }

    int index = hash % table;

    if (index < 0) index += table;

    return index;
}

int hashing::mid_square(const std::string& key) {
    long long hash = 0;

    for (char ch : key) {
        constexpr int PRIME = 31;

        hash = hash * PRIME + ch;
    }
    return std::abs(static_cast<int>(hash % 2147483647));
}