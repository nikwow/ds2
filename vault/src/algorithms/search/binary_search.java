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

        int index = Collections.binarySearch(list, key);

        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

    public List<String> list_all(List<String> list) {
        Collections.sort(list);
        return list;
    }
}