cmake_minimum_required(VERSION 4.0)
project(vault)

set(CMAKE_CXX_STANDARD 20)

add_executable(vault main.cpp
        core/user.cpp
        core/user.h
        algorithms/search/search.h
        algorithms/search/search.cpp
        algorithms/hasher/hasher.h
        algorithms/hasher/hasher.cpp
        algorithms/compression/huffman.cpp
        algorithms/compression/huffman.h
        core/password.h
        core/password.cpp
        core/entry.cpp
        core/entry.h)