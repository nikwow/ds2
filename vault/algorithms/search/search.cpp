//
// Created by nicol on 8/20/2025.
//

#include "search.h"

int collection_search::linear(const std::vector<int>& data, int target) {
    for (int i = 0; i < data.size(); i++) {
        if (data[i] == target) return i; // target found
    }
    return -1; // target not found
}

int collection_search::binary(const std::vector<int>& data, int target) {
    int left = 0;
    int right = data.size() - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (data[mid] == target) return mid; // target found
        if (data[mid] < target) left = mid + 1; // target is greater, ignore the left half
        else right = mid - 1; // target is smaller, ignore the right half
    }
    return -1; // target not found
}

int string_search::rabin_karp(const std::string& text, const std::string& pattern) {
    int n = text.length();
    int m = pattern.length();

    if (m > n) return -1; // pattern is longer than text, it's impossible to find

    constexpr int d = 256;
    constexpr int q = 101;
    int pattern_hash = 0;
    int text_hash = 0;
    int h = 1;

    for (int i = 0; i < m - 1; i++) h = (h * d) % q;
    for (int i = 0; i < m; i++) {
        pattern_hash = (d * pattern_hash + pattern[i]) % q;
        text_hash = (d * text_hash + text[i]) % q;
    }
    for (int i = 0; i <= n - m; i++) {
        if (pattern_hash == text_hash) {
            int j = 0;
            for (j = 0; j < m; j++) {
                if (text[i + j] != pattern[j]) break;
            }
            if (j == m) return i; // pattern found
        }
        if (i < n - m) {
            text_hash = (d * (text_hash - text[i] * h) + text[i + m]) % q;
            if (text_hash < 0) text_hash += q;
        }
    }
    return -1; // pattern not found
}