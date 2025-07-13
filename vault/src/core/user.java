package core;

import algorithms.hashing.hashing;

public class user {
    private final String name;
    private final String passHash;

    public user(String name, String password) {
        this.name = name;
        passHash = hashing.djb2(password);
    }

    public String getName() {
        return name;
    }

    public boolean authenticate(String attempt) {
        return passHash.equals(hashing.djb2(attempt));
    }
}