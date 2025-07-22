package core;

import password.password;
import password.huffman;
import password.hasher;

public class user {
    private String username;
    private String pass;
    private int id;
    private int user_hash;
    private huffman huffman;
    private hasher hasher;

    public user(String username, String pass, int id) {
        if (!password.is_valid(pass)) {
            throw new IllegalArgumentException("password is invalid");
        }
        this.huffman = new huffman();
        this.hasher = new hasher(1000);
        this.username = username;
        this.user_hash = hasher.gen_user(username);
        this.pass = process_password(pass);
        this.id = id;
    }

    private String process_password(String password) {
        this.huffman.build_tree(password);
        String compressed = this.huffman.encode(password);
        int hash = this.hasher.gen_pass(compressed);
        return String.valueOf(hash);
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && verify_password(password);
    }

    private boolean verify_password(String pass) {
        if (!password.is_valid(pass)) {
            return false;
        }

        String compressed = this.huffman.encode(pass);
        int hash = this.hasher.gen_pass(compressed);
        return this.pass.equals(String.valueOf(hash));
    }

    public String getUsername() {
        return this.username;
    }

    public int getUser_hash() {
        return this.user_hash;
    }

    public int getId() {
        return this.id;
    }

    public String getPass() {
        return this.pass;
    }
}