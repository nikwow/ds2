package algorithms.hashing;

public class hashing {
    public static String djb2(String input) {
        long hash = 5381;

        for (char c : input.toCharArray()) {
            hash = ((hash << 5) + hash) + c;
        }
        return Long.toHexString(hash);
    }
}