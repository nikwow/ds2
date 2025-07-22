package password;

public class password {
    public static boolean is_valid(String password) {
        return has_size(password)
                && has_upper(password)
                && has_lower(password)
                && has_number(password)
                && has_symbol(password);
    }

    private static boolean has_size(String password) {
        return password.length() >= 8;
    }

    private static boolean has_upper(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean has_lower(String password) {
        return password.matches(".*[a-z].*");
    }

    private static boolean has_number(String password) {
        return password.matches(".*\\d.*");
    }

    private static boolean has_symbol(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    }
}
