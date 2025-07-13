package core;

public class entry {
    private final String name;
    private final String url;
    private final String username;
    private final String password;

    public entry(String name, String url, String username, String password) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "URL: " + url + "\n" +
                "Username: " + username + "\n" +
                "Password: " + password;
    }
}