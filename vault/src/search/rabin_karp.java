package search;

import core.user;

import java.util.List;

public class rabin_karp implements search {
    @Override
    public void search(List<user> users, String pattern) {
        boolean found = false;

        for (user u : users) {
            if (contains(u.getUsername(), pattern)) {
                System.out.println("Username: " + u.getUsername() + ", ID: " + u.getId() + ", Password: " + u.getPass());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Not found");
        }
    }

    private boolean contains(String username, String pattern) {
        int prime = 101;
        int m = pattern.length();
        int n = username.length();

        if (m > n) {
            return false;
        }

        long pattern_hash = 0;
        long txt_hash = 0;
        long h = 1;

        for (int i = 0; i < m - 1; i++) {
            h = (h * 256) %  prime;
        }

        for (int i = 0; i < m; i++) {
            pattern_hash = (256 * pattern_hash + pattern.charAt(i)) % prime;
            txt_hash = (256 * txt_hash + username.charAt(i)) % prime;
        }

        for (int i = 0; i <= n - m; i++) {
            if (pattern_hash == txt_hash && username.substring(i, i + m).equals(pattern)) {
                return true;
            }
            if (i < n - m) {
                txt_hash = (256 * txt_hash + username.charAt(i + m)) % prime;
                if (txt_hash < 0) {
                    txt_hash +=  prime;
                }
            }
        }
        return false;
    }
}