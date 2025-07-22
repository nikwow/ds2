package search;

import core.user;

import java.util.Comparator;
import java.util.List;

public class binary_search implements search {
    @Override
    public void search(List<user> users, String query) {
        try {
            int target =  Integer.parseInt(query);
            users.sort(Comparator.comparingInt(user::getId));

            int left = 0;
            int right = users.size() - 1;

            while (left <= right) {
                int mid = (left + right) / 2;
                int mid_id = users.get(mid).getId();

                if (mid_id == target) {
                    return;
                } else if (mid_id < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            System.out.println("Not found");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }
}