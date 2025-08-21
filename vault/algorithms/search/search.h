//
// Created by nicol on 8/20/2025.
//

#ifndef VAULT_SEARCH_H
#define VAULT_SEARCH_H

#include <string>
#include <vector>

namespace collection_search {
    int linear(const std::vector<int>& data, int target);
    int binary(const std::vector<int>& data, int target);
}

namespace string_search {
    int rabin_karp(const std::string& text, const std::string& pattern);
}

#endif //VAULT_SEARCH_H