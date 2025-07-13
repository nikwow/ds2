package core;

import algorithms.hashing.search.multi_strategy;
import algorithms.hashing.search.search_strategy;
import utils.caesar_cipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class vault_manager {
    private final Map<String, entry> vault = new HashMap<>();
    private final caesar_cipher cipher;

    public vault_manager(int shift) {
        cipher = new caesar_cipher(shift);
    }

    public boolean add(String name, String url, String username, String password) {
        if (vault.containsKey(name)) {
            return false;
        }

        String encrypted = cipher.encrypt(password);
        entry e = new entry(name, url, username, encrypted);
        vault.put(name, e);
        return true;
    }

    public boolean delete(String name) {
        return vault.remove(name) != null;
    }

    public boolean update(String name, String url, String username, String password) {
        if (!vault.containsKey(name)) {
            return false;
        }

        String encrypted = cipher.encrypt(password);
        entry updated = new entry(name, url, username, encrypted);
        vault.put(name, updated);
        return true;
    }

    public entry get(String name) {
        entry e = vault.get(name);

        if (e == null) {
            return null;
        }

        return new entry(
                e.getName(),
                e.getUrl(),
                e.getUsername(),
                cipher.decrypt(e.getPassword())
        );
    }

    public entry search_key(String key, search_strategy<String> strategy) {
        List<String> keys = new ArrayList<>(vault.keySet());
        String result = strategy.search_key(keys, key);

        if (result != null) {
            return get(result);
        }
        return null;
    }

    public List<String> search_values(String pattern, multi_strategy strategy) {
        List<String> values = new ArrayList<>();

        for (entry e : vault.values()) {
            String decrypted = cipher.decrypt(e.getPassword());
            values.add(decrypted);
        }
        return strategy.search(values, pattern);
    }

    public void list_keys() {
        if (vault.isEmpty()) {
            System.out.println("No keys in vault");
        } else {
            for (String key : vault.keySet()) {
                System.out.println("- " + key);
            }
        }
    }

    public void list_all() {
        if (vault.isEmpty()) {
            System.out.println("Vault is empty");
        } else {
            for (entry e : vault.values()) {
                entry decrypted = get(e.getName());
                System.out.println(decrypted);
                System.out.println("--------------------------");
            }
        }
    }
}