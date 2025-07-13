package algorithms.hashing.search;

import java.util.List;

public class linear_search implements search_strategy<String> {
    @Override
    public String search_key(List<String> list, String key) {
        for (String item : list) {
            if (item.equalsIgnoreCase(key)) {
                return item;
            }
        }
        return null;
    }
}