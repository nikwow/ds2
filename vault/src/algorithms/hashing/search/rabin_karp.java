package algorithms.hashing.search;

import java.util.ArrayList;
import java.util.List;

public class rabin_karp implements multi_strategy{
    private static final int d = 256;
    private static final int q = 101;

    @Override
    public List<String> search(List<String> texts, String pattern) {
        List<String> result = new ArrayList<>();

        for (String text : texts) {
            if (contains(text, pattern)) {
                result.add(text);
            }
        }
        return result;
    }

    private boolean contains(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();

        if (m > n) {
            return false;
        }

        int h = 1;

        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % q;
        }

        int p = 0, t = 0;

        for (int i = 0; i < m; i++) {
            p = (d * p + pattern.charAt(i)) % q;
            t = (d * t + text.charAt(i)) % q;
        }

        for (int i = 0; i <= n - m; i++) {
            if (p == t && text.substring(i, i + m).equals(pattern)) {
                return true;
            }

            if (i < n - m) {
                t = (d * (t - text.charAt(i) * h) + text.charAt(i + m)) % q;
                if (t < 0) {
                    t = t + q;
                }
            }
        }
        return false;
    }
}