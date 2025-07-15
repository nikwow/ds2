package algorithms.search;

import utils.search_strategy;

import java.util.Collections;
import java.util.List;

public class binary_search implements search_strategy<String> {
    @Override
    public String search_key(List<String> list, String key) {
        Collections.sort(list);

        if (key == null || key.isEmpty()) {
            return null;
        }

        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            String mid_value = list.get(mid);
            int com = key.compareTo(mid_value);

            if (com == 0) {
                return mid_value;
            } else if (com < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return null;
    }

    public List<String> list_all(List<String> list) {
        Collections.sort(list);
        return list;
    }
}