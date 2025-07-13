package utils;

public class caesar_cipher {
    private final int shift;

    public caesar_cipher(int shift) {
        this.shift = shift % 26;
    }

    public String encrypt(String text) {
        StringBuilder sb = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                sb.append((char) ('a' + (c - 'a' + shift) % 26));
            } else if (Character.isDigit(c)) {
                sb.append((char) ('A' + (c - 'A' + shift) % 26));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public String decrypt(String text) {
        return new caesar_cipher(26 - shift).encrypt(text);
    }
}