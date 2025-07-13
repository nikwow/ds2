package algorithms.hashing.search;

import java.util.Collections;
import java.util.List;

public class binary_search implements search_strategy<String> {
    @Override
    public String search_key(List<String> list, String key) {
        Collections.sort(list);

        int index = Collections.binarySearch(list, key);

        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }
}