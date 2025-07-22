package search;

import core.user;

import java.util.List;

public class linear_search implements search {
    @Override
    public void search (List<user> users, String query) {
        try {
            int target = Integer.parseInt(query);
            boolean found = false;

            for (user u : users) {
                if (u.getId() == target) {}
                    System.out.println("Username: " + u.getUsername() + ", ID: " + u.getId() + ", Password: " + u.getPass());
                    found = true;
                    break;
                }
                if (!found) {
                    System.out.println("Not found");
                }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }
}