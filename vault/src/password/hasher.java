package password;

public class hasher {
    private final int size;

    public hasher(int size) {
        this.size = size;
    }

    public int gen_pass(String password) {
        int hash = 0;
        final int PRIME = 31;

        if (password == null) {
            return 0;
        }

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            hash = PRIME * hash + ch;
        }
        int index = hash % size;

        if (index < 0) {
            index += size;
        }
        return index;
    }

    public int gen_user(String username) {
        int hash = 0;
        final int PRIME = 31;

        for (int i = 0; i < username.length(); i++) {
            hash = hash * PRIME + username.charAt(i);
        }
        return Math.abs(hash);
    }
}
