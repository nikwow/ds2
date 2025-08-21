//
// Created by nicol on 8/20/2025.
//

#include "password.h"

#include <string>
#include <cctype>

namespace {
    bool size(const std::string& password) {
        return password.size() >= 8;
    }

    bool upper(const std::string& password) {
        for (char ch : password) {
            if (std::isupper(ch)) return true;
        }
        return false;
    }

    bool lower(const std::string& password) {
        for (char ch : password) {
            if (std::islower(ch)) return true;
        }
        return false;
    }

    bool number(const std::string& password) {
        for (char ch : password) {
            if (std::isdigit(ch)) return true;
        }
        return false;
    }

    bool special(const std::string& password) {
        for (char ch : password) {
            if (!std::isalnum(ch)) return true;
        }
        return false;
    }
}

bool validator::is_valid(const std::string& password) {
    return size(password) && upper(password) && lower(password) && number(password) && special(password);
}