//
// Created by nicol on 8/20/2025.
//

#ifndef VAULT_ENTRY_H
#define VAULT_ENTRY_H
#include <string>


class entry {
    enum class type {
        login,
        note,
        file_attachment
    };

    entry(const std::string& title, type type);
};


#endif //VAULT_ENTRY_H