package object_orinted_design.cricket_score_board;

import java.util.HashMap;
import java.util.Map;

public class GenericRepository<T> {

    private Map<String, T> entityMap = new HashMap<>();

    public T get(String id) {
        return entityMap.get(id);
    }

    public void create(String id, T t) {
        entityMap.put(id, t);
    }

}
