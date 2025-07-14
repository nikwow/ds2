package utils;

import java.util.List;

public interface search_strategy<S> {
  S search_key(List<S> list, String key);
}