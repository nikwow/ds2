package core;

import utils.multi_strategy;
import utils.search_strategy;
import utils.caesar_cipher;
import utils.huffman_compressor;

import java.util.*;

public class vault_manager {
    private final Map<String, entry> vault = new HashMap<>();
    private final caesar_cipher cipher;
    private final huffman_compressor huffman;

    public vault_manager(int shift) {
        cipher = new caesar_cipher(shift);
        huffman = new huffman_compressor();
    }

    public boolean add(String name, String url, String username, String password) {
        if (vault.containsKey(name)) {
            return false;
        }

        String encrypted = cipher.encrypt(password);
        String compressed = huffman.compress(encrypted);
        entry e = new entry(name, url, username, compressed);
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

        String decompressed = huffman.decompress(e.getPassword());
        String decrypted = cipher.decrypt(decompressed);
        return new entry(
                e.getName(),
                e.getUrl(),
                e.getUsername(),
                decrypted
        );
    }

    public List<entry> search_key(String key, search_strategy<String> strategy) {
        List<String> list = new ArrayList<>(vault.keySet());
        List<entry> result = new ArrayList<>();

        if (key == null || key.isEmpty()) {
            if (strategy instanceof algorithms.search.binary_search) {
                Collections.sort(list);
                for (String l : list) {
                    result.add(get(l));
                }
            } else {
                System.out.println("Linear search does not support full listing");
            }
        } else {
            String results = strategy.search_key(list, key);

            if (results != null) {
                result.add(get(results));
            }
        }
        return result;
    }

    public List<String> search_values(String pattern, multi_strategy strategy) {
        List<String> values = new ArrayList<>();

        for (entry e : vault.values()) {
            String decompressed = huffman.decompress(e.getPassword());
            String decrypted = cipher.decrypt(decompressed);
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