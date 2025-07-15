package utils;

public class caesar_cipher {
    private int shift;

    public caesar_cipher(int shift) {
        this.shift = shift % 26;
        if (this.shift < 0) {
            this.shift += 26;
        }
    }

    public String encrypt(String text) {
        StringBuilder sb = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                if (Character.isLowerCase(c)) {
                    sb.append((char) ('a' + (c - 'a' + shift) % 26));
                } else {
                    sb.append((char) ('A' + (c - 'A' + shift) % 26));
                }
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